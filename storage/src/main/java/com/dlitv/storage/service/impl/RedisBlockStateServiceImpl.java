package com.dlitv.storage.service.impl;

import com.dlitv.storage.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisBlockStateServiceImpl implements CacheService {

    private static final String LAST_PROCESSED_BLOCK_KEY = "lastProcessedBlock";

    private final StringRedisTemplate redisTemplate;

    public void saveLastProcessedBlock(BigInteger blockNumber) {
        this.redisTemplate.opsForValue().set(LAST_PROCESSED_BLOCK_KEY, blockNumber.toString());
    }

    public BigInteger getLastProcessedBlock() {
        return Optional.ofNullable(this.redisTemplate.opsForValue().get(LAST_PROCESSED_BLOCK_KEY))
                .map(BigInteger::new)
                .orElse(null);
    }
}
