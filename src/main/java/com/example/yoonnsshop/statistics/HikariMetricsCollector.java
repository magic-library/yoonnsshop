package com.example.yoonnsshop.statistics;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
@Slf4j
public class HikariMetricsCollector {

    private final MeterRegistry meterRegistry;

    public HikariMetricsCollector(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Scheduled(fixedRate = 5000) // 5초마다 실행
    public void collectMetrics() {
        Double totalConnections = meterRegistry.get("hikaricp.connections").gauge().value();
        Double activeConnections = meterRegistry.get("hikaricp.connections.active").gauge().value();
        Double idleConnections = meterRegistry.get("hikaricp.connections.idle").gauge().value();
//        Double connectionTimeoutTotal = meterRegistry.get("hikaricp.connections.timeout.total").counter().count();
//        Double connectionCreationTotal = (double) meterRegistry.get("hikaricp.connections.creation").timer().count();

        // JDBC 쿼리 수행 횟수 관련 메트릭
        Double connectionAcquisitionTotal = (double) meterRegistry.get("hikaricp.connections.acquire").timer().count();
        Double connectionUsageTotal = (double) meterRegistry.get("hikaricp.connections.usage").timer().count();

        log.info("HikariCP Metrics - Total: {}, Active: {}, Idle: {}, Acquired: {}, Used: {}",
                totalConnections, activeConnections, idleConnections,
                connectionAcquisitionTotal, connectionUsageTotal);
    }
}
