package com.study.spring.oauth.oauth;

import com.study.spring.oauth.util.AesCoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.JdkSerializationStrategy;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;
import org.springframework.util.*;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MyTokenStore implements TokenStore {

    private final JdbcTemplate jdbcTemplate;

    private static final String DEFAULT_REFRESH_TOKEN_INSERT_STATEMENT = "insert into oauth_refresh_token (token_id, token, authentication, gmt_create, gmt_modify, expiration) "
            + "values (?, ?, ?, ?, ?, ?)";
    private static final String DEFAULT_REFRESH_TOKEN_UPDATE_STATEMENT = "update oauth_refresh_token set expiration = ?, gmt_modify = ? where token_id = ?";
    private static final String DEFAULT_REFRESH_TOKEN_DELETE_STATEMENT = "delete from oauth_refresh_token where token_id = ?";
    private static final String DEFAULT_REFRESH_TOKEN_SELECT_STATEMENT = "select token_id, token, expiration from oauth_refresh_token where token_id = ?";
    private static final String DEFAULT_REFRESH_TOKEN_AUTHENTICATION_SELECT_STATEMENT = "select token_id, authentication from oauth_refresh_token where token_id = ?";

    private static final String DEFAULT_ACCESS_TOKEN_INSERT_STATEMENT = "insert into oauth_access_token (client_id, username, oauth_access_token, access_token, expiration, gmt_create) "
            + "values (?, ?, ?, ?, ?, ?)";
    private String insertRefreshTokenSql = DEFAULT_REFRESH_TOKEN_INSERT_STATEMENT;
    private String updateRefreshTokenSql = DEFAULT_REFRESH_TOKEN_UPDATE_STATEMENT;
    private String deleteRefreshTokenSql = DEFAULT_REFRESH_TOKEN_DELETE_STATEMENT;
    private String selectRefreshTokenSql = DEFAULT_REFRESH_TOKEN_SELECT_STATEMENT;
    private String selectRefreshTokenAuthenticationSql = DEFAULT_REFRESH_TOKEN_AUTHENTICATION_SELECT_STATEMENT;

    private String insertAccessTokenSql = DEFAULT_ACCESS_TOKEN_INSERT_STATEMENT;

    private StringRedisTemplate stringRedisTemplate;

    private static final String ACCESS = "access:";
    private static final String AUTH_TO_ACCESS = "auth_to_access:";
    private static final String AUTH = "auth:";
    private static final String REFRESH_AUTH = "refresh_auth:";
    private static final String ACCESS_TO_REFRESH = "access_to_refresh:";
    private static final String REFRESH = "refresh:";
    private static final String REFRESH_TO_ACCESS = "refresh_to_access:";
    // private static final String CLIENT_ID_TO_ACCESS = "client_id_to_access:";
    // private static final String UNAME_TO_ACCESS = "uname_to_access:";

    private static final boolean springDataRedis_2_0 = ClassUtils.isPresent("org.springframework.data.redis.connection.RedisStandaloneConfiguration",
            RedisTokenStore.class.getClassLoader());

    private final RedisConnectionFactory connectionFactory;
    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();
    private RedisTokenStoreSerializationStrategy serializationStrategy = new JdkSerializationStrategy();

    private String prefix = "";

    private Method redisConnectionSet_2_0;

    private long accessTokenTransitionalExpirationSeconds = 600L;

    public MyTokenStore(RedisConnectionFactory connectionFactory, StringRedisTemplate stringRedisTemplate, DataSource dataSource) {
        Assert.notNull(dataSource, "DataSource required");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.stringRedisTemplate = stringRedisTemplate;
        this.connectionFactory = connectionFactory;
        if (springDataRedis_2_0) {
            this.loadRedisConnectionMethods_2_0();
        }
    }

    public void setAuthenticationKeyGenerator(AuthenticationKeyGenerator authenticationKeyGenerator) {
        this.authenticationKeyGenerator = authenticationKeyGenerator;
    }

    public void setSerializationStrategy(RedisTokenStoreSerializationStrategy serializationStrategy) {
        this.serializationStrategy = serializationStrategy;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setAccessTokenTransitionalExpirationSeconds(long accessTokenTransitionalExpirationSeconds) {
        this.accessTokenTransitionalExpirationSeconds = accessTokenTransitionalExpirationSeconds;
    }

    private void loadRedisConnectionMethods_2_0() {
        this.redisConnectionSet_2_0 = ReflectionUtils.findMethod(RedisConnection.class, "set", byte[].class, byte[].class);
    }

    private RedisConnection getConnection() {
        return connectionFactory.getConnection();
    }

    private byte[] serialize(Object object) {
        return serializationStrategy.serialize(object);
    }

    private byte[] serializeKey(String object) {
        return serialize(prefix + object);
    }

    private OAuth2AccessToken deserializeAccessToken(byte[] bytes) {
        return serializationStrategy.deserialize(bytes, OAuth2AccessToken.class);
    }

    private OAuth2Authentication deserializeAuthentication(byte[] bytes) {
        return serializationStrategy.deserialize(bytes, OAuth2Authentication.class);
    }

    private OAuth2RefreshToken deserializeRefreshToken(byte[] bytes) {
        return serializationStrategy.deserialize(bytes, OAuth2RefreshToken.class);
    }

    private byte[] serialize(String string) {
        return serializationStrategy.serialize(string);
    }

    private String deserializeString(byte[] bytes) {
        return serializationStrategy.deserializeString(bytes);
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        String key = authenticationKeyGenerator.extractKey(authentication);
        log.info("my redis token store get access token by authToAccessKey:{}", key);
        byte[] serializedKey = serializeKey(AUTH_TO_ACCESS + key);
        byte[] bytes = null;
        RedisConnection conn = getConnection();
        try {
            bytes = conn.get(serializedKey);
        } finally {
            conn.close();
        }
        OAuth2AccessToken accessToken = deserializeAccessToken(bytes);
        log.debug("my redis token store get authToAccessKey:{} accessToken:{}", AUTH_TO_ACCESS + key, accessToken);
        if (accessToken != null) {
            OAuth2Authentication storedAuthentication = readAuthentication(accessToken.getValue());
            if ((storedAuthentication == null || !key.equals(authenticationKeyGenerator.extractKey(storedAuthentication)))) {
                // Keep the stores consistent (maybe the same user is
                // represented by this authentication but the details have
                // changed)
                storeAccessToken(accessToken, authentication);
            }

        }
        return accessToken;
    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        byte[] bytes = null;
        RedisConnection conn = getConnection();
        try {
            bytes = conn.get(serializeKey(AUTH + token));
        } finally {
            conn.close();
        }
        OAuth2Authentication auth = deserializeAuthentication(bytes);
        log.debug("my redis token store read authKey:{} oauth2Authentication:{}", AUTH + token, auth);
        return auth;
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return readAuthenticationForRefreshToken(token.getValue());
    }

    public OAuth2Authentication readAuthenticationForRefreshToken(String token) {
        log.info("my redis token read authentication by refresh token:{}", token);
        OAuth2Authentication authentication = null;

        try {
            authentication = jdbcTemplate.queryForObject(selectRefreshTokenAuthenticationSql, new RowMapper<OAuth2Authentication>() {
                public OAuth2Authentication mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return deserializeAuthentication(rs.getBytes(2));
                }
            }, extractTokenKey(token));
            log.debug("my redis token read clientId:{} by refresh token:{}", authentication.getOAuth2Request().getClientId(), extractTokenKey(token));
        } catch (EmptyResultDataAccessException e) {
            if (log.isInfoEnabled()) {
                log.info("Failed to find access token for token " + token);
            }
        } catch (IllegalArgumentException e) {
            log.warn("Failed to deserialize access token for " + token, e);
            removeRefreshToken(token);
        }

        return authentication;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        log.info("my redis token store store access token:{} expiration:{}", token.getValue(), token.getExpiration());
        byte[] serializedAccessToken = serialize(token);
        byte[] serializedAuth = serialize(authentication);
        byte[] accessKey = serializeKey(ACCESS + token.getValue());
        byte[] authKey = serializeKey(AUTH + token.getValue());
        String authToAccessKeyStr = AUTH_TO_ACCESS + authenticationKeyGenerator.extractKey(authentication);
        byte[] authToAccessKey = serializeKey(authToAccessKeyStr);
        // byte[] approvalKey = serializeKey(UNAME_TO_ACCESS + getApprovalKey(authentication));
        // byte[] clientId = serializeKey(CLIENT_ID_TO_ACCESS + authentication.getOAuth2Request().getClientId());

        RedisConnection conn = getConnection();
        try {
            conn.openPipeline();
            if (springDataRedis_2_0) {
                try {
                    this.redisConnectionSet_2_0.invoke(conn, accessKey, serializedAccessToken);
                    log.debug("my redis token store invoke accessKey:{} accessToken:{}", accessKey, token);
                    this.redisConnectionSet_2_0.invoke(conn, authKey, serializedAuth);
                    log.debug("my redis token store invoke authKey:{} authentication:{}", authKey, authentication);
                    this.redisConnectionSet_2_0.invoke(conn, authToAccessKey, serializedAccessToken);
                    log.debug("my redis token store invoke authToAccessKey:{} accessToken:{}", authToAccessKeyStr, token);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                conn.set(accessKey, serializedAccessToken);
                log.debug("my redis token store set accessKey:{} accessToken:{}", accessKey, token);
                conn.set(authKey, serializedAuth);
                log.debug("my redis token store set authKey:{} accessToken:{}", authKey, authentication);
                conn.set(authToAccessKey, serializedAccessToken);
                log.debug("my redis token store set authToAccessKey:{} accessToken:{}", authToAccessKey, token);
            }

            try {
                String requestUsername = null;
                String clientId = authentication.getOAuth2Request().getClientId();
                boolean isAppIdEncrypted = false;
                if (!authentication.isClientOnly()) {
//                    String rsaPrivateKey = (String) RequestContext.getCurrentContext().get("rsaPrivate");
//                    requestUsername = RsaCoder.decryptByPrivateKey(authentication.getUserAuthentication().getName(), rsaPrivateKey);
                    // 友刷client_id目前采用加密，后续所有client_id都采用铭文。isAppIdEncrypted就是为了标识是否来自友刷的请求
//                    isAppIdEncrypted = (boolean) RequestContext.getCurrentContext().get("isAppIdEncrypted");
                    if (isAppIdEncrypted) {
//                        clientId = RsaCoder.decryptByPrivateKey(clientId, rsaPrivateKey);
                    }
                }
                jdbcTemplate.update(insertAccessTokenSql,
                        new Object[] { clientId, requestUsername, new SqlLobValue(serializedAccessToken), token.getValue(), token.getExpiration(), new Date() },
                        new int[] { Types.VARCHAR, Types.VARCHAR, Types.BLOB, Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP });
                log.debug("my jdbc token store insert client_id:{},username:{},accessToken:{}", clientId, requestUsername, token.getValue());
            } catch (Exception e) {
                log.error("my jdbc token store insert error:{} ", e.getMessage());
            }

            if (token.getExpiration() != null) {
                int seconds = token.getExpiresIn();
                conn.expire(accessKey, seconds);
                log.debug("my redis token store expire accessKey:{} seconds:{}", accessKey, seconds);
                conn.expire(authKey, seconds);
                log.debug("my redis token store expire authKey:{} seconds:{}", authKey, seconds);
                conn.expire(authToAccessKey, seconds);
                log.debug("my redis token store expire authToAccessKey:{} seconds:{}", authToAccessKey, seconds);
                // conn.expire(clientId, seconds);
                // log.debug("my redis token store expire clientId:{} seconds:{}", clientId, seconds);
                // conn.expire(approvalKey, seconds);
                // log.debug("my redis token store expire approvalKey:{} seconds:{}", approvalKey, seconds);
            }
            OAuth2RefreshToken refreshToken = token.getRefreshToken();
            if (refreshToken != null && refreshToken.getValue() != null) {
                byte[] refresh = serialize(token.getRefreshToken().getValue());
                byte[] auth = serialize(token.getValue());
                byte[] refreshToAccessKey = serializeKey(REFRESH_TO_ACCESS + token.getRefreshToken().getValue());
                byte[] accessToRefreshKey = serializeKey(ACCESS_TO_REFRESH + token.getValue());
                if (springDataRedis_2_0) {
                    try {
                        this.redisConnectionSet_2_0.invoke(conn, refreshToAccessKey, auth);
                        log.debug("my redis token store invoke refreshToAccessKey:{} accessToken:{}", refreshToAccessKey, token.getValue());
                        this.redisConnectionSet_2_0.invoke(conn, accessToRefreshKey, refresh);
                        log.debug("my redis token store invoke accessToRefreshKey:{} refreshToken:{}", accessToRefreshKey, token.getRefreshToken().getValue());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    conn.set(refreshToAccessKey, auth);
                    log.debug("my redis token store set refreshToAccessKey:{} accessToken:{}", refreshToAccessKey, token.getValue());
                    conn.set(accessToRefreshKey, refresh);
                    log.debug("my redis token store set accessToRefreshKey:{} refreshToken:{}", accessToRefreshKey, token.getRefreshToken().getValue());
                }
                if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
                    ExpiringOAuth2RefreshToken expiringRefreshToken = (ExpiringOAuth2RefreshToken) refreshToken;
                    Date expiration = expiringRefreshToken.getExpiration();
                    if (expiration != null) {
                        long seconds = (expiration.getTime() - System.currentTimeMillis()) / 1000L;
                        conn.expire(refreshToAccessKey, seconds);
                        log.debug("my redis token store expire refreshToAccessKey:{} seconds:{}", refreshToAccessKey, seconds);
                        conn.expire(accessToRefreshKey, seconds);
                        log.debug("my redis token store expire accessToRefreshKey:{} seconds:{}", accessToRefreshKey, seconds);
                    }
                }
            }
            conn.closePipeline();
        } finally {
            conn.close();
        }
    }

    private static String getApprovalKey(OAuth2Authentication authentication) {
        String userName = authentication.getUserAuthentication() == null ? "" : authentication.getUserAuthentication().getName();
        return getApprovalKey(authentication.getOAuth2Request().getClientId(), userName);
    }

    private static String getApprovalKey(String clientId, String userName) {
        return clientId + (userName == null ? "" : ":" + userName);
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken accessToken) {
        removeAccessToken(accessToken.getValue());
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        // 先做解密
        try {
            String aesKey = stringRedisTemplate.opsForValue().get(tokenValue);
            if (StringUtils.isEmpty(aesKey)) {
                log.info("my redis token store read aesKey is expired by access token:{}", tokenValue);
                return null;
            }
            tokenValue = AesCoder.decryptData(aesKey, tokenValue);
            log.info("my redis token store read access token:{}", tokenValue);
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid access token: " + tokenValue);
        }
        byte[] key = serializeKey(ACCESS + tokenValue);
        byte[] bytes = null;
        RedisConnection conn = getConnection();
        try {
            bytes = conn.get(key);
        } finally {
            conn.close();
        }
        OAuth2AccessToken accessToken = deserializeAccessToken(bytes);
        log.debug("my redis token store read accessKey:{} accessToken:{}", ACCESS + tokenValue, accessToken);
        return accessToken;
    }

    public void removeAccessToken(String tokenValue) {
        log.info("my redis token store remove access token:{}", tokenValue);
        byte[] accessKey = serializeKey(ACCESS + tokenValue);
        byte[] authKey = serializeKey(AUTH + tokenValue);
        byte[] accessToRefreshKey = serializeKey(ACCESS_TO_REFRESH + tokenValue);
        RedisConnection conn = getConnection();
        try {
            conn.openPipeline();
            conn.get(accessKey);
            conn.get(authKey);
            conn.del(accessKey);
            log.debug("my redis token store del accessKey:{}", accessKey);
            conn.del(accessToRefreshKey);
            log.debug("my redis token store del accessToRefreshKey:{}", accessToRefreshKey);
            // Don't remove the refresh token - it's up to the caller to do that
            conn.del(authKey);
            log.debug("my redis token store del authKey:{}", authKey);
            List<Object> results = conn.closePipeline();
            byte[] access = (byte[]) results.get(0);
            byte[] auth = (byte[]) results.get(1);

            OAuth2Authentication authentication = deserializeAuthentication(auth);
            if (authentication != null) {
                String key = authenticationKeyGenerator.extractKey(authentication);
                byte[] authToAccessKey = serializeKey(AUTH_TO_ACCESS + key);
                // byte[] unameKey = serializeKey(UNAME_TO_ACCESS + getApprovalKey(authentication));
                // byte[] clientId = serializeKey(CLIENT_ID_TO_ACCESS + authentication.getOAuth2Request().getClientId());
                conn.openPipeline();
                conn.del(authToAccessKey);
                log.debug("my redis token store del authToAccessKey:{}", authToAccessKey);
                // conn.lRem(unameKey, 1, access);
                // log.debug("my redis token store lrem unameKey:{}", unameKey);
                // conn.lRem(clientId, 1, access);
                // log.debug("my redis token store lrem clientId:{}", clientId);
                conn.del(serialize(ACCESS + key));
                log.debug("my redis token store del accessKey:{}", ACCESS + key);
                conn.closePipeline();
            }
        } finally {
            conn.close();
        }
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        log.info("my redis token store store refresh token:{}", refreshToken.getValue());

        String value = refreshToken.getValue();
        boolean isUpdate = value.startsWith("{") && value.endsWith("}") ? true : false;

        Date expiration = null;
        if (refreshToken instanceof DefaultExpiringOAuth2RefreshToken) {
            DefaultExpiringOAuth2RefreshToken defaultExpiringOAuth2RefreshToken = (DefaultExpiringOAuth2RefreshToken) refreshToken;
            expiration = defaultExpiringOAuth2RefreshToken.getExpiration();
            if (isUpdate) {
                value = value.substring(1, value.length() - 1);
            }

        }

        String refreshTokenDB = extractTokenKey(value);

        if (!isUpdate) {
            jdbcTemplate.update(insertRefreshTokenSql, new Object[] { refreshTokenDB, new SqlLobValue(serializeRefreshToken(refreshToken)),
                    new SqlLobValue(serializeAuthentication(authentication)), new Date(), new Date(), expiration }, new int[] { Types.VARCHAR, Types.BLOB,
                    Types.BLOB, Types.TIMESTAMP, Types.TIMESTAMP, Types.TIMESTAMP });
            log.info("my redis token store insert refresh token value:{} tokenId:{} expiration:{}", value, refreshTokenDB, expiration);
        } else {
            int number = jdbcTemplate.update(updateRefreshTokenSql, new Object[] { expiration, new Date(), refreshTokenDB }, new int[] { Types.TIMESTAMP,
                    Types.TIMESTAMP, Types.VARCHAR });
            log.info("my redis token update db number:{} refreshTokenDB:{} refreshToken:{}", number, refreshTokenDB, value);

            RedisConnection conn = getConnection();
            try {
                conn.openPipeline();
                byte[] refreshToAccessKey = serializeKey(REFRESH_TO_ACCESS + value);
                if (expiration != null) {
                    long seconds = (expiration.getTime() - System.currentTimeMillis()) / 1000L;
                    conn.expire(refreshToAccessKey, seconds);
                    log.info("my redis token update expire refreshToAccessKey:{} seconds:{}", REFRESH_TO_ACCESS + value, seconds);
                }
                conn.closePipeline();
            } finally {
                conn.close();
            }
        }
    }

    protected String extractTokenKey(String value) {
        if (value == null) {
            return null;
        }
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
        }

        try {
            byte[] bytes = digest.digest(value.getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
        }
    }

    protected byte[] serializeRefreshToken(OAuth2RefreshToken token) {
        return SerializationUtils.serialize(token);
    }

    protected byte[] serializeAuthentication(OAuth2Authentication authentication) {
        return SerializationUtils.serialize(authentication);
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        log.info("my redis token read refresh token:{}", tokenValue);
        // 先做解密
        String rawTokenValue = null;
        try {
            // redis里存放的密文
            String aesKey = stringRedisTemplate.opsForValue().get(tokenValue);
            if (StringUtils.isEmpty(aesKey)) {
                log.info("my redis token store read aesKey is expired by refresh token:{}", tokenValue);
                return null;
            }
            rawTokenValue = AesCoder.decryptData(aesKey, tokenValue);
            log.info("my redis token store read refresh token:{}", rawTokenValue);
        } catch (Exception e) {
            log.error("my redis token store read refresh token:{} error", tokenValue, e);
            throw new InvalidGrantException("Invalid refresh token: " + tokenValue);
        }
        OAuth2RefreshToken refreshToken = null;

        try {
            refreshToken = jdbcTemplate.queryForObject(selectRefreshTokenSql, new RowMapper<OAuth2RefreshToken>() {

                public OAuth2RefreshToken mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Date expiration = rs.getTimestamp(3);
                    OAuth2RefreshToken refreshTokenDB = deserializeRefreshToken(rs.getBytes(2));
                    if (refreshTokenDB instanceof DefaultExpiringOAuth2RefreshToken) {
                        refreshTokenDB = new DefaultExpiringOAuth2RefreshToken(refreshTokenDB.getValue(), expiration);
                    }
                    long refreshTokenExpiration = (expiration.getTime() - System.currentTimeMillis()) / 1000L;
                    stringRedisTemplate.boundValueOps(tokenValue).expire(refreshTokenExpiration + 600, TimeUnit.SECONDS);
                    log.info("my redis token store expire refreshToken:{} expiration:{}", tokenValue, refreshTokenExpiration + 600);
                    return refreshTokenDB;
                }
            }, extractTokenKey(rawTokenValue));
        } catch (EmptyResultDataAccessException e) {
            if (log.isInfoEnabled()) {
                log.info("Failed to find refresh token for token " + tokenValue);
            }
        } catch (IllegalArgumentException e) {
            log.warn("Failed to deserialize refresh token for token " + tokenValue, e);
            removeRefreshToken(rawTokenValue);
        }
        return refreshToken;
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken refreshToken) {
        removeRefreshToken(refreshToken.getValue());
    }

    public void removeRefreshToken(String tokenValue) {
        log.info("my redis token store remove refresh token:{}", tokenValue);

        jdbcTemplate.update(deleteRefreshTokenSql, extractTokenKey(tokenValue));
        log.debug("my redis token store remove refreshToken from db:{}", tokenValue);

        byte[] refresh2AccessKey = serializeKey(REFRESH_TO_ACCESS + tokenValue);
        byte[] access2RefreshKey = serializeKey(ACCESS_TO_REFRESH + tokenValue);
        RedisConnection conn = getConnection();
        try {
            conn.openPipeline();
            conn.del(refresh2AccessKey);
            log.debug("my redis token store remove refresh2AccessKey:{}", REFRESH_TO_ACCESS + tokenValue);
            conn.del(access2RefreshKey);
            log.debug("my redis token store remove access2RefreshKey:{}", ACCESS_TO_REFRESH + tokenValue);
            conn.closePipeline();
        } finally {
            conn.close();
        }
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        if (refreshToken.getValue().startsWith("(") && refreshToken.getValue().endsWith(")")) {
            expireAccessTokenUsingRefreshToken(refreshToken, accessTokenTransitionalExpirationSeconds);
        } else {
            removeAccessTokenUsingRefreshToken(refreshToken.getValue());
        }
    }

    private void removeAccessTokenUsingRefreshToken(String refreshToken) {
        log.info("my redis token remove access token by refresh token:{}", refreshToken);
        byte[] key = serializeKey(REFRESH_TO_ACCESS + refreshToken);
        List<Object> results = null;
        RedisConnection conn = getConnection();
        try {
            conn.openPipeline();
            conn.get(key);
            conn.del(key);
            results = conn.closePipeline();
        } finally {
            conn.close();
        }
        if (results == null) {
            return;
        }
        byte[] bytes = (byte[]) results.get(0);
        String accessToken = deserializeString(bytes);
        if (accessToken != null) {
            removeAccessToken(accessToken);
        }
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        // byte[] approvalKey = serializeKey(UNAME_TO_ACCESS + getApprovalKey(clientId, userName));
        // List<byte[]> byteList = null;
        // RedisConnection conn = getConnection();
        // try {
        // byteList = conn.lRange(approvalKey, 0, -1);
        // } finally {
        // conn.close();
        // }
        // if (byteList == null || byteList.size() == 0) {
        // return Collections.<OAuth2AccessToken> emptySet();
        // }
        // List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>(byteList.size());
        // for (byte[] bytes : byteList) {
        // OAuth2AccessToken accessToken = deserializeAccessToken(bytes);
        // accessTokens.add(accessToken);
        // }
        // 数据量较大，且暂无使用场景
        List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>();
        return Collections.<OAuth2AccessToken> unmodifiableCollection(accessTokens);
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        // byte[] key = serializeKey(CLIENT_ID_TO_ACCESS + clientId);
        // List<byte[]> byteList = null;
        // RedisConnection conn = getConnection();
        // try {
        // byteList = conn.lRange(key, 0, -1);
        // } finally {
        // conn.close();
        // }
        // if (byteList == null || byteList.size() == 0) {
        // return Collections.<OAuth2AccessToken> emptySet();
        // }
        // List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>(byteList.size());
        // for (byte[] bytes : byteList) {
        // OAuth2AccessToken accessToken = deserializeAccessToken(bytes);
        // accessTokens.add(accessToken);
        // }
        // 数据量较大，且暂无使用场景
        List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>();
        return Collections.<OAuth2AccessToken> unmodifiableCollection(accessTokens);
    }

    public void expireAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken, long expiration) {
        expireAccessTokenUsingRefreshToken(refreshToken.getValue(), expiration);
    }

    private void expireAccessTokenUsingRefreshToken(String refreshToken, long expiration) {
        if (refreshToken.startsWith("(") && refreshToken.endsWith(")")) {
            refreshToken = refreshToken.substring(1, refreshToken.length() - 1);
        }
        log.info("my redis token expire access token by refresh token:{}", refreshToken);
        byte[] key = serializeKey(REFRESH_TO_ACCESS + refreshToken);
        List<Object> results = null;
        RedisConnection conn = getConnection();
        try {
            conn.openPipeline();
            conn.get(key);
            results = conn.closePipeline();
        } finally {
            conn.close();
        }
        if (results == null) {
            return;
        }
        byte[] bytes = (byte[]) results.get(0);
        String accessToken = deserializeString(bytes);
        if (accessToken != null) {
            expireAccessToken(accessToken, expiration);
        }
    }

    public void expireAccessToken(String tokenValue, long expiration) {
        log.info("my redis token store expire access token:{}", tokenValue);
        byte[] accessKey = serializeKey(ACCESS + tokenValue);
        byte[] authKey = serializeKey(AUTH + tokenValue);
        RedisConnection conn = getConnection();
        try {
            conn.openPipeline();
            conn.get(authKey);
            conn.expire(accessKey, expiration);
            log.debug("my redis token store expire accessKey:{} seconds:{}", accessKey, expiration);
            conn.expire(authKey, expiration);
            log.debug("my redis token store expire authKey:{} seconds:{}", authKey, expiration);

            List<Object> results = conn.closePipeline();
            byte[] auth = (byte[]) results.get(0);

            OAuth2Authentication authentication = deserializeAuthentication(auth);
            if (authentication != null) {
                String key = authenticationKeyGenerator.extractKey(authentication);
                byte[] authToAccessKey = serializeKey(AUTH_TO_ACCESS + key);
                // byte[] approvalKey = serializeKey(UNAME_TO_ACCESS + getApprovalKey(authentication));
                // byte[] clientId = serializeKey(CLIENT_ID_TO_ACCESS + authentication.getOAuth2Request().getClientId());
                conn.openPipeline();
                conn.expire(authToAccessKey, expiration);
                log.debug("my redis token store expire authToAccessKey:{} seconds:{}", authToAccessKey, expiration);
                // conn.expire(clientId, expiration);
                // log.debug("my redis token store expire clientId:{} seconds:{}", clientId, expiration);
                // conn.expire(approvalKey, expiration);
                // log.debug("my redis token store expire approvalKey:{} seconds:{}", approvalKey, expiration);
                conn.closePipeline();
            }
        } finally {
            conn.close();
        }
    }
}
