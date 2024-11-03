package com.project.E_Commerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.E_Commerce.entities.User;
import com.project.E_Commerce.entities.UserProduct;

@Repository
public interface UserProductRepository extends JpaRepository<UserProduct, Integer> {

    public List<UserProduct> getByInCartAndUser(boolean inCart, User user);

    public List<UserProduct> getByInWishlistAndUser(boolean inWishlist, User user);

    public List<UserProduct> getByOrderedAndUser(boolean b, User user);

    @Query(value = "SELECT SUM(total) FROM user_product", nativeQuery = true)
    public int sumByTotal();

}
