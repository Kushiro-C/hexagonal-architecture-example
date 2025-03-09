package com.example.demo.infrastructure.logging;

import com.example.demo.domain.spi.LogSpi;
import org.slf4j.Logger;

public class LogAdapter implements LogSpi {

    private final Logger logger;

    public LogAdapter(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void logTotoSay(String message) {
        logger.info("Toto said: {}", message);
    }
}
