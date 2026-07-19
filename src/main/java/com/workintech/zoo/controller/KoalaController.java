package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/koalas")
public class KoalaController {

    private final Map<Long, Koala> koalas;

    public KoalaController() {
        koalas = new HashMap<>();
    }

    @GetMapping
    public List<Koala> getAll() {
        return new ArrayList<>(koalas.values());
    }

    @GetMapping("/{id}")
    public Koala getById(@PathVariable long id) {
        return findById(id);
    }

    @PostMapping
    public Koala save(@RequestBody Koala koala) {
        koalas.put(koala.getId(), koala);
        return koala;
    }

    @PutMapping("/{id}")
    public Koala update(
            @PathVariable long id,
            @RequestBody Koala koala) {

        findById(id);

        koala.setId(id);
        koalas.put(id, koala);

        return koala;
    }

    @DeleteMapping("/{id}")
    public Koala delete(@PathVariable long id) {
        Koala koala = findById(id);
        koalas.remove(id);
        return koala;
    }

    private Koala findById(long id) {
        Koala koala = koalas.get(id);

        if (koala == null) {
            throw new ZooException(
                    "Koala bulunamadı. Id: " + id,
                    HttpStatus.NOT_FOUND
            );
        }

        return koala;
    }
}