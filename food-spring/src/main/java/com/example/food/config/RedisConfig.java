package com.example.food.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Bean
    public LettuceConnectionFactory redisConnection() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);

        return new LettuceConnectionFactory(config);
    }

    @Bean
    @Primary
    public RedisTemplate<Object, Object> redisTemplate(LettuceConnectionFactory redisLettuceConnection) {

        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisLettuceConnection);

        return redisTemplate;

    }

}
