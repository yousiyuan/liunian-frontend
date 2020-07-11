package org.liunian.common;

import org.liunian.common.token.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class TokenSessionUtils {

    private static RedisTemplate<Object, Object> redisTemplate;
    private static String NAME_SPACE;

    @Autowired
    public TokenSessionUtils(RedisTemplate<Object, Object> redisTemplate,
                             Environment environment) {
        TokenSessionUtils.redisTemplate = redisTemplate;
        TokenSessionUtils.NAME_SPACE = environment.getProperty("spring.application.name", "default") + ":" + "TOKEN-SESSION";
    }

    /**
     * 存放Session
     */
    public static String put(Object value) {
        if (null == value) {
            return null;
        }
        String token = JwtUtils.createToken();
        redisTemplate.opsForValue().set(TokenSessionUtils.NAME_SPACE + ":" + token, value);
        redisTemplate.expire(TokenSessionUtils.NAME_SPACE + ":" + token, 60 * 30, TimeUnit.SECONDS);
        return token;
    }

    /**
     * 获取Session
     */
    public static Object get(String token) {
        return redisTemplate.opsForValue().get(TokenSessionUtils.NAME_SPACE + ":" + token);
    }

}
