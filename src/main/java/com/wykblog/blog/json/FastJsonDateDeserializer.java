package com.wykblog.blog.json;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.wykblog.blog.utils.DateTimeUtil;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Date;

public class FastJsonDateDeserializer extends AbstractDateDeserializer implements ObjectDeserializer {
  public static final FastJsonDateDeserializer instance = new FastJsonDateDeserializer();
  
  public int getFastMatchToken() {
    return 4;
  }
  
  protected <T> T cast(DefaultJSONParser parser, Type clazz, Object fieldName, Object val) {
    if (val == null)
      return null; 
    if (val instanceof String) {
      String strVal = (String)val;
      if (strVal.length() == 0)
        return null; 
      if (strVal.length() <= 14 && (!strVal.contains(":") || !strVal.contains("-")))
        try {
          Long longVal = Long.valueOf(Long.parseLong(strVal));
          if (clazz == Date.class)
            return (T)new Date(longVal.longValue()); 
          if (clazz == Date.class)
            return (T)new Date(longVal.longValue()); 
          if (clazz == Timestamp.class)
            return (T)new Timestamp(longVal.longValue()); 
        } catch (NumberFormatException e) {
          e.printStackTrace();
        }  
      Date date = DateTimeUtil.parseStringToDate((String)val);
      if (clazz == Date.class)
        return (T)date; 
      if (clazz == Date.class)
        return (T)new Date(date.getTime()); 
      if (clazz == Timestamp.class)
        return (T)new Timestamp(date.getTime()); 
    } 
    if (val instanceof Long) {
      Long longVal = (Long)val;
      if (clazz == Date.class)
        return (T)new Date(longVal.longValue()); 
      if (clazz == Date.class)
        return (T)new Date(longVal.longValue()); 
      if (clazz == Timestamp.class)
        return (T)new Timestamp(longVal.longValue()); 
    } 
    throw new JSONException("parse field " + fieldName + " error where val=" + val);
  }
}