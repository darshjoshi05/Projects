package com.project.E_Commerce.services;

import java.util.List;

import com.project.E_Commerce.entities.User;
import com.project.E_Commerce.entities.UserProduct;

public interface UserProductService {

    public void addProduct(UserProduct product);

    public void deleteProduct(Integer id);

    public List<UserProduct> getCart(boolean inCart, User user);

    public List<UserProduct> getWishlist(boolean inWishlist, User user);

    public List<UserProduct> getOrdered(boolean ordered, User user);

    public void orderProduct(Integer id);

    public void cartProduct(Integer id);

    public int getOrderCount();

    public int getOrderValue();

    public List<UserProduct> getAllOrders();

}
