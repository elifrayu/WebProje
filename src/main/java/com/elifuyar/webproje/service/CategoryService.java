
package com.elifuyar.webproje.service;

import com.elifuyar.webproje.entity.Category;
import com.elifuyar.webproje.dto.CategoryRequest;
import com.elifuyar.webproje.dto.CategoryResponse;
import com.elifuyar.webproje.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // READ - Tum kategorileri getir
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // READ - ID ile kategori getir
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {
        Category category = findCategory(id);
        return toResponse(category);
    }

    // CREATE - Yeni kategori olustur
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = new Category();

        category.setName(request.name());
        category.setDescription(request.description());

        return toResponse(categoryRepository.save(category));
    }

    // UPDATE - Kategori guncelle
    public CategoryResponse updateCategory(
            Long id, CategoryRequest request) {

        Category category = findCategory(id);

        category.setName(request.name());
        category.setDescription(request.description());

        return toResponse(categoryRepository.save(category));
    }

    // DELETE - Kategori sil
    public void deleteCategory(Long id) {
        Category category = findCategory(id);
        categoryRepository.delete(category);
    }

    // Yardimci metot
    private Category findCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Kategori bulunamadi: " + id));
    }

    // Entity -> DTO donusumu
    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}
