package com.wykblog.blog.utils;

public class LanguageUtils {
  private static final int UNICODE_CHAR_NUM = 65536;
  
  public static LanguageType[] lang = new LanguageType[65536];
  
  static {
    int i;
    for (i = 0; i < 65536; i++)
      lang[i] = LanguageType.OTHER; 
    for (i = 0; i < 255; i++);
    for (i = 48; i <= 57; i++)
      lang[i] = LanguageType.NUMBER; 
    for (i = 65; i < 90; i++)
      lang[i] = LanguageType.ENGLISH; 
    for (i = 97; i < 122; i++)
      lang[i] = LanguageType.ENGLISH; 
    for (i = 12288; i <= 12351; i++)
      lang[i] = LanguageType.CHINESE_SYMBEL; 
    for (i = 19968; i <= 40895; i++)
      lang[i] = LanguageType.CHINESE; 
    for (i = 12352; i <= 12543; i++)
      lang[i] = LanguageType.JAPANESE; 
    for (i = 12784; i <= 12799; i++)
      lang[i] = LanguageType.JAPANESE; 
  }
  
  public static LanguageType getCharType(char c) {
    return lang[c];
  }
  
  public static boolean isChinese(char c) {
    LanguageType type = getCharType(c);
    if (type == LanguageType.CHINESE)
      return true; 
    return false;
  }
  
  public static boolean isChineseOrEngNum(char c) {
    LanguageType type = getCharType(c);
    if (type == LanguageType.CHINESE || type == LanguageType.ENGLISH || type == LanguageType.NUMBER)
      return true; 
    return false;
  }
}
