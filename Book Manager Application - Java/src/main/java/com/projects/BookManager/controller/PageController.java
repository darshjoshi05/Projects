package com.projects.BookManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.projects.BookManager.Entities.User;
import com.projects.BookManager.Forms.UserForm;
import com.projects.BookManager.Services.UserServices;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @Autowired
    UserServices userServices;

    @GetMapping("/home")
    public String homePage() {
        return "index";
    }

    @GetMapping("/log-in")
    public String logIn(Model model) {
        UserForm uf = new UserForm();
        model.addAttribute("uf", uf);
        return "log-in";
    }

    @RequestMapping("/register")
    public String register(org.springframework.ui.Model model) {
        UserForm uf = new UserForm();
        model.addAttribute("uf", uf);
        return "sign-in";
    }

    @GetMapping("/sign-in")
    public String getSignIn(Model model) {
        UserForm uf = new UserForm();
        model.addAttribute("uf", uf);
        return "sign-in";
    }

    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public String signIn(@ModelAttribute UserForm uf, Model model) {
        model.addAttribute("uf", uf);

        User user = new User();
        user.setName(uf.getName());
        user.setEmail(uf.getMail());
        user.setPassword(uf.getPassword());
        user.setAbout(uf.getAbout());
        user.setPhoneNumber(uf.getPhoneNumber());

        if (userServices.saveUser(user)) {
            model.addAttribute("success", true);
        } else {
            model.addAttribute("error", true);
        }

        return "sign-in"; // redirect:/register - redirects it to the mentioned page
    }
}