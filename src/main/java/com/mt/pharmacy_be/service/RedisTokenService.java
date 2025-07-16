package com.mt.pharmacy_be.service;

import com.mt.pharmacy_be.entity.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RedisTokenService {
    RedisTemplate<String, Object> redisTemplate;

    /**
     * Saves the user's token into Redis with initial status as not revoked and not expired.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Builds a key based on userId and token, stores revoked and expired flags, and sets key expiration time.
     */
    public void saveToken(UserEntity user, String accessToken, int duration) {
        String key = buildKey(user.getId(), accessToken);

        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("revoked", false);
        tokenData.put("expired", false);

        redisTemplate.opsForHash().putAll(key, tokenData);
        redisTemplate.expire(key, Duration.ofMinutes(duration));
    }

    /**
     * Revokes all tokens of a user by marking revoked and expired as true in Redis.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Finds all token keys for the user based on userId and updates revoked and expired status.
     */
    public void revokeAllUserTokens(UUID userId) {
        Set<String> keys = redisTemplate.keys("token:" + userId + ":*");
        if (!keys.isEmpty()) {
            for (String key : keys) {
                redisTemplate.opsForHash().put(key, "revoked", true);
                redisTemplate.opsForHash().put(key, "expired", true);
            }
        }
    }

    /**
     * Checks the validity of a token based on revoked and expired status stored in Redis.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Returns true if the token is not revoked and not expired; otherwise returns false.
     */
    public boolean isTokenValid(UUID userId, String token) {
        String key = buildKey(userId, token);
        Boolean revoked = (Boolean) redisTemplate.opsForHash().get(key, "revoked");
        Boolean expired = (Boolean) redisTemplate.opsForHash().get(key, "expired");

        return Boolean.FALSE.equals(revoked) && Boolean.FALSE.equals(expired);
    }

    /**
     * Builds the Redis key for storing the token in a standard format.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Concatenates userId and token into a key with the format "token:{userId}:{token}".
     */
    private String buildKey(UUID userId, String token) {
        return "token:" + userId + ":" + token;
    }
}
