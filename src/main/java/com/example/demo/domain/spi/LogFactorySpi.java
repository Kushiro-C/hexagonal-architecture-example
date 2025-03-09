package com.example.demo.domain.spi;

public interface LogFactorySpi {

    LogSpi getLogger(String name);
}
