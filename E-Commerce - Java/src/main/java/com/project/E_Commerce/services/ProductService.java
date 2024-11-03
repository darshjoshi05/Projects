package com.project.E_Commerce.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.project.E_Commerce.entities.Product;

public interface ProductService {

    public void addProduct(Product product, MultipartFile imageFile);

    public void updateProduct(Product product, MultipartFile imageFile);

    public void deleteProduct(Integer id);

    public Product getProductById(Integer id);

    public List<Product> getAllProducts();

    public int getProductCount();

    public List<Product> searchProducts(String name);

    public List<Product> getProductsByCategory(Integer id);

}
