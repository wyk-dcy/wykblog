package com.wykblog.blog.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.wykblog.blog.json.FastJsonDateDeserializer;
import com.wykblog.blog.json.Test;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

public class JsonUtils {
  private static Gson gson = new Gson();
  
  private static Type mapType = (new TypeToken<Map<String, String>>() {
    
    }).getType();
  
  static {
    ParserConfig.getGlobalInstance().putDeserializer(Date.class, (ObjectDeserializer)new FastJsonDateDeserializer());
    ParserConfig.getGlobalInstance().putDeserializer(Date.class, (ObjectDeserializer)new FastJsonDateDeserializer());
    ParserConfig.getGlobalInstance().putDeserializer(Timestamp.class, (ObjectDeserializer)new FastJsonDateDeserializer());
  }
  
  public static String toJson(Object obj) {
    return JSON.toJSONString(obj);
  }
  
  public static String toJson(Object obj, SerializerFeature... features) {
    return JSON.toJSONString(obj, features);
  }
  
  public static <T> T fromJson(String json, Class<T> clazz) {
    return (T)JSON.parseObject(json, clazz);
  }
  
  public static <T, U> Map<T, U> getMap(String str) {
    if (str == null || str.trim().length() == 0)
      return null; 
    try {
      return (Map<T, U>)gson.fromJson(str, mapType);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public static void main(String[] args) {
    String jsonDate = "{'date':'2016-05-16','sqlDate':'2016-05-16 17:32','timestamp':'2016-05-16 17:32:2'}";
    String jsonLong = "{'date':'1463328000000','sqlDate':1463391120000,'timestamp':1463391122000}";
    Test test2 = fromJson(jsonDate, Test.class);
    System.out.println(toJson(test2, new SerializerFeature[] { SerializerFeature.WriteDateUseDateFormat }));
    Test test3 = fromJson(jsonLong, Test.class);
    System.out.println(toJson(test3, new SerializerFeature[] { SerializerFeature.WriteDateUseDateFormat }));
  }
}