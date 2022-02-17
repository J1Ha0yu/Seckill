package com.j1.seckill.vo;

import com.j1.seckill.validator.isMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @ClassName LoginVo
 * @Description 登录参数
 * @Author J1
 * @Date DATE{TIME}
 */

@Data  //只有带class的类才能用
public class LoginVo {
    @NotNull
    @isMobile
    private String mobile;
    @NotNull
    @Length(min=32)
    private String password;
}
