package com.project.E_Commerce.servicesImplementation;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.E_Commerce.entities.Product;
import com.project.E_Commerce.repositories.ProductRepository;
import com.project.E_Commerce.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void addProduct(Product product, MultipartFile imageFile) {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        try {
            product.setImageData(imageFile.getBytes());
        } catch (IOException e) {
            System.out.println("Unable to save image file: " + imageFile.getOriginalFilename());
            e.printStackTrace();
        }
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Product oldProduct, MultipartFile imageFile) {
        Product product = productRepository.findById(oldProduct.getId()).get();

        product.setName(oldProduct.getName());
        product.setQuantity(oldProduct.getQuantity());
        product.setDescription(oldProduct.getDescription());
        product.setInCart(oldProduct.isInCart());
        product.setInWishlist(oldProduct.isInWishlist());
        product.setTotal(oldProduct.getTotal());
        product.setOrdered(oldProduct.isOrdered());
        product.setPrice(oldProduct.getPrice());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        try {
            product.setImageData(imageFile.getBytes());
        } catch (IOException e) {
            System.out.println("Unable to save image file: " + imageFile.getOriginalFilename());
            e.printStackTrace();
        }
        productRepository.save(product);

    }

    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(Integer id) {
        Product product = productRepository.findById(id).get();

        byte[] imageData = product.getImageData();
        String base64Image = Base64.getEncoder().encodeToString(imageData);
        product.setBase64Image(base64Image);

        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            byte[] imageData = product.getImageData();
            String base64Image = Base64.getEncoder().encodeToString(imageData);
            product.setBase64Image(base64Image);
        }

        return products;
    }

    @Override
    public int getProductCount() {
        return (int) productRepository.count();
    }

    @Override
    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContaining(name);
    }

    @Override
    public List<Product> getProductsByCategory(Integer id) {
        return productRepository.findByCategoryId(id);
    }

}
