package com.projects.BookManager.Services;

import java.util.List;
import java.util.Optional;
import com.projects.BookManager.Entities.User;

public interface UserServices {

    Boolean saveUser(User user);

    Optional<User> getUserById(Long id);

    Optional<User> updateUser(User user);

    String deleteUser(Long id);

    Boolean userExists(Long id);

    Boolean userExistsByMail(String mail);

    List<User> getAll();

    User getUserByName(String name);
}
