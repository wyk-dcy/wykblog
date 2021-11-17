package com.wykblog.blog.service;

import com.wykblog.blog.entity.TUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wykblog.blog.utils.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 关注公众号：小L星光
 * @since 2020-11-30
 */

public interface TUserService extends IService<TUser>  {

    boolean check(String username);

    void addUser(TUser tUser);

    TUser login(String username, String password);
}
