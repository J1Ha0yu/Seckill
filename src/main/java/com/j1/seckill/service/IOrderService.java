package com.j1.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.j1.seckill.pojo.Order;
import com.j1.seckill.pojo.User;
import com.j1.seckill.vo.GoodsVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author j1
 * @since 2022-02-19
 */
public interface IOrderService extends IService<Order> {

    Order seckill(User user, GoodsVo goods);
}
