package com.example.yoonnsshop.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HibernateRowsFilter extends Filter<ILoggingEvent> {

    private static final Pattern ROWS_PATTERN = Pattern.compile("rows: (\\d+)");

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (event.getLoggerName().equals("org.hibernate.stat.internal.StatisticsImpl")) {
            String message = event.getMessage();
            Matcher matcher = ROWS_PATTERN.matcher(message);
            if (matcher.find()) {
                // Instead of modifying the event, we'll return ACCEPT
                // The actual formatting will be done in the encoder pattern
                return FilterReply.ACCEPT;
            }
        }
        return FilterReply.DENY;
    }
}

