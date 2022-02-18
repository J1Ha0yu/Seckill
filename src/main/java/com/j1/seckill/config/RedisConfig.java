package com.j1.seckill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @ClassName RedisConfig
 * @Description redis配置类  实现序列化
 * @Author J1
 * @Date DATE{TIME}
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>(); //泛型
        redisTemplate.setKeySerializer(new StringRedisSerializer());// key序列化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer()); //value序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());//hash类型key序列化
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());//hash类型value序列化

        //注入连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);//设置连接工厂
        return redisTemplate;
    }
}
