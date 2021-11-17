package com.wykblog.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wykblog.blog.entity.TUser;
import com.wykblog.blog.mapper.TUserMapper;
import com.wykblog.blog.service.TUserService;
import com.wykblog.blog.utils.RespBean;
import com.wykblog.blog.utils.exception.ErrorCodes;
import com.wykblog.blog.utils.exception.Exceptions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 关注公众号：小L星光
 * @since 2020-11-30
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUserService {
    @Resource
    TUserMapper tUserMapper;


    @Override
    public boolean check(String username) {
        return Objects.isNull(tUserMapper.loadUserByUsername(username));
    }

    @Override
    public void addUser(TUser tUser) {
        tUser.setCreateTime(LocalDateTime.now());
        tUser.setUpdateTime(LocalDateTime.now());
        tUser.setAvatar("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2070453827,1163403148&fm=26&gp=0.jpg");
        tUser.setNickname(tUser.getUsername());
        tUser.setType(1);
        if (tUserMapper.insert(tUser) != 1) {
            throw Exceptions.fail(ErrorCodes.PARAM_ERROR, "请先绑定手机号码");
        }
    }

    @Override
    public TUser login(String username, String password) {
        if(Objects.isNull(tUserMapper.getUser(username,password))){
            throw Exceptions.fail(ErrorCodes.PARAM_ERROR, "用户不存在");
        }
        return tUserMapper.getUser(username,password);
    }
}
