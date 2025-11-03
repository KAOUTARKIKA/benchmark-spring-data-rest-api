package com.tp.benchmarkspringdatarestapi.controller;

import com.tp.benchmarkspringdatarestapi.entity.Category;
import com.tp.benchmarkspringdatarestapi.entity.Item;
import com.tp.benchmarkspringdatarestapi.service.CategoryService;
import com.tp.benchmarkspringdatarestapi.service.ItemService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@BasePathAwareController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ItemService itemService;

    @Autowired
    public CategoryController(CategoryService categoryService, ItemService itemService) {
        this.categoryService = categoryService;
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Category> result = categoryService.list(page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> get(@PathParam("id") Long id) {
        Optional<Category> category = categoryService.get(id);
        return category
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Category> create(@Valid @RequestBody Category category) {
        Category created = categoryService.create(category);
        return ResponseEntity.created(URI.create("/categories/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(
            @PathVariable Long id,
            @Valid @RequestBody Category payload) {
        Optional<Category> updated = categoryService.update(id, payload);
        return updated
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = categoryService.delete(id);
        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<Item>> listItems(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Item> result = itemService.listByCategory(id, page, size);
        return ResponseEntity.ok(result);
    }
}