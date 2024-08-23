package com.projects.BookManager.Services.Implementation;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.projects.BookManager.Entities.User;
import com.projects.BookManager.Helpers.ResourceNotFoundException;
import com.projects.BookManager.Repositories.UserRepository;
import com.projects.BookManager.Services.UserServices;

@Service
public class UserServiceImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Boolean saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return userRepository.existsById(user.getUserId());
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User user2 = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setProfilePicLink(user.getProfilePicLink());

        return Optional.ofNullable(userRepository.save(user2));
    }

    @Override
    public String deleteUser(Long id) {
        userRepository
                .delete(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found")));
        return "Deleted";
    }

    @Override
    public Boolean userExists(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public Boolean userExistsByMail(String mail) {
        User user = userRepository.findByEmail(mail).orElse(null);
        return user == null ? false : true;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findByName(name).orElse(null);
    }

}
