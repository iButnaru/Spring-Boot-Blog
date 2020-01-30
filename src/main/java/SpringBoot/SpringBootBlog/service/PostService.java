package SpringBoot.SpringBootBlog.service;

import SpringBoot.SpringBootBlog.model.Post;
import SpringBoot.SpringBootBlog.service.implementation.PostServiceImple;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * Services that extends the {@link PostServiceImple}, where found functionality for {@link Post}.
 *
 * @author Ionut
 * @see PostServiceImple
 */
public interface PostService {

    Optional<Post> findById(Long id);

    Post save(Post post);

    void delete(Post post);

    Page<Post> findAllPaged(int page);

    Page<Post> getAllSorted(int page);
}
