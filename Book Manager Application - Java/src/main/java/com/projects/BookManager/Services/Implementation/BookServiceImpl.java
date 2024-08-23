package com.projects.BookManager.Services.Implementation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.projects.BookManager.Entities.Book;
import com.projects.BookManager.Entities.Status;
import com.projects.BookManager.Entities.User;
import com.projects.BookManager.Helpers.ResourceNotFoundException;
import com.projects.BookManager.Repositories.BookRepository;
import com.projects.BookManager.Services.BookServices;

@Service
public class BookServiceImpl implements BookServices {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Boolean saveBook(Book book) {
        bookRepository.save(book);
        return true;
    }

    @Override
    public Boolean updateBook(Book book) {
        Book book1 = bookRepository.findById(book.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found" + book.toString()));

        book1.setTitle(book.getTitle());
        book1.setAuthor(book.getAuthor());
        book1.setPages(book.getPages());
        book1.setLanguage(book.getLanguage());
        book1.setCategory(book.getCategory());
        book1.setPublisher(book.getPublisher());
        book1.setStatus(book.getStatus());
        book1.setDescription(book.getDescription());

        bookRepository.save(book1);
        return true;
    }

    @Override
    public Boolean deleteBook(Long id) {
        bookRepository.deleteById(id);
        return null;
    }

    @Override
    public List<Book> getAllBooksByUser(User user) {
        return bookRepository.findByUser(user);
    }

    @Override
    public List<Book> getCurrentBooksByUser(User user, Status status) {
        return bookRepository.findByUserAndStatus(user, status);
    }

    @Override
    public Page<Book> getAllBooksByUser(User user, int page, int size, String sortBy, String sortDirection) {

        Sort sort = sortDirection.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page, size, sort);
        return bookRepository.findByUser(user, pageable);
    }

    @Override
    public List<Book> searchByTitle(User user, String title) {
        return bookRepository.findByUserAndTitleContaining(user, title);
    }

    @Override
    public List<Book> searchByAuthor(User user, String author) {
        return bookRepository.findByUserAndAuthorContaining(user, author);
    }

}
