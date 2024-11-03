package com.project.E_Commerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.E_Commerce.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByMail(String mail);
}
