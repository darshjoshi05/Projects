package com.project.E_Commerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.E_Commerce.entities.User;
import com.project.E_Commerce.services.UserService;

@Controller
public class HomeController {

    @Autowired
    private UserService userServices;

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/log-in")
    public String logIn(Model model) {
        model.addAttribute("user", new User());
        return "log-in";
    }

    @PostMapping("/log-in")
    public String logIn(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);

        // implement log in logic
        // update if-else for success and error

        return "log-in";
    }

    @GetMapping("/sign-in")
    public String signIn(Model model) {
        model.addAttribute("user", new User());
        return "sign-in";
    }

    @PostMapping("/sign-in")
    public String signIn(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);

        User user1 = new User();
        user1.setName(user.getName());
        user1.setMail(user.getMail());
        user1.setPassword(user.getPassword());
        user1.setNumber(user.getNumber());
        user1.setStatus("ACTIVE");

        userServices.addUser(user1);
        model.addAttribute("success", true);
        // update if-else for success and error

        return "sign-in";
    }
}
