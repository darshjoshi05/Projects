package com.projects.BookManager.Services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.projects.BookManager.Entities.Book;
import com.projects.BookManager.Entities.Status;
import com.projects.BookManager.Entities.User;

public interface BookServices {

    Boolean saveBook(Book book);

    Boolean updateBook(Book book);

    Boolean deleteBook(Long id);

    List<Book> getAllBooksByUser(User user);

    Page<Book> getAllBooksByUser(User user, int page, int size, String sortField, String sortDirection);

    List<Book> getCurrentBooksByUser(User user, Status status);

    List<Book> searchByTitle(User user, String title);

    List<Book> searchByAuthor(User user, String author);

}
