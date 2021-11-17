package com.wykblog.blog.utils;

import java.io.IOException;
import java.io.InputStream;

public class FP63 {
  public static final long ZEOR_FP63 = 0L;
  
  private static final long IRRED_POLY = -6270768363846673008L;
  
  private static final long X63 = 1L;
  
  public static long newFP63() {
    return -6270768363846673008L;
  }
  
  public static long newFP63(String s) {
    if (s == null)
      return 0L; 
    return extendFP63(-6270768363846673008L, s) >> 1L;
  }
  
  public static long newFP63(char[] c) {
    return extendFP63(-6270768363846673008L, c, 0, c.length) >> 1L;
  }
  
  public static long newFP63(char[] c, int off, int len) {
    return extendFP63(-6270768363846673008L, c, off, len) >> 1L;
  }
  
  public static long newFP63(byte[] bytes) {
    return extendFP63(-6270768363846673008L, bytes, 0, bytes.length) >> 1L;
  }
  
  public static long newFP63(InputStream is) throws IOException {
    return extendFP63(-6270768363846673008L, is) >> 1L;
  }
  
  public static long extendFP63(long fp, String s) {
    long[] mod = byteModeTable7;
    int mask = 255;
    int len = s.length();
    for (int i = 0; i < len; i++) {
      char c = s.charAt(i);
      fp = fp >>> 8L ^ mod[(c ^ (int)fp) & 0xFF];
      if (c > 'y')
        fp = fp >>> 8L ^ mod[(c >> 8 ^ (int)fp) & 0xFF]; 
    } 
    if ((fp & 0x1L) != 0L)
      fp ^= 0xCF517E46C7CE691FL; 
    return fp;
  }
  
  public static long extendFP63(long fp, char[] chars, int start, int len) {
    long[] mod = byteModeTable7;
    int end = start + len;
    for (int i = start; i < end; i++)
      fp = fp >>> 8L ^ mod[(chars[i] ^ (int)fp) & 0xFF]; 
    if ((fp & 0x1L) != 0L)
      fp ^= 0xCF517E46C7CE691FL; 
    return fp;
  }
  
  public static long extendFP63(long fp, byte[] bytes, int start, int len) {
    long[] mod = byteModeTable7;
    int end = start + len;
    for (int i = start; i < end; i++)
      fp = fp >>> 8L ^ mod[(bytes[i] ^ (int)fp) & 0xFF]; 
    if ((fp & 0x1L) != 0L)
      fp ^= 0xCF517E46C7CE691FL; 
    return fp;
  }
  
  public static long extendFP63(long fp, InputStream is) throws IOException {
    long[] mod = byteModeTable7;
    int mask = 255;
    int i;
    while ((i = is.read()) != -1)
      fp = fp >>> 8L ^ mod[(i ^ (int)fp) & 0xFF]; 
    if ((fp & 0x1L) != 0L)
      fp ^= 0xCF517E46C7CE691FL; 
    return fp;
  }
  
  public static int hashFP63(long fp) {
    return (int)fp;
  }

  private static
    long[] powerTable = { 
        Long.MIN_VALUE, 4611686018427387904L, 2305843009213693952L, 
        1152921504606846976L, 576460752303423488L, 288230376151711744L, 144115188075855872L, 
        72057594037927936L, 36028797018963968L, 18014398509481984L, 
        9007199254740992L, 4503599627370496L, 
        2251799813685248L, 1125899906842624L, 562949953421312L, 281474976710656L, 140737488355328L, 
        70368744177664L, 35184372088832L, 17592186044416L, 
        8796093022208L, 4398046511104L, 
        2199023255552L, 1099511627776L, 549755813888L, 274877906944L, 137438953472L, 68719476736L, 
        34359738368L, 17179869184L, 
        8589934592L, 4294967296L, 2147483648L, 
        1073741824L, 536870912L, 268435456L, 134217728L, 67108864L, 33554432L, 16777216L, 
        8388608L, 4194304L, 
        2097152L, 1048576L, 524288L, 262144L, 131072L, 65536L, 32768L, 16384L, 
        8192L, 4096L, 2048L, 
        1024L, 512L, 256L, 128L, 64L, 32L, 16L, 
        8L, 4L, 2L, 1L, 7469430140714824847L, 
        6087987854931439304L, 3043993927465719652L, 1521996963732859826L, 760998481866429913L, 7129041153085046371L, 
        6259730490778192830L, 3129865245389096415L, 8223430870829512288L, 4111715435414756144L, 2055857717707378072L, 
        1027928858853689036L, 513964429426844518L, 256982214713422259L, 7377110424097664086L, 3688555212048832043L, 
        9092925519748153242L, 4546462759874076621L, 8657216758979771241L, 6609860662390761787L, 5365413898108772370L, 
        2682706949054386185L, 8445884016146177163L, 6715527037968341706L, 3357763518984170853L, 8134672129406771773L, 
        6907021796111869329L, 5207826921327904327L, 4866784108260136876L, 2433392054130068438L, 1216696027065034219L, 
        8059743938678662778L, 4029871969339331389L, 8961814409823657233L, 6451369631757449735L, 5434525458760051084L, 
        2717262729380025542L, 1358631364690012771L, 7982020252082995646L, 3991010126041497823L, 8942401097629127904L, 
        4471200548814563952L, 2235600274407281976L, 1117800137203640988L, 558900068601820494L, 279450034300910247L, 
        7374881930746776028L, 3687440965373388014L, 1843720482686694007L, 7738209051664993716L, 3869104525832496858L, 
        1934552262916248429L, 7693497982190206265L, 5965821384788011539L, 5678425993924886918L, 2839212996962443459L, 
        8366504713791210734L, 4183252356895605367L, 8840088665912587700L, 4420044332956293850L, 2210022166478146925L, 
        7565333355578954297L, 6041020821508357523L, 5639559054053870150L };

  
  private static long[] byteModeTable7 = new long[256];

  static {
    for (int j = 0; j <= 255; j++) {
      long v = 0L;
      for (int k = 0; k <= 7; k++) {
        if ((j & 1L << k) != 0L)
          v ^= powerTable[71 - k];
      } 
      byteModeTable7[j] = v;
    } 
  }
}