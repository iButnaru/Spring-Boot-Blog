package SpringBoot.SpringBootBlog.repository;

import SpringBoot.SpringBootBlog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional
    User findByEmail(@Param("email") String email);

    @Transactional
   Optional<User>  findByUsername(@Param("username") String username);
}
