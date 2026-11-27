package com.elifuyar.webproje.repository;

import com.elifuyar.webproje.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository
        extends JpaRepository<Category, Long> {
}
