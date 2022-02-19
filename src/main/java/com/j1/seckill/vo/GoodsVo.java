package com.j1.seckill.vo;

import com.j1.seckill.pojo.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.BeanInfo;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName GoodsVo
 * @Description 商品返回对象
 * @Author J1
 * @Date DATE{TIME}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsVo extends Goods {
    //秒杀价格
    private BigDecimal seckillPrice;

    //库存数量
    private Integer stockCount;

    //秒杀开始时间
    private Date startDate;

    //秒杀结束时间
    private Date endDate;

}
