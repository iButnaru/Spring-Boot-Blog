package SpringBoot.SpringBootBlog.repository;

import SpringBoot.SpringBootBlog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Override
    Optional<Comment> findById(Long id);
}
