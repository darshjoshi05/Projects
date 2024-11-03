package com.project.E_Commerce.servicesImplementation;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.E_Commerce.entities.Category;
import com.project.E_Commerce.repositories.CategoryRepository;
import com.project.E_Commerce.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void addCategory(Category category, MultipartFile imagFile) {
        category.setImageName(imagFile.getOriginalFilename());
        category.setImageType(imagFile.getContentType());
        try {
            category.setImageData(imagFile.getBytes());
        } catch (IOException e) {
            System.out.println("Unable to save image file: " + imagFile.getOriginalFilename());
            e.printStackTrace();
        }
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Category category, MultipartFile imagFile) {
        Category category1 = categoryRepository.findById(category.getId()).get();

        category1.setName(category.getName());
        category1.setImageName(imagFile.getOriginalFilename());
        category1.setImageType(imagFile.getContentType());
        try {
            category1.setImageData(imagFile.getBytes());
        } catch (IOException e) {
            System.out.println("Unable to save image file: " + imagFile.getOriginalFilename());
            e.printStackTrace();
        }
        categoryRepository.save(category1);
    }

    @Override
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    public Category getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id).get();

        byte[] imageData = category.getImageData();
        String base64Image = Base64.getEncoder().encodeToString(imageData);
        category.setBase64Image(base64Image);

        return category;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        for (Category category : categories) {
            byte[] imageData = category.getImageData();
            String base64Image = Base64.getEncoder().encodeToString(imageData);
            category.setBase64Image(base64Image);
        }

        return categories;
    }

    @Override
    public int getCategoryCount() {
        return (int) categoryRepository.count();
    }

}
