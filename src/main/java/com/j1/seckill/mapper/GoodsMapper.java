package com.j1.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.j1.seckill.pojo.Goods;
import com.j1.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author j1
 * @since 2022-02-19
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    //    获取商品列表
    List<GoodsVo> findGoodsVo();

    //    获取商品详情
    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
