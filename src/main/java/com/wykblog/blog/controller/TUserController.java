package com.wykblog.blog.controller;


import com.wykblog.blog.utils.RespBean;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：小L星光
 * @since 2020-11-30
 */
@RestController
public class TUserController {

    @GetMapping("/hello")
    public RespBean test() {
        return RespBean.ok("hello");
    }
}
