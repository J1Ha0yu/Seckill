package com.j1.seckill.utils;

import java.util.UUID;

/**
 * @ClassName UUIDUtil
 * @Description UUID工具类
 * @Author J1
 * @Date DATE{TIME}
 */
public class UUIDUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
