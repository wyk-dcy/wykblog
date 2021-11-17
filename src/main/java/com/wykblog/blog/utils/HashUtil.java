package com.wykblog.blog.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
  private static Logger logger = LoggerFactory.getLogger(HashUtil.class);
  
  private static char[] hexDigits = new char[] { 
      '0', '1', '2', '3', '4', 
      '5', '6', '7', '8', '9', 
      'A', 'B', 'C', 'D', 'E', 'F' };
  
  private static char[] charDigits = new char[] { 
      '0', '1', '2', '3', '4', 
      '5', '6', '7', '8', '9', 
      'A', 'B', 'C', 'D', 'E', 
      'F', 'G', 'H', 'I', 'J', 
      'K', 'L', 'M', 'N', 'O', 
      'P', 'Q', 'R', 'S', 'T', 
      'U', 'V', 'W', 'X', 'Y', 'Z', 
      'a', 'b', 'c', 'd', 
      'e', 
      'f', 'g', 'h', 'i', 'j', 
      'k', 'l', 'm', 'n', 
      'o', 
      'p', 'q', 'r', 's', 't', 
      'u', 'v', 'w', 'x', 
      'y', 'z' };
  
  public static String getUrlHashStr(String url, int len) {
    long hash = getFPHash(url);
    return convertToHashStr(hash, len);
  }
  
  public static String convertToHashStr(long hid, int len) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < len; i++) {
      char c = charDigits[(int)((hid & 0xFFL) % charDigits.length)];
      sb.append(c);
      hid >>= 6L;
    } 
    return sb.toString();
  }
  
  public static long getFPHash(String str) {
    return FP63.newFP63(str);
  }
  
  public static String getFileMd5(InputStream input) {
    try {
      MessageDigest mdInst = MessageDigest.getInstance("MD5");
      byte[] buffer = new byte[1024];
      int len = 0;
      try {
        while ((len = input.read(buffer)) > 0)
          mdInst.update(buffer, 0, len); 
      } catch (IOException iOException) {}
      input.close();
      byte[] digests = mdInst.digest();
      int j = digests.length;
      char[] str = new char[j * 2];
      int k = 0;
      for (int i = 0; i < j; i++) {
        byte byte0 = digests[i];
        str[k++] = hexDigits[byte0 >>> 4 & 0xF];
        str[k++] = hexDigits[byte0 & 0xF];
      } 
      return new String(str);
    } catch (Exception e) {
      logger.warn("", e);
      return "";
    } 
  }
  
  public static String getFileMd5(String file) {
    InputStream input = null;
    try {
      input = CommonUtil.openResourceInputStream(file);
      return getFileMd5(input);
    } catch (IOException e) {
      return "";
    } finally {
      if (input != null)
        try {
          input.close();
        } catch (IOException iOException) {} 
    } 
  }
  
  public static String getMd5(byte[] bytes) {
    try {
      MessageDigest mdInst = MessageDigest.getInstance("MD5");
      mdInst.update(bytes);
      byte[] digests = mdInst.digest();
      int j = digests.length;
      char[] str = new char[j * 2];
      int k = 0;
      for (int i = 0; i < j; i++) {
        byte byte0 = digests[i];
        str[k++] = hexDigits[byte0 >>> 4 & 0xF];
        str[k++] = hexDigits[byte0 & 0xF];
      } 
      return new String(str);
    } catch (NoSuchAlgorithmException e) {
      return "";
    } 
  }
  
  public static String getSHA1(String str) {
    return getSHA1(str.getBytes());
  }
  
  public static String getSHA1(byte[] bytes) {
    try {
      MessageDigest mdInst = MessageDigest.getInstance("SHA-1");
      mdInst.update(bytes);
      byte[] digests = mdInst.digest();
      int j = digests.length;
      char[] str = new char[j * 2];
      int k = 0;
      for (int i = 0; i < j; i++) {
        byte byte0 = digests[i];
        str[k++] = hexDigits[byte0 >>> 4 & 0xF];
        str[k++] = hexDigits[byte0 & 0xF];
      } 
      return new String(str);
    } catch (NoSuchAlgorithmException e) {
      return "";
    } 
  }
  
  public static String getMd5(String str) {
    return getMd5(str.getBytes());
  }
  
  public static long convertStringToLong(String str) {
    long l = 0L;
    for (int i = 0; i < 16; i++) {
      int c = Character.digit(str.charAt(i), 16);
      l = l << 4L | (c & 0xF);
    } 
    return l;
  }
  
  public static String convertLongToString(long l) {
    char[] str = new char[16];
    for (int i = 0; i < 16; i++) {
      int idx = (int)(l & 0xFL);
      l >>>= 4L;
      str[15 - i] = hexDigits[idx];
    } 
    return new String(str);
  }

  public static void main(String[] args) {
    System.out.println(getUrlHashStr("a", 7));
    System.out.println(getUrlHashStr("ab", 7));
    System.out.println(getUrlHashStr("ac", 7));
    System.out.println(getUrlHashStr("ad", 7));
    System.out.println(getUrlHashStr("ae", 7));
  }
}