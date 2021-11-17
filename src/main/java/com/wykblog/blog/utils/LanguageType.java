package com.wykblog.blog.utils;

public enum LanguageType {
  ENGLISH(0),
  CHINESE(1),
  CHINESE_TRADITIONAL(2),
  JAPANESE(3),
  OTHER(4),
  CHINESE_SYMBEL(5),
  ENG_SYMBEL(6),
  NUMBER(7);
  
  private final int code;
  
  LanguageType(int code) {
    this.code = code;
  }
  
  public int getCode() {
    return this.code;
  }
  
  public static String toString(int code) {
    if (code == ENGLISH.code)
      return ENGLISH.name(); 
    if (code == CHINESE.code)
      return CHINESE.name(); 
    if (code == CHINESE_TRADITIONAL.code)
      return CHINESE_TRADITIONAL.name(); 
    if (code == JAPANESE.code)
      return JAPANESE.name(); 
    if (code == OTHER.code)
      return OTHER.name(); 
    return null;
  }
}