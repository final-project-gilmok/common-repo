package kr.gilmok.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * Redis 기반 Access Token 블랙리스트 구현체 (공통).
 */
@Repository
@RequiredArgsConstructor
public class AccessTokenBlocklistRepositoryImpl implements AccessTokenBlocklistRepository {

    private static final String KEY_PREFIX = "blocklist:jti:";
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void block(String jti, long ttlMs) {
        if (ttlMs <= 0) return;
        
        redisTemplate.opsForValue().set(
                KEY_PREFIX + jti,
                "1",
                ttlMs,
                TimeUnit.MILLISECONDS
        );
    }

    @Override
    public boolean isBlocked(String jti) {
        return redisTemplate.hasKey(KEY_PREFIX + jti);
    }
}
