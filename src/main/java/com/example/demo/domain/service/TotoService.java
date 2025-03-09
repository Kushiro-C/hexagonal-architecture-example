package com.example.demo.domain.service;

import com.example.demo.domain.DomainService;
import com.example.demo.domain.DomainTransactional;
import com.example.demo.domain.api.TotoApi;
import com.example.demo.domain.model.Toto;
import com.example.demo.domain.spi.LogFactorySpi;
import com.example.demo.domain.spi.LogSpi;
import com.example.demo.domain.spi.TotoSpi;

import java.util.List;

@DomainService
public class TotoService implements TotoApi {

    private final TotoSpi totoSpi;
    private final LogSpi logSpi;

    public TotoService(TotoSpi totoSpi, LogFactorySpi logFactorySpi) {
        this.totoSpi = totoSpi;
        this.logSpi = logFactorySpi.getLogger(TotoService.class.getName());
    }

    @Override
    public List<Toto> findAll() {
        logSpi.logTotoSay("Stop spying on us!");
        return totoSpi.findAll();
    }

    @Override
    @DomainTransactional
    public Toto create(Toto toto) {
        logSpi.logTotoSay("Hello i'm new here, my name is %s".formatted(toto.name()));
        return totoSpi.create(toto);
    }
}
