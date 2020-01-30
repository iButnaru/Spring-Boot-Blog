package SpringBoot.SpringBootBlog.service.implementation;

import SpringBoot.SpringBootBlog.model.Role;
import SpringBoot.SpringBootBlog.model.User;
import SpringBoot.SpringBootBlog.repository.RoleRepository;
import SpringBoot.SpringBootBlog.repository.UserRepository;
import SpringBoot.SpringBootBlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Implementation for {@link UserService}
 *
 * @author Ionut
 */

@Service
public class UserServiceImpl implements UserService {

    public static final String USER_ROLE = "ROLE_USER";

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;


    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        String[] userRoles = user.getRoles().stream().map(Role::getRole).toArray(String[]::new);
        return AuthorityUtils.createAuthorityList(userRoles);
    }

    /**
     * Finds {@link User} object in DB
     *
     * @param username identifier for the user
     * @return found object from DB
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Finds {@link User} object in DB
     *
     * @param email identifier for the user
     * @return found object from DB
     */
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Saves the {@link User} object to the DB.
     *
     * @param user - the instance that will be saved
     * @return  save method from repository
     */
    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setRoles(Collections.singletonList(roleRepository.findByRole(USER_ROLE)));
        return userRepository.save(user);
    }

    /**
     * Finds  user entity and is used by an instance of {@link org.springframework.security.authentication.AuthenticationProvider} in order to authenticate a user.
     *
     * @param email the username based on which the user entity is found.
     * @return {@link org.springframework.security.core.userdetails.User} model containing information used in the authentication process
     * @throws UsernameNotFoundException if no user corresponding with the provided username(email) is found
     */
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(), true, true, true,
                getAuthorities(user));
    }

}
