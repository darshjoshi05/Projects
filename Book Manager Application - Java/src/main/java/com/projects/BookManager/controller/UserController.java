package com.projects.BookManager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projects.BookManager.Entities.Book;
import com.projects.BookManager.Entities.Status;
import com.projects.BookManager.Entities.User;
import com.projects.BookManager.Forms.UserForm;
import com.projects.BookManager.Helpers.LoginHelper;
import com.projects.BookManager.Services.BookServices;
import com.projects.BookManager.Services.UserServices;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServices userServices;

    @Autowired
    BookServices bookServices;

    @RequestMapping(value = "/dashboard")
    public String userDashboard(Authentication authentication, Model model) {

        User user = userServices.getUserByName(LoginHelper.getUsernameOfLoggedUser(authentication));
        List<Book> books = bookServices.getCurrentBooksByUser(user, Status.READING);
        model.addAttribute("books", books);

        return "user/dashboard";
    }

    @RequestMapping(value = "/profile")
    public String userProfile(Model model) {

        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);

        // Added existing user data on the profile page
        // String username = LoginHelper.getUsernameOfLoggedUser(authentication);
        // User user = userServices.getUserByName(username);
        // model.addAttribute("user", user);

        return "user/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute UserForm userForm, Model model, Authentication authentication) {
        model.addAttribute("userForm", userForm);

        User user = userServices.getUserByName(LoginHelper.getUsernameOfLoggedUser(authentication));

        user.setName(userForm.getName());
        user.setEmail(userForm.getMail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());

        if (userServices.saveUser(user)) {
            model.addAttribute("success", true);
        } else {
            model.addAttribute("error", true);
        }

        return "user/profile";
    }
}
