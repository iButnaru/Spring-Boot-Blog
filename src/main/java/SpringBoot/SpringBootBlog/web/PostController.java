package SpringBoot.SpringBootBlog.web;

import SpringBoot.SpringBootBlog.model.Post;
import SpringBoot.SpringBootBlog.model.User;
import SpringBoot.SpringBootBlog.service.implementation.CommentServiceImpl;
import SpringBoot.SpringBootBlog.service.implementation.PostServiceImple;
import SpringBoot.SpringBootBlog.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

/**
 * Controller that handles the mapping for the actions related to the posts.
 *
 * @author Ionut
 */
@Controller
@RequestMapping("/")
public class PostController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    PostServiceImple postService;
    @Autowired
    CommentServiceImpl commentService;


    /**
     * Method that check if the current registered user is the owner of the post.
     *
     * @param principal indicates the current logged user
     * @param post      {@link Post} instance on which is checked who is the user
     * @return true if the current logged user is the one who added the post, false otherwise
     */
    private boolean isPrincipalOwnerOfPost(Principal principal, Post post) {
        return principal != null && principal.getName().equals(post.getUser().getEmail());
    }

    /**
     * Handles the request {@link GetMapping}  used to map retrieve the view for adding a new post in DB.
     *
     * @param model     {@link Model} used to add the attributes that will be returned by the view
     * @param principal {@link Principal} object which stores the currently logged in user.
     * @return the name of the view where the post can be added
     */
    @GetMapping("/newPost")
    public String showForm(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Post post = new Post();
        post.setUser(user);
        model.addAttribute("post", post);
        return "post";
    }

    /**
     * Handles request {@link PostMapping} in order to retrieve all the information and save it after to DB
     *
     * @param post          {@link Post} object that will be added in the DB.
     * @param bindingResult instance where we can find all the errors
     * @param model         {@link Model} used to add the attributes that will be returned by the view
     * @return returns the post for if there are errors, otherwise, you get redirected to the home page
     */
    @PostMapping("/newPost")
    public String createNewPost(@Valid Post post, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "post";
        } else {
            postService.save(post);
            return "redirect:/";
        }
    }

    /**
     * Handles the request {@link GetMapping}  used to map retrieve the view with a specific post found by id and also the number of its comments.
     *
     * @param id        {@link Post} identifier
     * @param model     {@link Model} used to add the attributes that will be returned by the view
     * @param principal {@link Principal} object which stores the currently logged in user
     * @return the name of the view where the post is being displayed
     */
    @GetMapping("/post/{id}")
    public String getPostById(@PathVariable Long id, Model model, Principal principal) {
        Optional<Post> opPost = postService.findById(id);
        if (opPost.isPresent()) {
            Post post = opPost.get();
            Integer count = commentService.countComments(post);
            model.addAttribute("count", count);
            model.addAttribute("post", post);
            if (isPrincipalOwnerOfPost(principal, post)) {
                model.addAttribute("username", principal.getName());
            }
        }
        return "/showPost";
    }

    /**
     * Handles request {@link GetMapping} in order to retrieve the view with the post found in DB.
     *
     * @param id        {@link Post} identifier
     * @param model     {@link Model} used to add the attributes that will be returned by the view
     * @param principal {@link Principal} object which stores the currently logged in user
     * @return the form where the post can be edited
     */
    @GetMapping("/editPost/{id}")
    public String editPostById(@PathVariable Long id, Model model, Principal principal) {

        Optional<Post> opPost = postService.findById(id);

        if (opPost.isPresent()) {
            Post post = opPost.get();
            model.addAttribute("post", post);
            if (isPrincipalOwnerOfPost(principal, post)) {
                model.addAttribute("username", principal.getName());
            }
        }
        return "post";
    }

    /**
     * Handles request {@link PostMapping}  that after post in found in DB, will delete it.
     *
     * @param id        {@link Post} identifier
     * @param principal {@link Principal} object which stores the currently logged in user
     * @return if the action is successful you will be redirected to the home page, otherwise, to the post
     */
    @PostMapping("/post/{id}")
    public String deletePost(@PathVariable Long id, Principal principal) {

        Optional<Post> opPost = postService.findById(id);
        if (opPost.isPresent()) {
            Post post = opPost.get();
            if (isPrincipalOwnerOfPost(principal, post)) {
                postService.delete(post);
                return "redirect:/";
            }
        }
        return "redirect:/showPost";
    }
}
