package com.example.yoonnsshop.statistics;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManagerFactory;

@Slf4j
@Component
public class SessionMetricsCounter {

    private final EntityManagerFactory entityManagerFactory;
    private long totalQueries = 0;
    private long totalConnections = 0;

    public SessionMetricsCounter(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Scheduled(fixedRate = 5000) // 5초마다 실행
    public void collectAndPrintMetrics() {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Statistics statistics = sessionFactory.getStatistics();

        long currentQueries = statistics.getPrepareStatementCount();
        long currentConnections = statistics.getConnectCount();

        totalQueries += currentQueries;
        totalConnections += currentConnections;

        log.info("Session Metrics - Total Queries: {}, Total Connections: {}, " +
                        "Current Queries: {}, Current Connections: {}",
                totalQueries, totalConnections, currentQueries, currentConnections);

        // 현재 카운트 초기화 (선택사항)
        statistics.clear();
    }
}
