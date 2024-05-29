package by.waitaty.authserver.service;



import by.waitaty.authserver.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);

    User updateUser(User user);
}
