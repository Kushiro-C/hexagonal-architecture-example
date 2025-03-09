package com.example.demo.domain.api;

import com.example.demo.domain.model.Toto;

import java.util.List;

public interface TotoApi {

    List<Toto> findAll();

    Toto create(Toto toto);
}
