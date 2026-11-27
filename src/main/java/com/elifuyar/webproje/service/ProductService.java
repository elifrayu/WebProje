
package com.elifuyar.webproje.service;

import com.elifuyar.webproje.dto.ProductRequest;
import com.elifuyar.webproje.dto.ProductResponse;
import com.elifuyar.webproje.entity.Category;
import com.elifuyar.webproje.entity.Product;
import com.elifuyar.webproje.repository.CategoryRepository;
import com.elifuyar.webproje.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(
            ProductRepository productRepository,
            CategoryRepository categoryRepository) {

        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // Tum urunleri getir
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ID ile urun getir
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        return toResponse(findProduct(id));
    }

    // Aktif urunleri getir
    @Transactional(readOnly = true)
    public List<ProductResponse> getActiveProducts() {
        return productRepository.findByActiveTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Kategoriye gore filtrele
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .filter(product -> Boolean.TRUE.equals(product.getActive()))
                .map(this::toResponse)
                .toList();
    }

    // Urun ara
    @Transactional(readOnly = true)
    public List<ProductResponse> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .filter(product -> Boolean.TRUE.equals(product.getActive()))
                .map(this::toResponse)
                .toList();
    }

    // Yeni urun ekle
    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product();

        applyRequest(product, request);

        return toResponse(productRepository.save(product));
    }

    // Urun guncelle
    public ProductResponse updateProduct(
            Long id, ProductRequest request) {

        Product product = findProduct(id);

        applyRequest(product, request);

        return toResponse(productRepository.save(product));
    }

    // Urunu pasif hale getir
    public void deleteProduct(Long id) {
        Product product = findProduct(id);

        product.setActive(false);

        productRepository.save(product);
    }

    // Yardimci metotlar
    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Urun bulunamadi: " + id));
    }

    private void applyRequest(
            Product product, ProductRequest request) {

        Category category = categoryRepository
                .findById(request.categoryId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Kategori bulunamadi."));

        product.setCategory(category);
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());
        product.setImageUrl(request.imageUrl());
        product.setActive(request.active());
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getImageUrl(),
                product.getActive()
        );
    }
}
