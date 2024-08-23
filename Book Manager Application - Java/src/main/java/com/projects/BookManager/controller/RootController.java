package com.projects.BookManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.projects.BookManager.Entities.User;
import com.projects.BookManager.Helpers.LoginHelper;
import com.projects.BookManager.Services.UserServices;

@ControllerAdvice
public class RootController {

    // private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserServices userServices;

    @ModelAttribute
    public void updateLoggedUserInfo(Authentication authentication, Model model) {
        if (authentication == null)
            return;

        // System.out.println("Updating logged user information to the model");

        String name = LoginHelper.getUsernameOfLoggedUser(authentication);
        // logger.info("User: {}", name);

        User user = userServices.getUserByName(name);

        // System.out.println(user.getName() + " : " + user.getEmail());
        model.addAttribute("loggedUser", user);

    }
}
