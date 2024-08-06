package com.example.yoonnsshop.config;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@Slf4j
public class MetricsConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags(
            @Value("${spring.application.name}") String applicationName) {
        return registry -> {
            try {
                registry.config().commonTags(
                        "application", applicationName,
                        "instance", applicationName + ":" +
                                InetAddress.getLocalHost().getHostName() + ":" +
                                environment.getProperty("server.port", "8080")
                );
            } catch (UnknownHostException e) {
                // 호스트 이름을 가져오지 못한 경우 대체 값 사용
                registry.config().commonTags(
                        "application", applicationName,
                        "instance", applicationName + ":unknown:" +
                                environment.getProperty("server.port", "8080")
                );
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        };
    }
}
