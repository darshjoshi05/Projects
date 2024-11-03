package com.project.E_Commerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.project.E_Commerce.entities.Category;
import com.project.E_Commerce.entities.Product;
import com.project.E_Commerce.entities.User;
import com.project.E_Commerce.services.CategoryService;
import com.project.E_Commerce.services.ProductService;
import com.project.E_Commerce.services.UserProductService;
import com.project.E_Commerce.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserProductService userProductService;

    @GetMapping()
    public String adminHome(Model model) {
        model.addAttribute("totalProducts", productService.getProductCount());
        model.addAttribute("totalCategories", categoryService.getCategoryCount());
        model.addAttribute("totalOrders", userProductService.getOrderCount());
        model.addAttribute("totalAmount", userProductService.getOrderValue());
        model.addAttribute("totalUsers", userService.getUserCount());
        return "/admin/admin";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "/admin/users";
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("orders", userProductService.getAllOrders());
        return "/admin/admin_orders";
    }

    @PostMapping("/users/active/{id}")
    public String enableOrDisableUser(@PathVariable Integer id, Model model) {
        userService.enableUser(id);
        model.addAttribute("users", userService.getAllUsers());
        return "redirect:/admin/users";
    }

    @PostMapping("/users/inactive/{id}")
    public String disableUser(@PathVariable Integer id, Model model) {
        userService.disableUser(id);
        model.addAttribute("users", userService.getAllUsers());
        return "redirect:/admin/users";
    }

    @GetMapping("/add_category")
    public String addCategory(Model model) {
        model.addAttribute("category", new Category());
        return "/admin/add_category";
    }

    @PostMapping("/add_category")
    public String addCategory(@RequestPart("image") MultipartFile image, @ModelAttribute Category category,
            Model model) {
        categoryService.addCategory(category, image);
        model.addAttribute("success", true);
        return "/admin/add_category";
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "/admin/categories";
    }

    @PostMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Integer id, Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        categoryService.deleteCategory(categoryService.getCategoryById(id));
        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/edit/{id}")
    public String editCategory(@PathVariable Integer id, Model model) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        return "/admin/edit_category";
    }

    @PostMapping("/categories/update")
    public String updateCategory(@RequestPart("image") MultipartFile image, @ModelAttribute Category category,
            Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        categoryService.updateCategory(category, image);
        return "redirect:/admin/categories";
    }

    @GetMapping("/add_product")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/admin/add_product";
    }

    @PostMapping("/add_product")
    public String addProduct(@RequestPart("image") MultipartFile image, @ModelAttribute Product product, Model model) {

        productService.addProduct(product, image);
        model.addAttribute("success", true);
        return "/admin/add_product";
    }

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "/admin/products";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Integer id, Model model) {
        model.addAttribute("products", productService.getAllProducts());
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }

    @PostMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/admin/edit_product";
    }

    @PostMapping("/products/update")
    public String updateProduct(@ModelAttribute Product product, Model model,
            @RequestPart("image") MultipartFile image) {
        model.addAttribute("products", productService.getAllProducts());
        productService.updateProduct(product, image);
        return "redirect:/admin/products";
    }

}
