package com.j1.seckill.controller;

import com.j1.seckill.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @ClassName GoodsController
 * @Description 跳转到商品列表页面
 * @Author J1
 * @Date DATE{TIME}
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @RequestMapping("/toList")
    public String toList(HttpSession session, Model model, @CookieValue("userTicket") String ticket) {
        if (!StringUtils.hasText(ticket)) return "login";
        User user = (User) session.getAttribute(ticket);
        if(user==null) return "login";
        //将user传到前端去
        model.addAttribute("user",user);

        return "goodsList";
    }
}
