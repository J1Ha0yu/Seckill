package com.j1.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName DemoController
 * @Description 测试
 * @Author J1
 * @Date DATE{TIME}
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
    /**
     * 用于测试页面跳转
     * @param model
     * @return
     */

    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("name","J1");
        return "hello";
    }
}
