package com.project.E_Commerce.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.project.E_Commerce.entities.Category;

public interface CategoryService {

    public void addCategory(Category category, MultipartFile imagFile);

    public void updateCategory(Category category, MultipartFile imagFile);

    public void deleteCategory(Category category);

    public Category getCategoryById(Integer id);

    public List<Category> getAllCategories();

    public int getCategoryCount();

}
