package com.projects.BookManager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projects.BookManager.Entities.Book;
import com.projects.BookManager.Entities.User;
import com.projects.BookManager.Forms.BookForm;
import com.projects.BookManager.Helpers.LoginHelper;
import com.projects.BookManager.Services.BookServices;
import com.projects.BookManager.Services.UserServices;

@Controller
@RequestMapping(value = "/user/books")
public class BookController {

    @Autowired
    BookServices bookServices;

    @Autowired
    UserServices userServices;

    @RequestMapping("/add")
    public String addBook(Model model) {
        BookForm bf = new BookForm();
        model.addAttribute("bf", bf);
        return "user/add_book";
    }

    @PostMapping("/add")
    public String saveBook(Model model, @ModelAttribute BookForm bf, Authentication authentication) {

        String username = LoginHelper.getUsernameOfLoggedUser(authentication);
        User user = userServices.getUserByName(username);
        model.addAttribute("bf", bf);

        Book book = new Book();

        book.setTitle(bf.getTitle());
        book.setAuthor(bf.getAuthor());
        book.setPages(bf.getPages());
        book.setLanguage(bf.getLanguage());
        book.setCategory(bf.getCategory());
        book.setPublisher(bf.getPublisher());
        book.setStatus(bf.getStatus());
        book.setDescription(bf.getDescription());
        book.setUser(user);

        if (bookServices.saveBook(book)) {
            model.addAttribute("success", true);
        } else {
            model.addAttribute("error", true);
        }

        return "user/add_book"; // redirect:/user/books/add
    }

    @RequestMapping
    public String books(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sortBy", defaultValue = "title") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model,
            Authentication authentication) {

        String username = LoginHelper.getUsernameOfLoggedUser(authentication);
        User user = userServices.getUserByName(username);

        List<Book> books = bookServices.getAllBooksByUser(user);

        // For pagination view
        // Page<Book> books = bookServices.getAllBooksByUser(user, page, size, sortBy,
        // direction);

        model.addAttribute("books", books);

        return "user/books";
    }

    @RequestMapping("/search")
    public String searchHandler(
            @RequestParam("field") String field,
            @RequestParam("keyword") String keyword,
            Model model, Authentication authentication) {

        User user = userServices.getUserByName(LoginHelper.getUsernameOfLoggedUser(authentication));

        List<Book> books = null;
        if (field.equalsIgnoreCase("title")) {

            books = bookServices.searchByTitle(user, keyword);
            model.addAttribute("books", books);

        } else if (field.equalsIgnoreCase("author")) {

            books = bookServices.searchByAuthor(user, keyword);
            model.addAttribute("books", books);

        }

        return "user/search";
    }
}
