package com.example.yoonnsshop.statistics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManagerFactory;

@Slf4j
@Component
public class SessionMetricsCounter {
    private final MeterRegistry meterRegistry;
    private final EntityManagerFactory entityManagerFactory;
    private long totalQueries = 0;
    private long totalConnections = 0;
    // hibernate에서 준비중인 쿼리 수와 연결 수를 가져온다. SessionFactory가 초기화된 이후부터 누적된 값
    private long currentQueries  = 0;
    // hibernate가 데이터베이스에 연결한 총 횟수.
    private long currentConnections = 0;

    public SessionMetricsCounter(EntityManagerFactory entityManagerFactory, MeterRegistry meterRegistry) {
        this.entityManagerFactory = entityManagerFactory;
        this.meterRegistry = meterRegistry;

        Gauge.builder("session.queries", () -> totalQueries)
                .register(meterRegistry);
        Gauge.builder("session.connections", () -> totalConnections)
                .register(meterRegistry);
        Gauge.builder("session.queries.current", () -> currentQueries)
                .register(meterRegistry);
        Gauge.builder("session.connections.current", () -> currentConnections)
                .register(meterRegistry);
    }

    @Scheduled(fixedRate = 5000)
    public void collectAndPublishMetrics() {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Statistics statistics = sessionFactory.getStatistics();

        currentQueries = statistics.getPrepareStatementCount();
        currentConnections = statistics.getConnectCount();

        totalQueries += currentQueries;
        totalConnections += currentConnections;

        log.info("Session Metrics - Total Queries: {}, Total Connections: {}, " +
                        "Current Queries: {}, Current Connections: {}",
                totalQueries, totalConnections, currentQueries, currentConnections);

        // 현재 카운트 초기화.
        statistics.clear();
    }
}