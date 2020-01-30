package SpringBoot.SpringBootBlog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping()
    public String showLogin() {
        return "login";
    }

    @PostMapping()
    public String login(Principal principal) {
        if (principal != null) {
            return "redirect:/";
        }
        return "redirect:/";
    }
}
