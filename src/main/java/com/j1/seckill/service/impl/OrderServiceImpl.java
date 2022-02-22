package com.j1.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j1.seckill.mapper.OrderMapper;
import com.j1.seckill.pojo.Order;
import com.j1.seckill.pojo.SeckillGoods;
import com.j1.seckill.pojo.SeckillOrder;
import com.j1.seckill.pojo.User;
import com.j1.seckill.service.IOrderService;
import com.j1.seckill.service.ISeckillGoodsService;
import com.j1.seckill.service.ISeckillOrderService;
import com.j1.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author j1
 * @since 2022-02-19
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private static ISeckillGoodsService seckillGoodsService;
    @Autowired
    private static OrderMapper orderMapper;
    @Autowired
    private static ISeckillOrderService seckillOrderService;

    //    秒杀
    @Override
    public Order seckill(User user, GoodsVo goods) {

//        秒杀商品表减库存
        SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id", goods.getId()));//查询秒杀商品表
        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        seckillGoodsService.updateById(seckillGoods);

//        生成订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        order.setDeliveryAddrId(0L);//收货地址
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0); //0创建未支付
        order.setCreateDate(new Date());

        orderMapper.insert(order);

//        生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goods.getId());

        seckillOrderService.save(seckillOrder);


        return order;
    }
}
