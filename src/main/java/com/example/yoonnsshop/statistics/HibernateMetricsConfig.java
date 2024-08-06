package com.example.yoonnsshop.statistics;

import io.micrometer.core.instrument.MeterRegistry;
import org.hibernate.stat.Statistics;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

@Configuration
public class HibernateMetricsConfig implements ApplicationListener<ContextRefreshedEvent> {

    private final MeterRegistry meterRegistry;

    public HibernateMetricsConfig(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Statistics hibernateStatistics = event.getApplicationContext().getBean(Statistics.class);
        configureHibernateMetrics(hibernateStatistics);
    }

    private void configureHibernateMetrics(Statistics hibernateStatistics) {
        // JDBC statement preparation time
        meterRegistry.gauge("hibernate.statement.prepare.count", hibernateStatistics, Statistics::getPrepareStatementCount);

        // JDBC statement execution time
        meterRegistry.gauge("hibernate.statement.execution.time", hibernateStatistics,
                (stats) -> stats.getQueryExecutionCount() > 0
                        ? (double) stats.getQueryExecutionMaxTime()
                        : 0);

        // Add more metrics as needed
    }
}


