package com.j1.seckill.controller;

import com.j1.seckill.service.IUserService;
import com.j1.seckill.vo.LoginVo;
import com.j1.seckill.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @ClassName LoginController
 * @Description 登录
 * @Author J1
 * @Date DATE{TIME}
 */

//@RestController  有页面跳转不能用它，要用Controller
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Autowired
    private IUserService userService;


    //    跳转到登录页
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }


//    登录功能
    @RequestMapping("/doLogin")
    @ResponseBody
    public RespBean doLogin(@Valid LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        log.info("{}",loginVo);
        return userService. doLogin(loginVo,request,response);
    }
}
