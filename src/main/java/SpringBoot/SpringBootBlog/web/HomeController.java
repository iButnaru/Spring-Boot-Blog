package SpringBoot.SpringBootBlog.web;

import SpringBoot.SpringBootBlog.model.Post;
import SpringBoot.SpringBootBlog.service.implementation.PostServiceImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Controller that handles the mapping in order to see the home page with all the posts.
 *
 * @author Ionut
 */
@RequestMapping("/")
@Controller
public class HomeController {

    @Autowired
    PostServiceImple postService;

    /**
     * Handles the request {@link GetMapping}  used to retrieve the view for the home page with all posts.
     *
     * @param model {@link Model} used to add the attributes that will be returned by the view
     * @param page  integer that helps to identify which page (of 5 posts) will returned.
     * @return the page with all posts
     */
    @GetMapping("/")
    public String home(Model model, @RequestParam(defaultValue = "0") int page) {

        Page<Post> posts = postService.findAllPaged(page);
        model.addAttribute("posts", posts);
        model.addAttribute("page", page);
        return "home";
    }

    /**
     * Handles the request {@link GetMapping}  used to retrieve the view for the home page with all posts sorted by category.
     *
     * @param model {@link Model} used to add the attributes that will be returned by the view
     * @param page  integer that helps to identify which page (of 10 posts) will returned.
     * @return the page with all posts sorted by category.
     */
    @GetMapping("/sort")
    public String getSortedPosts(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Post> sortedPosts = postService.getAllSorted(page);
        model.addAttribute("sorted", sortedPosts);
        return "sortedHome";
    }
}

