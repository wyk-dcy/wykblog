package com.wykblog.blog.controller;

import com.wykblog.blog.entity.TUser;
import com.wykblog.blog.manage.MessageManage;
import com.wykblog.blog.service.TUserService;
import com.wykblog.blog.utils.RespBean;
import com.wykblog.blog.utils.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuyongkang
 * @date 2021年11月11日 14:21
 */
@RestController
@RequestMapping("/blog")
public class RegisterController {
    @Autowired
    TUserService tUserService;

    @Autowired
    MessageManage messageManage;

    @PostMapping("/register")
    @ApiOperation("用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名"),
            @ApiImplicitParam(name = "password", value = "密码"),
            @ApiImplicitParam(name = "telephone", value = "手机号"),
            @ApiImplicitParam(name = "code", value = "验证码"),
    })
    public Result<String> register(String username, String password, String telephone, String code) {
        //respBean.setObj(messageManage.checkVerifyCode(telephone, code));
        TUser tUser = new TUser();
        tUser.setUsername(username);
        tUser.setPassword(password);
        tUser.setTelephone(telephone);
        tUserService.addUser(tUser);
        return Result.success(null);
    }

    @PostMapping("/check")
    @ApiOperation("检测用户名是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名"),
    })
    public Result<Boolean> check(String username) {
        return Result.success(tUserService.check(username));
    }

    @ApiOperation("发送验证码")
    @PostMapping("/sendVerifyCode")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "telephone", value = "手机号"),
    })
    public Result<String> sendVerifyCode(String telephone) {
        this.messageManage.sendRelayMessage(telephone, 6, 60);
        return Result.success(null);
    }

}