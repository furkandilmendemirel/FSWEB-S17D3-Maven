package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {

    private final Map<Long, Kangaroo> kangaroos;

    public KangarooController() {
        kangaroos = new HashMap<>();
    }

    @GetMapping
    public List<Kangaroo> getAll() {
        return new ArrayList<>(kangaroos.values());
    }

    @GetMapping("/{id}")
    public Kangaroo getById(@PathVariable long id) {
        return findById(id);
    }

    @PostMapping
    public Kangaroo save(@RequestBody Kangaroo kangaroo) {

        if (kangaroo.getId() <= 0
                || kangaroo.getName() == null
                || kangaroo.getName().isBlank()) {

            throw new IllegalArgumentException(
                    "Kangaroo bilgileri geçersiz."
            );
        }

        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroo;
    }

    @PutMapping("/{id}")
    public Kangaroo update(
            @PathVariable long id,
            @RequestBody Kangaroo kangaroo) {

        findById(id);

        kangaroo.setId(id);
        kangaroos.put(id, kangaroo);

        return kangaroo;
    }

    @DeleteMapping("/{id}")
    public Kangaroo delete(@PathVariable long id) {
        Kangaroo kangaroo = findById(id);
        kangaroos.remove(id);
        return kangaroo;
    }

    private Kangaroo findById(long id) {
        Kangaroo kangaroo = kangaroos.get(id);

        if (kangaroo == null) {
            throw new ZooException(
                    "Kangaroo bulunamadı. Id: " + id,
                    HttpStatus.NOT_FOUND
            );
        }

        return kangaroo;
    }
}