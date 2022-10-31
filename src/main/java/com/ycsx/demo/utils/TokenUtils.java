package com.ycsx.demo.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ycsx.demo.entity.User;
import com.ycsx.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class TokenUtils {
    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户信息生成Token，存储了userId，根据用户密码进行加密，默认一天过期
     * @param user
     * @return
     */
    public static String getToken(User user){
        // .create()-jwt提供，DateUtil-hutool提供，注意区分
        return JWT.create()
                .withExpiresAt(DateUtil.offsetDay(new Date(),2))
                .withAudience(user.getId().toString())
                .sign(Algorithm.HMAC256(user.getPassword()));
    }

    /** 这里修改上面的方法，改为传入sign标记而不是获取对象的密码，更灵活 */
    public static String getSignToken(String userId, String sign) {
        return JWT.create().withAudience(userId) // 将 user id 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2小时后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
    }
}
