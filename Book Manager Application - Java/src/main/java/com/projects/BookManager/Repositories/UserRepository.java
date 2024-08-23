package com.projects.BookManager.Repositories;

import org.springframework.stereotype.Repository;
import com.projects.BookManager.Entities.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String mail);

    Optional<User> findByName(String name);

}
