package com.example.SWP391_KOIFARMSHOP_BE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class JwtBlacklistService {
    private final RedisTemplate<String, String> redisTemplate;
    private final static String BLACKLIST_PREFIX = "blacklist:";

    @Autowired
    public JwtBlacklistService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void blacklistToken(String token, long expirationTime) {
        redisTemplate.opsForValue().set(BLACKLIST_PREFIX + token, "true", expirationTime, TimeUnit.MILLISECONDS);
    }

    public boolean isTokenBlacklisted(String token) {
        return redisTemplate.hasKey(BLACKLIST_PREFIX + token);
    }

}
