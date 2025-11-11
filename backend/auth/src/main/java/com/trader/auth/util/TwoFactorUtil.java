package com.trader.auth.util;

import java.security.SecureRandom;
import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class TwoFactorUtil {

    private final RedisTemplate<String, String> redisTemplate;
    private final SecureRandom random = new SecureRandom();

    public TwoFactorUtil(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String generateCode(String adminId, Duration ttl) {
        String code = String.valueOf(100000 + random.nextInt(900000));
        redisTemplate.opsForValue().set("admin:2fa:" + adminId, code, ttl);
        return code;
    }

    public boolean verifyCode(String adminId, String code) {
        String key = "admin:2fa:" + adminId;
        String stored = redisTemplate.opsForValue().get(key);
        if (stored != null && stored.equals(code)) {
            redisTemplate.delete(key);
            return true;
        }
        return false;
    }
}
