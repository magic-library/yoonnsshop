package com.example.yoonnsshop.domain.items;

import com.example.yoonnsshop.domain.items.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ItemCountInitializer implements ApplicationRunner {
    private final ItemRepository itemRepository;
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public ItemCountInitializer(ItemRepository itemRepository, StringRedisTemplate redisTemplate) {
        this.itemRepository = itemRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Long initialCount = itemRepository.count();
        redisTemplate.opsForValue().set("item_count", initialCount.toString());
        log.debug("Initial item count: {}", initialCount);
    }
}
