package SpringBoot.SpringBootBlog.service.implementation;

import SpringBoot.SpringBootBlog.model.Comment;
import SpringBoot.SpringBootBlog.model.Post;
import SpringBoot.SpringBootBlog.repository.CommentRepository;
import SpringBoot.SpringBootBlog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Implementation for {@link CommentService}
 *
 * @author Ionut
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    /**
     * Saves {@link Comment} object in DB
     *
     * @param comment {@link Comment} instance that will be saved in DB
     */
    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    /**
     * Gets all the comments of a certain {@link Post} and then returns number of the found comments
     *
     * @param post {@link Post} instance for which we get the number of comments
     * @return number of comments
     */
    @Override
    public Integer countComments(Post post) {
        Collection<Comment> count = post.getComments();
        return count.size();
    }
}
