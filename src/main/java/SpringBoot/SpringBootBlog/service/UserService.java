package SpringBoot.SpringBootBlog.service;

import SpringBoot.SpringBootBlog.model.User;
import SpringBoot.SpringBootBlog.service.implementation.UserServiceImpl;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

/**
 * Services that extends the {@link UserServiceImpl}, where found functionality for {@link User}.
 *
 * @author Ionut
 * @see UserServiceImpl
 */
public interface UserService  extends UserDetailsService{

    Optional<User> findByUsername(String username);

    User findByEmail(String email);
    User save (User user);
}
