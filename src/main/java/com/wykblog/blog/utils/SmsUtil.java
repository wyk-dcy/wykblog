package com.wykblog.blog.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

/**
 * @author wuyongkang
 * @date 2021年11月12日 11:33
 */
public class SmsUtil {

    public static void sendPhoneSms(String telephone, JSONObject params) {
        try {
            HttpClient client = new HttpClient();
            PostMethod post = new PostMethod("http://gbk.api.smschinese.cn");
            post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
            NameValuePair[] data = {new NameValuePair("Uid", "wuyongkang"), new NameValuePair("Key", "d41d8cd98f00b204e980"),
                    new NameValuePair("smsMob", telephone), new NameValuePair("smsText", "吴永康测试验证" + params.getString("code"))};
            post.setRequestBody(data);

            client.executeMethod(post);
            Header[] headers = post.getResponseHeaders();
            int statusCode = post.getStatusCode();
            System.out.println("statusCode:" + statusCode);
            for (Header h : headers) {
                System.out.println(h.toString());
            }
            String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
            System.out.println(result); //打印返回消息状态
            post.releaseConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}