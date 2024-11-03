package com.project.E_Commerce.services;

import java.util.List;

import com.project.E_Commerce.entities.User;

public interface UserService {

    public void addUser(User user);

    public boolean disableUser(Integer id);

    public boolean enableUser(Integer id);

    public void updateUser(User user);

    public void deleteUser(User user);

    public User getUserById(Integer id);

    public List<User> getAllUsers();

    public int getUserCount();

    public User getUserByMail(String mail);
}
