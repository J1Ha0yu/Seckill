package com.j1.seckill.exception;

import com.j1.seckill.vo.RespBean;
import com.j1.seckill.vo.RespBeanEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



/**
 * @ClassName GlobalExceptionHandler
 * @Description 全局异常处理类
 * @Author J1
 * @Date DATE{TIME}
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public RespBean ExceptionHandler(Exception e) {
        if (e instanceof GlobalException) {
            GlobalException ex = (GlobalException) e;
            return RespBean.error(ex.getRespBeanEnum());
        }else if(e instanceof BindException){//绑定异常
            BindException ex = (BindException) e;
            RespBean respBean = RespBean.error(RespBeanEnum.BIND_ERROR);
            respBean.setMessage("参数校验异常："+ex.getAllErrors().get(0).getDefaultMessage());//获取异常中第一个错误信息
            return respBean;
        }
        return RespBean.error(RespBeanEnum.ERROR);
    }
}
