package com.wykblog.blog.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectUtil {
  private static final Logger logger = LoggerFactory.getLogger(ObjectUtil.class);
  
  public static <T> T convertValue(Object value, Class<T> clazz) {
    if (value == null)
      return null; 
    String param = value.toString().trim();
    if (param.trim().equals(""))
      return null; 
    if (instanceOf(value, clazz)) {
      if (clazz == String.class)
        return (T)param; 
      return (T)value;
    } 
    try {
      if (clazz == Integer.class || clazz == int.class)
        return (T)Integer.valueOf(param); 
      if (clazz == Double.class || clazz == double.class)
        return (T)Double.valueOf(param); 
      if (clazz == Float.class || clazz == float.class)
        return (T)Float.valueOf(param); 
      if (clazz == Long.class || clazz == long.class)
        return (T)Long.valueOf(param); 
      if (clazz == Short.class || clazz == short.class)
        return (T)Short.valueOf(param); 
      if (clazz == Byte.class || clazz == byte.class)
        return (T)Byte.valueOf(param); 
      if (clazz == String.class)
        return (T)param; 
      if (clazz == Boolean.class || clazz == boolean.class)
        return (T)Boolean.valueOf(value.toString()); 
      if (clazz == Date.class || clazz == Timestamp.class || clazz == Date.class) {
        long time = 0L;
        if (StringUtil.isNumeric(param)) {
          time = Long.valueOf(param).longValue();
          if (time < 2147483647L)
            time *= 1000L; 
        } else {
          DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
          if (param.length() > 11)
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
          time = dateFormat.parse(param).getTime();
        } 
        Date t = null;
        if (clazz == Date.class) {
          t = new Date(time);
        } else if (clazz == Timestamp.class) {
          t = new Timestamp(time);
        } else if (clazz == Date.class) {
          t = new Date(time);
        } 
        t.setTime(time);
        return (T)t;
      } 
      return clazz.newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
      return null;
    } catch (IllegalAccessException e) {
      return null;
    } catch (Exception e) {
      logger.debug("", e);
      return null;
    } 
  }
  
  public static boolean instanceOf(Object value, Class<?> cls) {
    if (cls.isInstance(value))
      return true; 
    Class<? extends Object> c = (Class)value.getClass();
    while (c != null) {
      if (c.equals(cls))
        return true; 
      c = (Class)c.getSuperclass();
    } 
    return false;
  }
  
  public static void invokeDefaultValue(Object obj) {
    if (obj == null)
      return; 
    Class<? extends Object> cls = (Class)obj.getClass();
    Field[] fields = cls.getDeclaredFields();
    Class<? extends Object> superCls = (Class)cls.getSuperclass();
    Field[] superFields = superCls.getDeclaredFields();
    byte b;
    int i;
    Field[] arrayOfField1;
    for (i = (arrayOfField1 = fields).length, b = 0; b < i; ) {
      Field field = arrayOfField1[b];
      try {
        field.setAccessible(true);
        if (field.get(obj) == null) {
          String setVariableName = getSetFunctionName(field.getName());
          if (field.getType() == Integer.class) {
            invokeMethod(obj, setVariableName, new Object[] { Integer.valueOf(0) });
          } else if (field.getType() == Long.class) {
            invokeMethod(obj, setVariableName, new Object[] { Long.valueOf(0L) });
          } else if (field.getType() == Float.class) {
            invokeMethod(obj, setVariableName, new Object[] { Float.valueOf(0.0F) });
          } else if (field.getType() == Double.class) {
            invokeMethod(obj, setVariableName, new Object[] { Double.valueOf(0.0D) });
          } 
        } 
      } catch (Exception e) {
        logger.warn("", e);
      } 
      b++;
    } 
    for (i = (arrayOfField1 = superFields).length, b = 0; b < i; ) {
      Field field = arrayOfField1[b];
      try {
        field.setAccessible(true);
        if (field.get(obj) == null) {
          String setVariableName = getSetFunctionName(field.getName());
          if (field.getType() == Integer.class) {
            invokeMethod(obj, setVariableName, new Object[] { Integer.valueOf(0) });
          } else if (field.getType() == Long.class) {
            invokeMethod(obj, setVariableName, new Object[] { Long.valueOf(0L) });
          } else if (field.getType() == Float.class) {
            invokeMethod(obj, setVariableName, new Object[] { Float.valueOf(0.0F) });
          } else if (field.getType() == Double.class) {
            invokeMethod(obj, setVariableName, new Object[] { Double.valueOf(0.0D) });
          } 
        } 
      } catch (Exception e) {
        logger.warn("", e);
      } 
      b++;
    } 
  }
  
  public static void updateObjectValue(Object toUpdateObj, Object obj2) {
    if (toUpdateObj == null || obj2 == null)
      return; 
    Class<? extends Object> cls = (Class)toUpdateObj.getClass();
    while (cls.getSuperclass() != null) {
      Method[] methods = cls.getDeclaredMethods();
      byte b;
      int i;
      Method[] arrayOfMethod1;
      for (i = (arrayOfMethod1 = methods).length, b = 0; b < i; ) {
        Method method = arrayOfMethod1[b];
        String methodName = method.getName();
        if (methodName.startsWith("set")) {
          String getFuncName = "g" + methodName.substring(1);
          Method getMethod = getMethod(obj2, getFuncName, (Class<? extends Object>[])new Class[0]);
          if (getMethod != null)
            try {
              Object obj = getMethod.invoke(obj2, new Object[0]);
              if (obj != null)
                invokeMethod(toUpdateObj, methodName, new Object[] { obj }); 
            } catch (Exception exception) {} 
        } 
        b++;
      } 
      cls = (Class)cls.getSuperclass();
    } 
  }
  
  public static void setFieldValue(Object obj, String field, Object value) {
    if (obj == null)
      return; 
    if (obj instanceof Map) {
      ((Map<String, Object>)obj).put(field, value);
      return;
    } 
    try {
      Class<?> cls = null;
      if (value != null)
        cls = value.getClass(); 
      Map<String, String> fieldNames = getObjectFieldsNameMap(obj.getClass());
      String fieldName = fieldNames.get(StringUtil.getLetterOrDigit(field).toLowerCase());
      String setMethodName = getSetFunctionName(fieldName);
      Method method = getMethod(obj, setMethodName, (Class<? extends Object>[])new Class[] { cls });
      if (method != null) {
        Object v = convertValue(value, method.getParameterTypes()[0]);
        method.setAccessible(true);
        method.invoke(obj, new Object[] { v });
      } 
    } catch (Exception e) {
      logger.warn("", e);
    } 
  }
  
  private static Map<String, Map<String, String>> fieldsNameMap = new ConcurrentHashMap<String, Map<String, String>>();
  
  private static Map<String, String> getObjectFieldsNameMap(Class<?> cls) {
    Map<String, String> fieldNames = fieldsNameMap.get(cls.getName());
    if (fieldNames == null) {
      fieldNames = new HashMap<String, String>();
      List<String> fields = getObjectFields(cls);
      for (String field : fields)
        fieldNames.put(StringUtil.getLetterOrDigit(field).toLowerCase(), field); 
      fieldsNameMap.put(cls.getName(), fieldNames);
    } 
    return fieldNames;
  }
  
  private static Map<String, List<String>> fieldsMap = new ConcurrentHashMap<String, List<String>>();
  
  public static List<String> getObjectFields(Class<?> cls) {
    List<String> fields = fieldsMap.get(cls.getName());
    if (fields != null)
      return fields; 
    fields = new ArrayList<String>();
    while (cls.getSuperclass() != null) {
      Method[] methods = cls.getDeclaredMethods();
      Field[] fs = cls.getDeclaredFields();
      byte b;
      int i;
      Field[] arrayOfField1;
      for (i = (arrayOfField1 = fs).length, b = 0; b < i; ) {
        Field f = arrayOfField1[b];
        if (!f.getName().startsWith("this$") && !fields.contains(f.getName()))
          fields.add(f.getName()); 
        b++;
      } 
      Method[] arrayOfMethod1;
      for (i = (arrayOfMethod1 = methods).length, b = 0; b < i; ) {
        Method method = arrayOfMethod1[b];
        String methodName = method.getName();
        if (methodName.startsWith("get")) {
          String fieldName = methodName.replace("get", "");
          if (!StringUtil.isEmpty(fieldName)) {
            fieldName = String.valueOf(Character.toLowerCase(fieldName.charAt(0))) + fieldName.substring(1);
            if (!fields.contains(fieldName))
              fields.add(fieldName); 
          } 
        } 
        b++;
      } 
      cls = cls.getSuperclass();
    } 
    fieldsMap.put(cls.getName(), fields);
    return fields;
  }
  
  public static Object getObjectFieldValue(Object obj, String field) {
    if (obj == null || field == null)
      return null; 
    field = StringUtil.getLetterOrDigit(field);
    try {
      Class<? extends Object> cls = (Class)obj.getClass();
      while (cls != null && cls != Object.class) {
        Field[] fields = cls.getDeclaredFields();
        byte b;
        int i;
        Field[] arrayOfField1;
        for (i = (arrayOfField1 = fields).length, b = 0; b < i; ) {
          Field f = arrayOfField1[b];
          f.setAccessible(true);
          if (StringUtil.getLetterOrDigit(f.getName()).equalsIgnoreCase(field))
            return f.get(obj); 
          b++;
        } 
        cls = (Class)cls.getSuperclass();
      } 
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
    return null;
  }
  
  public static List<Entry<String, Object>> getValidFieldValues(Object obj) {
    List<Entry<String, Object>> validValues = new ArrayList<Entry<String, Object>>();
    if (obj == null)
      return validValues; 
    Set<String> nameSet = new HashSet<String>();
    Class<? extends Object> cls = (Class)obj.getClass();
    while (cls.getSuperclass() != null) {
      Method[] methods = cls.getDeclaredMethods();
      byte b;
      int i;
      Method[] arrayOfMethod1;
      for (i = (arrayOfMethod1 = methods).length, b = 0; b < i; ) {
        Method method = arrayOfMethod1[b];
        String methodName = method.getName();
        if (methodName.startsWith("get"))
          try {
            Object value = method.invoke(obj, new Object[0]);
            String fieldName = methodName.replace("get", "");
            if (!StringUtil.isEmpty(fieldName)) {
              fieldName = String.valueOf(Character.toLowerCase(fieldName.charAt(0))) + fieldName.substring(1);
              if (value != null && 
                !nameSet.contains(fieldName.toUpperCase())) {
                nameSet.add(fieldName.toUpperCase());
                validValues.add(new Entry<String, Object>(fieldName, value));
              } 
            } 
          } catch (Exception exception) {} 
        b++;
      } 
      cls = (Class)cls.getSuperclass();
    } 
    return validValues;
  }
  
  public static String ObjectToString(Object obj) {
    if (obj == null)
      return null; 
    Set<String> nameSet = new HashSet<String>();
    StringBuffer querySB = new StringBuffer();
    Class<? extends Object> cls = (Class)obj.getClass();
    while (cls.getSuperclass() != null) {
      Field[] fields = cls.getDeclaredFields();
      int count = 0;
      Method[] methods = cls.getDeclaredMethods();
      byte b;
      int i;
      Method[] arrayOfMethod1;
      for (i = (arrayOfMethod1 = methods).length, b = 0; b < i; ) {
        Method method = arrayOfMethod1[b];
        String methodName = method.getName();
        if (methodName.startsWith("get"))
          try {
            Object value = method.invoke(obj, new Object[0]);
            String fieldName = methodName.replace("get", "");
            if (!StringUtil.isEmpty(fieldName)) {
              fieldName = String.valueOf(Character.toLowerCase(fieldName.charAt(0))) + fieldName.substring(1);
              if (value != null && 
                !nameSet.contains(fieldName.toUpperCase())) {
                nameSet.add(fieldName.toUpperCase());
                if (count++ > 0)
                  querySB.append("    "); 
                querySB.append(fieldName).append(": ").append(String.valueOf(value));
              } 
            } 
          } catch (Exception exception) {} 
        b++;
      } 
      Field[] arrayOfField1;
      for (i = (arrayOfField1 = fields).length, b = 0; b < i; ) {
        Field field = arrayOfField1[b];
        try {
          field.setAccessible(true);
          if (!Modifier.isStatic(field.getModifiers()))
            if (!nameSet.contains(field.getName().toUpperCase())) {
              nameSet.add(field.getName().toUpperCase());
              if (count++ > 0)
                querySB.append("    "); 
              querySB.append(field.getName()).append(": ").append(String.valueOf(field.get(obj)));
            }  
        } catch (Exception e) {
          logger.warn("", e);
        } 
        b++;
      } 
      cls = (Class)cls.getSuperclass();
    } 
    return querySB.toString();
  }
  
  public static Object invokeMethod(Object obj, String methodName, Object... params) {
    if (obj == null || methodName == null)
      return null; 
    Class[] parameterTypes = null;
    if (params != null && params.length > 0) {
      List<Class<? extends Object>> typeList = new ArrayList<Class<? extends Object>>();
      byte b;
      int i;
      Object[] arrayOfObject;
      for (i = (arrayOfObject = params).length, b = 0; b < i; ) {
        Object paramObj = arrayOfObject[b];
        if (paramObj != null) {
          Class<? extends Object> c = (Class)paramObj.getClass();
          typeList.add(c);
        } 
        b++;
      } 
      parameterTypes = new Class[typeList.size()];
      typeList.toArray((Class<?>[])parameterTypes);
    } 
    try {
      Method method = null;
      if (parameterTypes == null) {
        method = getMethod(obj, methodName, (Class<? extends Object>[])new Class[0]);
      } else {
        method = getMethod(obj, methodName, (Class<? extends Object>[])parameterTypes);
      } 
      if (method == null)
        return null; 
      method.setAccessible(true);
      return method.invoke(obj, params);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  private static Method getMethod(Object obj, String methodName, Class... parameterTypes) {
    try {
      Class<? extends Object> cls = (Class)obj.getClass();
      while (cls.getSuperclass() != null) {
        Method[] methods = cls.getDeclaredMethods();
        String internedName = methodName.intern();
        for (int i = 0; i < methods.length; i++) {
          Method m = methods[i];
          if (m.getName().equals(internedName))
            if (arrayContentsEq((Object[])parameterTypes, (Object[])m.getParameterTypes()))
              return m;  
        } 
        cls = (Class)cls.getSuperclass();
      } 
    } catch (Exception exception) {}
    return null;
  }
  
  private static boolean arrayContentsEq(Object[] a1, Object[] a2) {
    if (a1 == null || a2 == null)
      return false; 
    if (a1.length != a2.length)
      return false; 
    return true;
  }
  
  protected static String getSetFunctionName(String variablNname) {
    String strHead = variablNname.substring(0, 1);
    String strTail = variablNname.substring(1, variablNname.length());
    String strRetval = "set" + strHead.toUpperCase() + strTail;
    return strRetval;
  }
  
  protected static String getGetFunctionName(String variablNname) {
    String strHead = variablNname.substring(0, 1);
    String strTail = variablNname.substring(1, variablNname.length());
    String strRetval = "get" + strHead.toUpperCase() + strTail;
    return strRetval;
  }
}