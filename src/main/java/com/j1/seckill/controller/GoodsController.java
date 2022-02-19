package com.j1.seckill.controller;

import com.j1.seckill.config.UserArgumentResolver;
import com.j1.seckill.pojo.User;
import com.j1.seckill.service.IGoodsService;
import com.j1.seckill.service.IUserService;
import com.j1.seckill.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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


    //    注入接口或bean组件？
    @Autowired
    private IUserService userService;

    @Autowired
    private IGoodsService goodsService;


    //    跳转商品列表
    @RequestMapping("/toList")
//    public String toList(HttpSession session, Model model, @CookieValue("userTicket") String ticket) {
//    public String toList(HttpServletRequest request, HttpServletResponse response, Model model, @CookieValue("userTicket") String ticket) {
    public String toList(User user, Model model) {
        /*  通过addArgumentResolvers实现
         if (!StringUtils.hasText(ticket)) return "login";

         //        方法（1）：通过session获取用户信息
         //        User user = (User) session.getAttribute(ticket);

         //        方法（2）：通过redis获取
         User user = userService.getUserByCookie(ticket, request, response);


         if (user == null) return "login";
         */

        //将user传到前端去
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.findGoodsVo());

        return "goodsList";
    }

    //    跳转商品详情页
    @RequestMapping("/toDetail/{goodsId}")
    public String toDetail(Model model, User user, @PathVariable Long goodsId) {
        model.addAttribute("user", user);
        model.addAttribute("goods", goodsService.findGoodsVoByGoodsId(goodsId));
        return "goodsDetail";
    }
}
