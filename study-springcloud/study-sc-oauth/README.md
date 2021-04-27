#Spring Oauth 2.0
默认请求路径
https:///网关地址/oauth/token
##流程
 1.  APP登录，访问该地址获取access_token，https:///网关地址/oauth/token?grant_type=password&scope=select&username=密文&password=密文&client_id=明文&client_secret=密文&busiType=密文&msg=密文（具体参数说明见下文【接入方登录申请token】）
 2.  网关接收登录请求后，根据busiType获取后端业务系统登录接口loginUrl（网关预先绑定了busiType和loginUrl的关系），例如：http://172.17.0.164/vip-user/user/login?username=?&password=?&msg=?，其中msg为json字符串。后端业务系统返回json数据（参数及格式见下文【规范与约束】），其中包含identification这个key，是用户在业务系统中的唯一标识，网关绑定username和identification关系入库。
 3.  APP获取access_token后，访问其他业务接口，只需要在请求中带着这个值即可，其他业务参数保持不变，该怎么传还是怎么传【详情见下方重点补充】
 4.  网关接收APP的业务接口请求后，路由到后端业务系统，其中会将access_token置换成登录时分配的identification，传递过去【注意：identification是业务系统和网关之间约定的用户唯一标识】
 5.  当access_token过期后，APP端可以利用存储的refresh_token重新请求/oauth/token接口，获取新的access_token，从而避免了用户的重新登录

刷新token接口信息：
POST /oauth/token?grant_type=refresh_token&refresh_token=密文&client_id=明文&client_secret=密文
补充：refresh_token用登录时颁发的AES密码进行加密，其他字段和登录获取access_token一致用RSA公钥加密

