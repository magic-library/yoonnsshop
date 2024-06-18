package com.example.yoonnsshop.domain.items;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepositoryImpl implements ItemRepositoryCustom {
    private final RedisTemplate<String, String> redisTemplate;

    public ItemRepositoryImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Long getItemCount() {
        String countStr = redisTemplate.opsForValue().get("item_count");
        return countStr != null ? Long.parseLong(countStr) : 0L;
    }
}
