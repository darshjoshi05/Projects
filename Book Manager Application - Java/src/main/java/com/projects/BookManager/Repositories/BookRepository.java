package com.projects.BookManager.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.BookManager.Entities.Book;
import com.projects.BookManager.Entities.Status;
import com.projects.BookManager.Entities.User;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByUserAndTitleContaining(User user, String title);

    List<Book> findByUserAndAuthorContaining(User user, String author);

    List<Book> findByUser(User user);

    List<Book> findByUserAndStatus(User user, Status status);

    Page<Book> findByUser(User user, Pageable pageable);

}
