package com.j1.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName RespBean
 * @Description 公共返回对象
 * @Author J1
 * @Date DATE{TIME}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespBean {
    private long code;  //状态码
    private String message; //消息
    private Object object;  //返回对象


    //    成功返回结果
    public static RespBean success() {
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(), null);
    }

    //    重载success方法
    public static RespBean success(Object obj) {
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(), obj);
    }

    //    失败返回结果
//    需要直接传参枚举类RespBeanEnum，因为成功基本是200，但失败结果有很多
    public static RespBean error(RespBeanEnum respBeanEnum) {
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), null);
    }

    //    重载
    public RespBean error(RespBeanEnum respBeanEnum, Object obj) {
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(), obj);
    }
}
