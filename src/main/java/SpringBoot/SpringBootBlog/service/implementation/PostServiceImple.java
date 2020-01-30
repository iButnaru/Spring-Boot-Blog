package SpringBoot.SpringBootBlog.service.implementation;

import SpringBoot.SpringBootBlog.model.Post;
import SpringBoot.SpringBootBlog.repository.PostRepository;
import SpringBoot.SpringBootBlog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation for {@link PostService}
 *
 * @author Ionut
 */
@Service
public class PostServiceImple implements PostService {

    @Autowired
    PostRepository postRepository;

    /**
     * Finds {@link Post} object in DB
     *
     * @param id identifier for the post
     * @return {@link Post} found object from DB
     */
    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    /**
     * Saves {@link Post} object in DB
     * @param post the instance that will be saved
     */
    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    /**
     * Finds all Posts from DB and then returns them 5 by 5
     * @param page number of the first page where the  objects will be shown
     * @return all {@link Post} instances from DB
     */
    @Override
    public Page<Post> findAllPaged(int page) {
        return postRepository.findAll(PageRequest.of(page, 5));
    }

    /**
     * Finds all posts from DB and then returns 10 by 10, sorted by category.
     * @param page number of the first page where the {@link Post} instances will be shown
     * @return {@link Post} instances from DB
     */
    @Override
    public Page<Post> getAllSorted(int page) {
        return postRepository.findAll(PageRequest.of(page, 10, Sort.by("category")));
    }

    /**
     * Deletes {@link Post} object from DB
     */
    @Override
    public void delete(Post post) {
        postRepository.delete(post);

    }

}
