package com.wykblog.blog.manage;


public interface MessageManage {
    void sendRelayMessage(String telephone, int codeLength, int expireTime);

    Boolean checkVerifyCode(String telephone, String verifyCode);
}
