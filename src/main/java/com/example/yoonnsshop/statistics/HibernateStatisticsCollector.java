package com.example.yoonnsshop.statistics;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManagerFactory;

//@Component
@Slf4j
public class HibernateStatisticsCollector {

    private final EntityManagerFactory entityManagerFactory;

    public HibernateStatisticsCollector(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Scheduled(fixedRate = 5000) // 5초마다 실행
    public void collectStatistics() {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Statistics stats = sessionFactory.getStatistics();
        long queryExecutionCount = stats.getQueryExecutionCount();
        long entityLoadCount = stats.getEntityLoadCount();
        long secondLevelCacheHitCount = stats.getSecondLevelCacheHitCount();
        long secondLevelCacheMissCount = stats.getSecondLevelCacheMissCount();

        log.info("Hibernate Statistics - Queries: {}, Entity Loads: {}, L2 Cache Hits: {}, L2 Cache Misses: {}",
                queryExecutionCount, entityLoadCount, secondLevelCacheHitCount, secondLevelCacheMissCount);
    }
}