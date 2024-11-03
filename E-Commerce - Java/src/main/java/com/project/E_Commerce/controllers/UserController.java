package com.project.E_Commerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.E_Commerce.entities.Product;
import com.project.E_Commerce.entities.User;
import com.project.E_Commerce.entities.UserProduct;
import com.project.E_Commerce.services.CategoryService;
import com.project.E_Commerce.services.ProductService;
import com.project.E_Commerce.services.UserProductService;
import com.project.E_Commerce.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserProductService userProductService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/explore")
    public String explore(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("products", productService.getAllProducts());
        return "user/user_products";
    }

    @GetMapping("/product/{id}")
    public String product(Model model, @PathVariable Integer id) {
        model.addAttribute("product", productService.getProductById(id));
        return "user/user_product";
    }

    @PostMapping("/product/cart/{id}")
    public String addToCart(@RequestParam Integer quantity, @PathVariable Integer id,
            Authentication user) {
        Product product = productService.getProductById(id);
        UserProduct product2 = new UserProduct();
        product2.setName(product.getName());
        product2.setPrice(product.getPrice());
        product2.setTotal(product.getPrice() * quantity);
        product2.setQuantity(quantity);
        product2.setInCart(true);
        product2.setProduct(productService.getProductById(id));
        product2.setUser(userService.getUserByMail(user.getName()));
        userProductService.addProduct(product2);
        return "redirect:/user/cart";
    }

    @GetMapping("/cart")
    public String cart(Authentication user, Model model) {
        User user2 = userService.getUserByMail(user.getName());
        model.addAttribute("products", userProductService.getCart(true, user2));
        return "user/cart";
    }

    @PostMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable Integer id) {
        userProductService.deleteProduct(id);
        return "redirect:/user/cart";
    }

    @PostMapping("/cart/order/{id}")
    public String order(@PathVariable Integer id) {
        userProductService.orderProduct(id);
        return "redirect:/user/cart";
    }

    @PostMapping("/product/wishlist/{id}")
    public String addToWishlist(@RequestParam Integer quantity, @PathVariable Integer id,
            Authentication user) {
        Product product = productService.getProductById(id);
        UserProduct product2 = new UserProduct();
        product2.setName(product.getName());
        product2.setPrice(product.getPrice());
        product2.setTotal(product.getPrice() * quantity);
        product2.setQuantity(quantity);
        product2.setInWishlist(true);
        product2.setProduct(productService.getProductById(id));
        product2.setUser(userService.getUserByMail(user.getName()));
        userProductService.addProduct(product2);
        return "redirect:/user/wishlist";
    }

    @GetMapping("/wishlist")
    public String wishlist(Authentication user, Model model) {
        User user2 = userService.getUserByMail(user.getName());
        model.addAttribute("products", userProductService.getWishlist(true, user2));
        return "user/wishlist";
    }

    @PostMapping("/wishlist/remove/{id}")
    public String removeFromWishlist(@PathVariable Integer id) {
        userProductService.deleteProduct(id);
        return "redirect:/user/wishlist";
    }

    @PostMapping("/wishlist/cart/{id}")
    public String toCart(@PathVariable Integer id) {
        userProductService.cartProduct(id);
        return "redirect:/user/wishlist";
    }

    @GetMapping("/profile")
    public String profile(Authentication user, Model model) {
        model.addAttribute("user", userService.getUserByMail(user.getName()));
        return "user/profile";
    }

    @PostMapping("/profile/{id}")
    public String profile(@ModelAttribute User newUser) {
        userService.updateUser(newUser);
        return "redirect:/user/explore";
    }

    @GetMapping("/orders")
    public String orders(Authentication user, Model model) {
        model.addAttribute("products", userProductService.getOrdered(true, userService.getUserByMail(user.getName())));
        return "user/orders";
    }

    @GetMapping("/products/search")
    public String search(Model model, @RequestParam("search") String name) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("products", productService.searchProducts(name));
        return "user/user_products";
    }

    @GetMapping("/category/{id}")
    public String category(Model model, @PathVariable Integer id) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("products", productService.getProductsByCategory(id));
        return "user/user_products";
    }

}