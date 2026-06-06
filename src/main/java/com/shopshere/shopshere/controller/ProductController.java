package com.shopshere.shopshere.controller;

import com.shopshere.shopshere.dto.ProductRequestDTO;
import com.shopshere.shopshere.entity.Product;
import com.shopshere.shopshere.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // ✅ CREATE (ADMIN ideally)
    @PostMapping
    public Product create(@RequestBody ProductRequestDTO dto){
        return productService.create(dto);
    }

    // ✅ GET ALL WITH PAGINATION
    @GetMapping
    public Page<Product> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){

        return productService.getAll(PageRequest.of(page, size));
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id){
        return productService.getById(id);
    }

    // 🔍 SEARCH
    @GetMapping("/search")
    public Page<Product> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){

        return productService.search(keyword, PageRequest.of(page, size));
    }

    // 🔥 FILTER + SEARCH + CATEGORY
    @GetMapping("/filter")
    public Page<Product> filter(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){

        return productService.filter(categoryId, keyword, PageRequest.of(page, size));
    }

    // ✏️ UPDATE
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id,
                          @RequestBody ProductRequestDTO dto){
        return productService.update(id, dto);
    }

    // ❌ DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        productService.delete(id);
    }
}