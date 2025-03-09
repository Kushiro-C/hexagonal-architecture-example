package com.example.demo.domain.spi;

import com.example.demo.domain.model.Toto;

import java.util.List;

public interface TotoSpi {

    List<Toto> findAll();

    Toto create(Toto toto);
}
