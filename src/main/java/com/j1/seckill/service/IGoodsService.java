package com.j1.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.j1.seckill.pojo.Goods;
import com.j1.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author j1
 * @since 2022-02-19
 */
public interface IGoodsService extends IService<Goods> {
    //获取商品列表
    List<GoodsVo> findGoodsVo();
}
