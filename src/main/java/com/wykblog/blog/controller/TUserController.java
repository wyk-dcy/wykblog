package com.wykblog.blog.controller;


import com.wykblog.blog.entity.TUser;
import com.wykblog.blog.service.TUserService;
import com.wykblog.blog.utils.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 关注公众号：小L星光
 * @since 2020-11-30
 */
@RestController
@RequestMapping("/user")
public class TUserController {
    @Autowired
    TUserService tUserService;

    @PostMapping("/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名"),
            @ApiImplicitParam(name = "password", value = "密码"),
    })
    public Result<TUser> login(String username, String password) {
        return Result.success(tUserService.login(username,password));
    }
}
