package com.project.E_Commerce.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.E_Commerce.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "SELECT name, image_name FROM category", nativeQuery = true)
    List<Object[]> findCategoriesExcludingImage();
}
