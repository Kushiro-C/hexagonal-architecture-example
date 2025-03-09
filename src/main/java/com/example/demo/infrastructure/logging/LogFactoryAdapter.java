package com.example.demo.infrastructure.logging;

import com.example.demo.domain.spi.LogFactorySpi;
import com.example.demo.domain.spi.LogSpi;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogFactoryAdapter implements LogFactorySpi {

    @Override
    public LogSpi getLogger(String name) {
        return new LogAdapter(LoggerFactory.getLogger(name));
    }
}
