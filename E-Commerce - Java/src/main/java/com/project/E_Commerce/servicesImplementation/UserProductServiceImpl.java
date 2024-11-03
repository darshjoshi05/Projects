package com.project.E_Commerce.servicesImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.E_Commerce.entities.User;
import com.project.E_Commerce.entities.UserProduct;
import com.project.E_Commerce.repositories.UserProductRepository;
import com.project.E_Commerce.services.UserProductService;

@Service
public class UserProductServiceImpl implements UserProductService {

    @Autowired
    private UserProductRepository userProductRepository;

    @Override
    public void addProduct(UserProduct product) {
        userProductRepository.save(product);
    }

    @Override
    public void deleteProduct(Integer id) {
        userProductRepository.deleteById(id);
    }

    @Override
    public List<UserProduct> getCart(boolean inCart, User user) {
        return userProductRepository.getByInCartAndUser(true, user);
    }

    @Override
    public List<UserProduct> getWishlist(boolean inWishlist, User user) {
        return userProductRepository.getByInWishlistAndUser(true, user);
    }

    @Override
    public List<UserProduct> getOrdered(boolean ordered, User user) {
        return userProductRepository.getByOrderedAndUser(true, user);
    }

    @Override
    public void orderProduct(Integer id) {
        UserProduct product = userProductRepository.findById(id).get();
        product.setId(id);
        product.setInCart(false);
        product.setOrdered(true);
        userProductRepository.save(product);
    }

    @Override
    public void cartProduct(Integer id) {
        UserProduct product = userProductRepository.findById(id).get();
        product.setId(id);
        product.setInCart(true);
        product.setInWishlist(false);
        userProductRepository.save(product);
    }

    @Override
    public int getOrderCount() {
        return (int) userProductRepository.count();
    }

    @Override
    public int getOrderValue() {
        return userProductRepository.sumByTotal();
    }

    @Override
    public List<UserProduct> getAllOrders() {
        return userProductRepository.findAll();
    }

}
