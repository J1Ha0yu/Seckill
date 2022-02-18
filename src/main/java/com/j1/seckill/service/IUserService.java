package com.j1.seckill.service;

import com.j1.seckill.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.j1.seckill.vo.LoginVo;
import com.j1.seckill.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author j1
 * @since 2022-02-14
 */
public interface IUserService extends IService<User> {
    //  登录
    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);


    //    根据cookie获取用户
    User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response);

}
