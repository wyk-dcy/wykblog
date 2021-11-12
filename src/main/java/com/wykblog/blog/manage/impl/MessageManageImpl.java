package com.wykblog.blog.manage.impl;

import com.alibaba.fastjson.JSONObject;
import com.wykblog.blog.manage.MessageManage;
import com.wykblog.blog.utils.ConstantUtil;
import com.wykblog.blog.utils.RandomizationUtil;
import com.wykblog.blog.utils.RespBean;
import com.wykblog.blog.utils.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author wuyongkang
 * @date 2021年11月12日 9:39
 */
@Service
public class MessageManageImpl implements MessageManage {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void sendRelayMessage(String telephone, int codeLength, int expireTime) {
        try {
            Integer verifyCode = RandomizationUtil.generateValidateCode(codeLength);
            String cacheKey = String.format(ConstantUtil.BIOG_PREFIX,
                    telephone);
            redisTemplate.opsForValue().set(cacheKey, verifyCode, expireTime);
            JSONObject params = new JSONObject();
            params.put("code", verifyCode);
            SmsUtil.sendPhoneSms(telephone, params);
        } catch (Exception e) {
            return;
        }
    }

    @Override
    public Boolean checkVerifyCode(String telephone, String verifyCode) {
        String cacheKey = String.format(ConstantUtil.BIOG_PREFIX,
                telephone);
        return redisTemplate.opsForValue().get(cacheKey).equals(verifyCode);
    }
}