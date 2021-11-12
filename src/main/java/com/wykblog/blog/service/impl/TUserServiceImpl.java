package com.wykblog.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wykblog.blog.entity.TUser;
import com.wykblog.blog.mapper.TUserMapper;
import com.wykblog.blog.service.TUserService;
import com.wykblog.blog.utils.RespBean;
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
    public RespBean check(String username) {
        RespBean respBean = RespBean.build();
        respBean.setMsg("用户名是否存在");
        respBean.setStatus(200);
        respBean.setObj(Objects.isNull(tUserMapper.loadUserByUsername(username)));
        return respBean;
    }

    @Override
    public RespBean addUser(TUser tUser) {
        RespBean respBean = RespBean.build();
        tUser.setCreateTime(LocalDateTime.now());
        tUser.setAvatar("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2070453827,1163403148&fm=26&gp=0.jpg");
        tUser.setNickname(tUser.getUsername());
        tUser.setType(1);
        if (tUserMapper.save(tUser) == 1) {
            respBean.setMsg("注册账号成功");
            respBean.setStatus(200);
            return respBean;
        }
        respBean.setMsg("注册账号失败");
        return respBean;
    }
}
