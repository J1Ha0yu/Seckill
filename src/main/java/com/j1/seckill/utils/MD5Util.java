package com.j1.seckill.utils;

import org.springframework.stereotype.Component;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @ClassName MD5Util
 * @Description MD5工具类
 * @Author J1
 * @Date DATE{TIME}
 */

@Component
public class MD5Util {
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "1a2b3c4d";

    //    第一次加密：从前端到后端
    public static String inputPassToFromPass(String inputPass) {
//        加盐混淆
        String str = "" + salt.charAt(0) + salt.charAt(3) + inputPass + salt.charAt(5) + salt.charAt(2);
        return md5(str);
    }


    //    第二次加密：从后端到数据库
    public static String fromPassToDBPass(String fromPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(3) + fromPass + salt.charAt(5) + salt.charAt(2);
        return md5(str);
    }


    public static String inputPassToDBPass(String inputPass, String salt) {
        String fromPass = inputPassToFromPass(inputPass);
        String dbPass = fromPassToDBPass(fromPass, salt);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFromPass("123456"));
        System.out.println(fromPassToDBPass("08abfcd0528561455d2003c4e9f64add", "1a2b3c4d"));
        System.out.println(inputPassToDBPass("123456", "1a2b3c4d"));
    }

}
