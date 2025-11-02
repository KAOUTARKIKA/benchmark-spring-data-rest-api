package com.tp.benchmarkspringdatarestapi.controller;

import com.tp.benchmarkspringdatarestapi.dto.PageResponse;
import com.tp.benchmarkspringdatarestapi.entity.Category;
import com.tp.benchmarkspringdatarestapi.entity.Item;
import com.tp.benchmarkspringdatarestapi.service.CategoryService;
import com.tp.benchmarkspringdatarestapi.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller custom pour Category afin de maintenir la compatibilité
 * avec les endpoints des variantes Jersey et Spring MVC.
 *
 * @BasePathAwareController évite les conflits avec Spring Data REST.
 */
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
    public ResponseEntity<PageResponse<Category>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PageResponse<Category> result = categoryService.list(page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> get(@PathVariable Long id) {
        Optional<Category> category = categoryService.get(id);
        return category
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Category> create(@Valid @RequestBody Category category) {
        Category created = categoryService.create(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
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

    /**
     * Endpoint relationnel : GET /categories/{id}/items?page=&size=
     */
    @GetMapping("/{id}/items")
    public ResponseEntity<PageResponse<Item>> listItems(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PageResponse<Item> result = itemService.listByCategory(id, page, size);
        return ResponseEntity.ok(result);
    }
}