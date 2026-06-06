package com.shopshere.shopshere.serviceimpl;

import com.shopshere.shopshere.dto.ProductRequestDTO;
import com.shopshere.shopshere.entity.Category;
import com.shopshere.shopshere.entity.Product;
import com.shopshere.shopshere.exception.ResourceNotFoundException;
import com.shopshere.shopshere.repository.CategoryRepository;
import com.shopshere.shopshere.repository.ProductRepository;
import com.shopshere.shopshere.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;
    private final CategoryRepository categoryRepository;

    @Override
    public Product create(ProductRequestDTO dto){

        Product p = new Product();

        p.setName(dto.getName());
        p.setPrice(dto.getPrice());
        p.setStock(dto.getStock());

        // ✅ FIX: fetch category from DB
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        p.setCategory(category);

        p.setBrand(dto.getBrand());
        p.setImageUrl(dto.getImageUrl());
        p.setDescription(dto.getDescription());

        return repo.save(p);
    }

    @Override
    public Page<Product> getAll(Pageable pageable){
        return repo.findAll(pageable);
    }

    @Override
    public Product getById(Long id){
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }



    @Override
    public Product update(Long id, ProductRequestDTO dto){

        Product p = getById(id);

        p.setName(dto.getName());
        p.setPrice(dto.getPrice());
        p.setStock(dto.getStock());

        // ✅ ALSO FIX CATEGORY IN UPDATE
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            p.setCategory(category);
        }

        p.setBrand(dto.getBrand());
        p.setImageUrl(dto.getImageUrl());
        p.setDescription(dto.getDescription());

        return repo.save(p);
    }

    @Override
    public void delete(Long id){
        repo.deleteById(id);
    }



    @Override
    public Page<Product> search(String keyword, Pageable pageable){
        return repo.findByNameContainingIgnoreCase(keyword, pageable);
    }

    @Override
    public Page<Product> filter(Long categoryId, String keyword, Pageable pageable){

        if(categoryId != null && keyword != null){
            return repo.findByCategoryIdAndNameContainingIgnoreCase(categoryId, keyword, pageable);
        }

        if(categoryId != null){
            return repo.findByCategoryId(categoryId, pageable);
        }

        if(keyword != null){
            return repo.findByNameContainingIgnoreCase(keyword, pageable);
        }

        return repo.findAll(pageable);
    }
}