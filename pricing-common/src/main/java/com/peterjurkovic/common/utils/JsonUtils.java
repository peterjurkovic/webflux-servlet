package com.peterjurkovic.common.utils;

import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;

public class JsonUtils {

  private final static Logger Log = LoggerFactory.getLogger(JsonUtils.class);
    
    private JsonUtils(){}
    
  
    public final static ObjectMapper OBJECT_MAPPER =  new ObjectMapper()
            .configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
            .configure(MapperFeature.USE_ANNOTATIONS, true);
    
    
    
    public static String toJson(Object object){
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            Log.error("[toJson] can not convert: " + 
                        (object != null ? object.getClass().getName() : "null") + " to JSON.", e);
        }
        return "{}";
    }
    
    public static <T> T fromJson(String json, Class<T> clazz){
        try {
            return (T) OBJECT_MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            Log.error("[fromJson] failed map ["+json+"] to:" + clazz, e);
        }
        return null;
    }
    
    public static <T> List<T> toList(InputStream is, Class<T> clazz){
        try {
            CollectionType javaType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);
            return  OBJECT_MAPPER.readValue(is, javaType);
        } catch (Exception e) {
            Log.error("[fromJson] failed map InputStream to:" + clazz, e);
        }
        return null;
    }
}
