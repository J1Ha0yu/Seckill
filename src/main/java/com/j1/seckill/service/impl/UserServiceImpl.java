package com.j1.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.j1.seckill.config.RedisConfig;
import com.j1.seckill.exception.GlobalException;
import com.j1.seckill.mapper.UserMapper;
import com.j1.seckill.pojo.User;
import com.j1.seckill.service.IUserService;
import com.j1.seckill.utils.CookieUtil;
import com.j1.seckill.utils.MD5Util;
import com.j1.seckill.utils.UUIDUtil;
import com.j1.seckill.vo.LoginVo;
import com.j1.seckill.vo.RespBean;
import com.j1.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName UserServiceImpl
 * @Description 服务实现类
 * @Author J1
 * @Date DATE{TIME}
 */


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    //    登录
    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

//        参数校验:通过自定义注解@isMobile实现了
//        if (!StringUtils.hasText(mobile) || !StringUtils.hasText(password)) {
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
//        }
//        if (!ValidatorUtil.isMobile(mobile)) {
//            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
//        }

        //根据手机号获取用户
        User user = userMapper.selectById(mobile);
//        判断用户是否存在
        if (user == null) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
//        判断密码是否正确
        if (!MD5Util.fromPassToDBPass(password, user.getSalt()).equals(user.getPassword())) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }


//        生成Cookie
        String ticket = UUIDUtil.uuid();


//       方法（1） setAttribute()方法：增加一个指定名称和对应的新属性，或者把一个现有属性设定为指定的值
//        request.getSession().setAttribute(ticket, user);//session中存储的是用户User,ticket（即cookie）是属性，user是值

//       方法（2）redis方法：将用户信心存入redis中
        redisTemplate.opsForValue().set("user:" + ticket, user);

//        放入cookie中
        CookieUtil.setCookie(request, response, "userTicket", ticket);

//        登陆成功
        return RespBean.success();
    }


    //    根据cookie获取用户
    @Override
    public User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response) {

        if (!StringUtils.hasText(userTicket)) return null;

        User user = (User) redisTemplate.opsForValue().get("user:" + userTicket);

        if (user != null) {
            CookieUtil.setCookie(request, response, "userTicket", userTicket);
        }
        return user;
    }

    //    更新密码
    @Override
    public RespBean updatePassword(String userTicket, String password, HttpServletRequest request, HttpServletResponse response) {
        User user = getUserByCookie(userTicket, request, response);
        if (user == null) {
            throw new GlobalException(RespBeanEnum.MOBILE_NOT_EXIST);
        }

        user.setPassword(MD5Util.inputPassToDBPass(password, user.getSalt()));
        int result = userMapper.updateById(user);
        if (result == 1) {
            //在redis中删除
            redisTemplate.delete("user:" + userTicket);
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.UPDATE_FAIL);
    }

}
