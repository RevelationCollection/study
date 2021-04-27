package com.study.spring.oauth.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class JSONUtils {
	private static Logger log = LoggerFactory.getLogger(JSONUtils.class);

    private JSONUtils() {}

	private static ObjectMapper mapper = new ObjectMapper();
	static{
		mapper.getDeserializationConfig().without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
		.with(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
	}

	public static JsonNode readTree(String content){
        try {
            return mapper.readTree(content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static  <T> T  treeToValue(TreeNode n, Class<T> valueType){
        try {
	        return mapper.treeToValue(n,valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static  <T> T  readValue(String content, Class<T> valueType){
        try {
            return mapper.readValue(content,valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String  writeValueAsString(Object value){
        try {
            return mapper.writeValueAsString(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ObjectMapper  getMapper(){
        return mapper;
    }

	public static <T> T parser(String content,  TypeReference<T> t) throws  IOException {
		return mapper.readValue(content, t);

	}
	public static <T> T parser(String content,Class<T> cls) throws JsonProcessingException {
		return mapper.readValue(content, cls);
	}

	public static <T> T parseObject(String content,Class<T> cls) {
		try{
			return mapper.readValue(content, cls);
		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}

	public static String toJSONString(Object obj) throws JsonProcessingException {
		return mapper.writeValueAsString(obj);
	}

	public static String toString(Object obj) {
		try {
			if (obj==null) return null;
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			log.error("json error",e);
			return null;
		}
	}
//
//	public static String alibabaJsonString(Object obj){
//		return com.alibaba.fastjson.JSON.toJSONString(obj
//				, SerializerFeature.WriteMapNullValue
//				,SerializerFeature.WriteNullStringAsEmpty
//				,SerializerFeature.WriteNullListAsEmpty);
//	}
//
//	public static String alibabaNoJsonString(Object obj){
//		return com.alibaba.fastjson.JSON.toJSONString(obj);
//	}

}
