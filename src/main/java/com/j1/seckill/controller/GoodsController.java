package com.j1.seckill.controller;

import com.j1.seckill.config.UserArgumentResolver;
import com.j1.seckill.pojo.User;
import com.j1.seckill.service.IGoodsService;
import com.j1.seckill.service.IUserService;
import com.j1.seckill.service.impl.UserServiceImpl;
import com.j1.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;


    //    跳转商品列表
    @RequestMapping(value = "/toList", produces = "text/html;charset=utf-8")
    @ResponseBody
//    public String toList(HttpSession session, Model model, @CookieValue("userTicket") String ticket) {
//    public String toList(HttpServletRequest request, HttpServletResponse response, Model model, @CookieValue("userTicket") String ticket) {
    public String toList(User user, Model model, HttpServletRequest request, HttpServletResponse response) {
        /*  通过addArgumentResolvers实现
         if (!StringUtils.hasText(ticket)) return "login";

         //        方法（1）：通过session获取用户信息
         //        User user = (User) session.getAttribute(ticket);

         //        方法（2）：通过redis获取
         User user = userService.getUserByCookie(ticket, request, response);


         if (user == null) return "login";
         */

//        redis中获取页面
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsList");
        if (StringUtils.hasText(html)) {
            return html;
        }


        //将user传到前端去
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.findGoodsVo());

//        return "goodsList";

//        如果为空，手动渲染，存入redis并返回

        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
//        获取模板引擎
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList", webContext);
        if (StringUtils.hasText(html)) {
            valueOperations.set("goodsList", html, 60, TimeUnit.SECONDS);
        }
        return html;
    }

    //    跳转商品详情页
    @RequestMapping(value = "/toDetail/{goodsId}", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toDetail(Model model, User user, @PathVariable Long goodsId, HttpServletRequest request, HttpServletResponse response) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsDetail:" + goodsId);
        if (StringUtils.hasText(html)) {
            return html;
        }

        model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();

//        秒杀状态
        int secKillStatus = 0;
//        秒杀倒计时
        int remainSeconds = 0;
        if (nowDate.before(startDate)) {
//            秒杀未开始
            secKillStatus = 0;
            remainSeconds = (int) ((startDate.getTime() - nowDate.getTime()) / 1000);
        } else if (nowDate.after(endDate)) {
//            秒杀已结束
            secKillStatus = 2;
            remainSeconds = -1;
        } else {
//            进行中
            secKillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("secKillStatus", secKillStatus);
        model.addAttribute("goods", goodsVo);

        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", webContext);
        if (StringUtils.hasText(html)) {
            valueOperations.set("goodsDetail:" + goodsId, html, 60, TimeUnit.SECONDS);
        }
//        return "goodsDetail";
        return html;
    }

}
