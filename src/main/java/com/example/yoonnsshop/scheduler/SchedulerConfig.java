package com.example.yoonnsshop.scheduler;

import com.example.yoonnsshop.domain.items.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    private final ItemService itemService;

    @Autowired
    public SchedulerConfig(ItemService itemService) {
        this.itemService = itemService;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void updateCountCache() {
        itemService.updateCountCache();
    }
}