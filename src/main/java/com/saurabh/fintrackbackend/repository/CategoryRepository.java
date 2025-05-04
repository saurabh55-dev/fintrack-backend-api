package com.saurabh.fintrackbackend.repository;

import com.saurabh.fintrackbackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
