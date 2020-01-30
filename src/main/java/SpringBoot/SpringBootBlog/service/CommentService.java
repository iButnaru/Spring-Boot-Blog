package SpringBoot.SpringBootBlog.service;

import SpringBoot.SpringBootBlog.model.Comment;
import SpringBoot.SpringBootBlog.model.Post;
import SpringBoot.SpringBootBlog.service.implementation.CommentServiceImpl;

/**
 * Services that extends the {@link CommentServiceImpl}, where found functionality for {@link Comment}.
 *
 * @author Ionut
 * @see CommentServiceImpl
 */
public interface CommentService {

    Comment save(Comment comment);

    Integer countComments(Post post);

}
