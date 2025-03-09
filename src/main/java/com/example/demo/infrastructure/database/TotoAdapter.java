package com.example.demo.infrastructure.database;

import com.example.demo.domain.model.Toto;
import com.example.demo.domain.spi.TotoSpi;
import com.example.demo.infrastructure.database.mapper.TotoMapper;
import com.example.demo.infrastructure.database.repository.TotoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TotoAdapter implements TotoSpi {

    private final TotoRepository totoRepository;
    private final TotoMapper totoMapper;

    public TotoAdapter(TotoRepository totoRepository, TotoMapper totoMapper) {
        this.totoRepository = totoRepository;
        this.totoMapper = totoMapper;
    }

    @Override
    public List<Toto> findAll() {
        return totoRepository.findAll().stream()
                .map(totoMapper::toDomain)
                .toList();
    }

    @Override
    public Toto create(Toto toto) {
        var totoEntity = totoRepository.save(totoMapper.toEntity(toto));
        return totoMapper.toDomain(totoEntity);
    }
}
