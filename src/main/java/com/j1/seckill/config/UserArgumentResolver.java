package com.j1.seckill.config;

import com.j1.seckill.pojo.User;
import com.j1.seckill.service.IUserService;
import com.j1.seckill.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName UserArgumentResolver
 * @Description 自定义用户参数
 * @Author J1
 * @Date DATE{TIME}
 */
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private IUserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        return clazz == User.class;
    }


    //上边为true则执行
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        String ticket = CookieUtil.getCookieValue(request, "userTicket");
        if (!StringUtils.hasText(ticket)) {
            return null;
        }

        return userService.getUserByCookie(ticket, request, response);
    }
}
