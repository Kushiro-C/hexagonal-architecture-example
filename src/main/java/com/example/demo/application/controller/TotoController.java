package com.example.demo.application.controller;

import com.example.demo.application.dto.form.TotoForm;
import com.example.demo.application.dto.view.TotoView;
import com.example.demo.domain.api.TotoApi;
import com.example.demo.domain.model.Toto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/toto")
@RestController
public class TotoController {

    private final TotoApi totoApi;

    public TotoController(TotoApi totoApi) {
        this.totoApi = totoApi;
    }

    @GetMapping
    public List<TotoView> findAll() {
        return totoApi.findAll().stream()
                .map(toto -> new TotoView(toto.name(), toto.description()))
                .toList();
    }

    @PostMapping
    public TotoView create(@RequestBody TotoForm form) {
        var createdToto = totoApi.create(new Toto(form.name(), form.description()));
        return new TotoView(createdToto.name(), createdToto.description());
    }
}
