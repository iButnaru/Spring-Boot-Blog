package SpringBoot.SpringBootBlog.web;

import SpringBoot.SpringBootBlog.model.Comment;
import SpringBoot.SpringBootBlog.model.Post;
import SpringBoot.SpringBootBlog.model.User;
import SpringBoot.SpringBootBlog.service.implementation.CommentServiceImpl;
import SpringBoot.SpringBootBlog.service.implementation.PostServiceImple;
import SpringBoot.SpringBootBlog.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * Controller that handles the mapping for the actions related to the comments.
 *
 * @author Ionut
 */
@Controller
@RequestMapping("/")
public class CommentController {

    @Autowired
    CommentServiceImpl commentService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    PostServiceImple postService;

    @ModelAttribute
    public Comment comment() {
        return new Comment();
    }

    /**
     * Handles request {@link PostMapping} in order to retrieve all the information and save it after to DB
     *
     * @param comment {@link Comment} object that will be added in the DB
     * @param result  nstance where we can find all the errors
     * @return if there are errors, returns the comment form, otherwise, it returns the post
     */
    @PostMapping("/createComment")
    public String saveComment(@Valid Comment comment, BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors) {
                System.out.println(error.getObjectName() + " - " + error.getDefaultMessage());
            }
            return "/commentForm";
        } else {
            commentService.save(comment);
            return "redirect:/post/" + comment.getPost().getId();
        }
    }

    /**
     * Handles the request {@link GetMapping}  used to map retrieve the view for adding a new comment in DB.
     *
     * @param id        {@link Comment} identifier
     * @param model     {@link Model} used to add the attributes that will be returned by the view
     * @param principal {@link Principal} object which stores the currently logged in user.
     * @return the name of the view where the comment can be added
     */
    @GetMapping("/commentPost/{id}")
    public String commentPost(@PathVariable("id") Long id, Model model, Principal principal) {

        Optional<Post> post = postService.findById(id);
        if (post.isPresent()) {
            Optional<User> user = Optional.ofNullable(userService.findByEmail(principal.getName()));
            if (user.isPresent()) {
                Comment comment = new Comment();
                comment.setUser(user.get());
                comment.setPost(post.get());
                model.addAttribute("comment", comment);
                return "/commentForm";

            } else {
                return "/error";
            }

        } else {
            return "/error";
        }
    }
}
