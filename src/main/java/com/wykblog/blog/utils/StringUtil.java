package com.wykblog.blog.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtil {
    private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

    public static final String EMPTY_STRING = "";

    private static final String AS = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String convertEncode(String strIn, String encoding, String targetEncoding) {
        String strOut = strIn;
        if (strIn == null)
            return strOut;
        try {
            if (encoding != null && targetEncoding != null) {
                strOut = new String(strIn.getBytes(encoding), targetEncoding);
            } else if (encoding != null) {
                strOut = new String(strIn.getBytes(encoding));
            } else if (targetEncoding != null) {
                strOut = new String(strIn.getBytes(), targetEncoding);
            } else {
                return strOut;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("Unsupported Encoding: " + encoding);
        }
        return strOut;
    }

    public static String extractString(String str, String startStr, String endStr) {
        if (isEmpty(str))
            return str;
        if (startStr == null)
            startStr = "";
        int startIdx = 0;
        startIdx = str.indexOf(startStr);
        if (startIdx == -1) {
            startIdx = 0;
        } else {
            startIdx += startStr.length();
        }
        int endIdx = str.length();
        if (endStr != null) {
            endIdx = str.indexOf(endStr, startIdx);
            if (endIdx == -1)
                endIdx = str.length();
        }
        return str.substring(startIdx, endIdx);
    }

    public static String replace(String text, String repl, String with) {
        return replace(text, repl, with, -1);
    }

    public static String replaceSpace(String text) {
        return replace(text, " ", "");
    }

    public static String replace(String text, String repl, String with, int max) {
        if (text == null || repl == null || with == null || repl.length() == 0 || max == 0)
            return text;
        StringBuffer buf = new StringBuffer(text.length());
        int start = 0;
        int end = 0;
        while ((end = text.indexOf(repl, start)) != -1) {
            buf.append(text.substring(start, end)).append(with);
            start = end + repl.length();
            if (--max == 0)
                break;
        }
        if (start == 0)
            return text;
        buf.append(text.substring(start));
        return buf.toString();
    }

    public static String trim(String str) {
        return trim(str, null, 0);
    }

    public static String trim(String str, String stripChars) {
        return trim(str, stripChars, 0);
    }

    public static String trimStart(String str) {
        return trim(str, null, -1);
    }

    public static String trimStart(String str, String stripChars) {
        return trim(str, stripChars, -1);
    }

    public static String trimEnd(String str) {
        return trim(str, null, 1);
    }

    public static String trimEnd(String str, String stripChars) {
        return trim(str, stripChars, 1);
    }

    public static String trimToNull(String str) {
        return trimToNull(str, null);
    }

    public static String trimToNull(String str, String stripChars) {
        String result = trim(str, stripChars);
        if (result == null || result.length() == 0)
            return null;
        return result;
    }

    public static String trimToEmpty(String str) {
        return trimToEmpty(str, null);
    }

    public static String trimToEmpty(String str, String stripChars) {
        String result = trim(str, stripChars);
        if (result == null)
            return "";
        return result;
    }

    private static String trim(String str, String stripChars, int mode) {
        if (str == null)
            return null;
        int length = str.length();
        int start = 0;
        int end = length;
        if (mode <= 0)
            if (stripChars == null) {
                while (start < end && Character.isWhitespace(str.charAt(start)))
                    start++;
            } else {
                if (stripChars.length() == 0)
                    return str;
                while (start < end && stripChars.indexOf(str.charAt(start)) != -1)
                    start++;
            }
        if (mode >= 0)
            if (stripChars == null) {
                while (start < end && Character.isWhitespace(str.charAt(end - 1)))
                    end--;
            } else {
                if (stripChars.length() == 0)
                    return str;
                while (start < end && stripChars.indexOf(str.charAt(end - 1)) != -1)
                    end--;
            }
        if (start > 0 || end < length)
            return str.substring(start, end);
        return str;
    }

    public static boolean hasLength(String str) {
        return (str != null && str.length() > 0);
    }

    public static boolean hasText(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0)
            return false;
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i)))
                return true;
        }
        return false;
    }

    public static boolean hasText(StringBuffer str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0)
            return false;
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i)))
                return true;
        }
        return false;
    }

    public static String[] commaDelimitedListToStringArray(String str) {
        return delimitedListToStringArray(str, ",");
    }

    public static String[] delimitedListToStringArray(String str, String delimiter) {
        if (str == null)
            return new String[0];
        if (delimiter == null)
            return new String[]{str};
        List<String> result = new ArrayList<String>();
        if ("".equals(delimiter)) {
            for (int i = 0; i < str.length(); i++)
                result.add(str.substring(i, i + 1));
        } else {
            int pos = 0;
            int delPos = 0;
            while ((delPos = str.indexOf(delimiter, pos)) != -1) {
                result.add(str.substring(pos, delPos));
                pos = delPos + delimiter.length();
            }
            if (str.length() > 0 && pos <= str.length())
                result.add(str.substring(pos));
        }
        return toStringArray(result);
    }

    public static String[] toStringArray(Collection<String> collection) {
        if (collection == null)
            return null;
        return collection.<String>toArray(new String[collection.size()]);
    }

    public static boolean isEmpty(String s) {
        return !(s != null && s.trim().length() != 0);
    }

    public static boolean isAnyEmpty(String... s) {
        byte b;
        int i;
        String[] arrayOfString;
        for (i = (arrayOfString = s).length, b = 0; b < i; ) {
            String str = arrayOfString[b];
            if (isEmpty(str))
                return false;
            b++;
        }
        return true;
    }

    public static boolean isAllEmpty(String... s) {
        byte b;
        int i;
        String[] arrayOfString;
        for (i = (arrayOfString = s).length, b = 0; b < i; ) {
            String str = arrayOfString[b];
            if (isNotEmpty(str))
                return false;
            b++;
        }
        return true;
    }

    public static boolean isNotEmpty(String s) {
        return (s != null && s.trim().length() != 0);
    }

    public static String reverse(String s) {
        if (isEmpty(s))
            return s;
        StringBuilder sb = new StringBuilder(s);
        return sb.reverse().toString();
    }

    public static String escapeHtml(String str) {
        if (str == null)
            return "";
        str = str.replace(">", "&gt;");
        str = str.replace("<", "&lt;");
        return str;
    }

    public static String limitString(String s, int byteLength) {
        return limitString(s, byteLength, "...");
    }

    public static String limitString(String s, int byteLength, String omit) {
        if (s == null)
            return null;
        if (byteLength <= 0)
            return "";
        if ((s.getBytes()).length <= byteLength)
            return s;
        String r = "";
        for (int i = 0; i < s.length(); i++) {
            String tmp = s.substring(i, i + 1);
            if ((r.getBytes()).length + (tmp.getBytes()).length > byteLength)
                break;
            r = String.valueOf(r) + tmp;
        }
        if (omit != null)
            r = String.valueOf(r) + omit;
        return r;
    }

    public static String getPatternMatchStr(String src, String pattern) {
        if (src == null)
            return null;
        try {
            Pattern p = Pattern.compile(pattern);
            Matcher matcher = p.matcher(src);
            if (matcher.find())
                return matcher.group();
        } catch (Exception e) {
            logger.warn("", e);
        }
        return null;
    }

    public static String getRandomString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            char c = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt((int) (Math.random() * "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ".length()));
            sb.append(c);
        }
        return sb.toString().toUpperCase();
    }

    public static String toSize(String s, int byteLength, String omit) {
        if (byteLength <= 0)
            return "";
        if (s == null)
            s = "";
        if (omit == null)
            omit = "...";
        int omitSize = (omit.getBytes()).length;
        if ((s.getBytes()).length > byteLength)
            if (byteLength < omitSize) {
                s = limitString(s, byteLength);
            } else {
                s = limitString(s, byteLength - omitSize, omit);
            }
        while ((s.getBytes()).length + omitSize <= byteLength)
            s = String.valueOf(s) + omit;
        return s;
    }

    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str))
            return null;
        String firstLetter = str.substring(0, 1);
        String result = String.valueOf(firstLetter.toUpperCase()) + str.substring(1);
        return result;
    }

    public static String lowerFirstLetter(String str) {
        if (isEmpty(str))
            return null;
        String firstLetter = str.substring(0, 1);
        String result = String.valueOf(firstLetter.toLowerCase()) + str.substring(1);
        return result;
    }

    public static boolean isAsciiStr(String str) {
        if (str == null)
            return false;
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (str.charAt(i) > 'y')
                return false;
        }
        return true;
    }

    public static boolean isNumeric(String str) {
        if (str == null)
            return false;
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }

    public static String toLowerCase(String str) {
        if (str == null)
            return null;
        return str.toLowerCase();
    }

    public static int countMatches(String str, String subStr) {
        if (str == null || str.length() == 0 || subStr == null || subStr.length() == 0)
            return 0;
        int count = 0;
        int index = 0;
        while ((index = str.indexOf(subStr, index)) != -1) {
            count++;
            index += subStr.length();
        }
        return count;
    }

    public static String readLine(InputStreamReader inputReader) throws IOException {
        StringBuffer sb = new StringBuffer();
        int num = 0;
        int n;
        while ((n = inputReader.read()) > 0) {
            num++;
            char c = (char) n;
            if (c == '\n' || c == '\r')
                break;
            sb.append(c);
        }
        if (num == 0)
            return null;
        return sb.toString();
    }

    public static String getResource(String uri) {
        if (isEmpty(uri))
            return "";
        if (uri.contains("."))
            return uri.split("\\.")[0];
        return uri;
    }

    public static String getExtension(String uri) {
        if (isEmpty(uri))
            return "";
        if (uri.contains(".")) {
            String[] strs = uri.split("\\.");
            if (strs.length > 1) {
                String extension = strs[1];
                if (isNotEmpty(extension))
                    return extension;
            }
        }
        return "";
    }

    public static String getFileExtName(String file) {
        if (isEmpty(file))
            return null;
        int idx = file.lastIndexOf(".");
        if (idx < 0)
            return file;
        return file.substring(idx + 1);
    }

    public static String formatString(String str, Object... obj) {
        return String.format(str, obj);
    }

    public static String lastSubStr(String str, int len) {
        if (str == null)
            return null;
        if (str.length() <= len)
            return str;
        return str.substring(str.length() - len, str.length());
    }

    public static String filterAsciiStr(String str) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c > 'y')
                sb.append(c);
        }
        return sb.toString();
    }

    public static String getAsciiStr(String str) {
        if (str == null)
            return null;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c > 'y')
            sb.append(c);
        }
        return trim(sb.toString());
    }

    public static String getLetterOrDigit(String str) {
        if (str == null)
            return "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isLetterOrDigit(c))
                sb.append(c);
        }
        return sb.toString();
    }

    public static boolean isPhone(String phone) {
        if (isEmpty(phone))
            return false;
        try {
            Pattern p = null;
            Matcher m = null;
            boolean b = false;
            p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
            m = p.matcher(phone);
            b = m.matches();
            return b;
        } catch (Exception e) {
            return false;
        }
    }

    public static String escapeQueryChars(String s) {
        if (isEmpty(s))
            return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\' || c == '+' || c == '-' || c == '!' || c == '(' || c == ')' || c == ':' ||
                    c == '^' || c == '[' || c == ']' || c == '"' || c == '{' || c == '}' || c == '~' ||
                    c == '*' || c == '?' || c == '|' || c == '&' || c == ';' || c == '/' ||
                    Character.isWhitespace(c))
                sb.append('\\');
            sb.append(c);
        }
        return sb.toString();
    }

    public static String filterUnknownChar(String str) {
        if (isEmpty(str))
            return str;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            LanguageType type = LanguageUtils.getCharType(str.charAt(i));
            if (type != LanguageType.OTHER)
                sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    public static String genArrayInSql(String... strings) {
        if (strings == null || strings.length == 0)
            return null;
        StringBuilder sb = new StringBuilder();
        byte b;
        int i;
        String[] arrayOfString;
        for (i = (arrayOfString = strings).length, b = 0; b < i; ) {
            String string = arrayOfString[b];
            sb.append(",'").append(string).append("'");
            b++;
        }
        return sb.substring(1);
    }

    public static String trimCtrlChars(String str) {
        if (isEmpty(str))
            return str;
        boolean containCtrlChar = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < ' ' && c != '\n' && c != '\r')
                containCtrlChar = true;
        }
        if (containCtrlChar) {
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < str.length(); j++) {
                char c = str.charAt(j);
                if (c >= ' ' || c == '\n' || c == '\r')
                    sb.append(c);
            }
            str = sb.toString();
        }
        return str;
    }
}