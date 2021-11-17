package com.wykblog.blog.utils;

import java.util.UUID;

public class UUIDUtil {
  public static String getID() {
    UUID uuid = UUID.randomUUID();
    return String.valueOf(HashUtil.convertToHashStr(uuid.getMostSignificantBits(), 5)) + 
      HashUtil.convertToHashStr(uuid.getLeastSignificantBits(), 5);
  }
  
  public static String convertID(String uuidStr) {
    UUID uuid = UUID.fromString(uuidStr);
    return String.valueOf(HashUtil.convertToHashStr(uuid.getMostSignificantBits(), 5)) + 
      HashUtil.convertToHashStr(uuid.getLeastSignificantBits(), 5);
  }
  
  public static void main(String[] args) {
    System.out.println(convertID("d64cfa7e-8d19-4054-82b4-9e5707f55633"));
  }
}