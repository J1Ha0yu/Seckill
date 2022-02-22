package com.j1.seckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.j1.seckill.pojo.Order;
import com.j1.seckill.pojo.SeckillOrder;
import com.j1.seckill.pojo.User;
import com.j1.seckill.service.IGoodsService;
import com.j1.seckill.service.IOrderService;
import com.j1.seckill.service.ISeckillOrderService;
import com.j1.seckill.service.impl.GoodsServiceImpl;
import com.j1.seckill.vo.GoodsVo;
import com.j1.seckill.vo.RespBean;
import com.j1.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName SeckillController
 * @Description 秒杀
 * @Author J1
 * @Date DATE{TIME}
 */

@Controller
@RequestMapping("/seckill")
public class SecKillController {

    @Autowired
    private static IGoodsService goodsService;
    @Autowired
    private static ISeckillOrderService seckillOrderService;
    @Autowired
    private static IOrderService orderService;


    @RequestMapping("/doSeckill")
    public String doSecKill(Model model, User user, Long goodsId) {

//        秒杀
        if (user == null) return "login";
        model.addAttribute("user", user);

        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
//        判断库存
        if (goodsVo.getStockCount() < 1) {
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_ERROR.getMessage());
            return "seKillFail";
        }

//        判断是否重复抢购
        SeckillOrder seckillOrder =
                seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));//mybatis-plus中的知识点
        if (seckillOrder != null) {
            model.addAttribute("errmsg", RespBeanEnum.REPEAT_ERROR.getMessage());
            return "seKillFail";
        }
        Order order = orderService.seckill(user, goodsVo);

        model.addAttribute("goods", goodsVo);
        model.addAttribute("order", order);


        return "orderDetail";
    }
}
