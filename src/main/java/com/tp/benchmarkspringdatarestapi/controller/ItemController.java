package com.tp.benchmarkspringdatarestapi.controller;

import com.tp.benchmarkspringdatarestapi.entity.Item;
import com.tp.benchmarkspringdatarestapi.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@BasePathAwareController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<Item>> list(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<Item> result = (categoryId != null)
                ? itemService.listByCategory(categoryId, page, size)
                : itemService.list(page, size);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> get(@PathVariable Long id) {
        Optional<Item> item = itemService.get(id);
        return item
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Item> create(@Valid @RequestBody Item item) {
        Item created = itemService.create(item);
        return ResponseEntity.created(URI.create("/items/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> update(
            @PathVariable Long id,
            @Valid @RequestBody Item payload) {
        Optional<Item> updated = itemService.update(id, payload);
        return updated
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = itemService.delete(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}