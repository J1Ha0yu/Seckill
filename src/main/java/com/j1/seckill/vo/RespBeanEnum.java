package com.j1.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * @ClassName RespBeanEnum
 * @Description 公共返回对象枚举
 * @Author J1
 * @Date DATE{TIME}
 */

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
    //通用返回对象
    SUCCESS(200,"SUCCESS"),
    ERROR(500,"服务端异常"),

//    登录模块返回对象
    LOGIN_ERROR(500210,"用户名或密码不正确"),
    MOBILE_ERROR(500211,"手机号码格式不正确"),
    BIND_ERROR(500212,"参数校验异常");   //用逗号间隔，结束采用分号

    private final Integer code;  //状态码
    private final String message; //状态信息

}
