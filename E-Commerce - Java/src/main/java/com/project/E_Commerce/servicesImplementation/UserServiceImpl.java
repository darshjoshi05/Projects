package com.project.E_Commerce.servicesImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.E_Commerce.entities.User;
import com.project.E_Commerce.repositories.UserRepository;
import com.project.E_Commerce.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean disableUser(Integer id) {
        User user = userRepository.findById(id).get();
        user.setStatus("INACTIVE");
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean enableUser(Integer id) {
        User user = userRepository.findById(id).get();
        user.setStatus("ACTIVE");
        userRepository.save(user);
        return true;
    }

    @Override
    public void updateUser(User oldUser) {
        User newUser = new User();

        newUser.setId(oldUser.getId());
        newUser.setName(oldUser.getName());
        newUser.setMail(oldUser.getMail());
        newUser.setNumber(oldUser.getNumber());
        newUser.setStatus(oldUser.getStatus());
        newUser.setPassword(oldUser.getPassword());

        userRepository.save(newUser);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id).get();
    }

    @Override
    public int getUserCount() {
        return (int) userRepository.count();
    }

    @Override
    public User getUserByMail(String mail) {
        return userRepository.findByMail(mail);
    }

}
