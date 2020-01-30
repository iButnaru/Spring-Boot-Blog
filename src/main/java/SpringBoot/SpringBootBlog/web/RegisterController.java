package SpringBoot.SpringBootBlog.web;

import SpringBoot.SpringBootBlog.model.User;
import SpringBoot.SpringBootBlog.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller that handles the mapping for registering the user.
 *
 * @author Ionut
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    UserServiceImpl userService;

    @ModelAttribute("user")
    public User user() {
        return new User();
    }

    /**
     * Handles the request {@link GetMapping}  used to map retrieve the view for the user registration process.
     *
     * @param model {@link Model} used to add attributes that requires to be returned to the View.
     * @return the name of the view where a user can register.
     */
    @GetMapping
    public String showRegistration(Model model) {
        return "register";
    }

    /**
     * Handles request {@link PostMapping} in order to retrieve all the information and save it after to DB.
     *
     * @param user   the new object with all the information to be saved in DB.
     * @param result instance where we can find all the errors
     * @return in case of a successful registration, returns the login page, otherwise, returns the same registration page
     */
    @PostMapping
    public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult result) {

        if (result.hasErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError error : errors) {
                System.out.println(error.getObjectName() + " - " + error.getDefaultMessage());
            }
            return "register";
        }
        if (!result.hasErrors()) {
            userService.save(user);
            return "redirect:/login?success";
        }
        List<FieldError> errors = result.getFieldErrors();
        return "redirect:/login?success";
    }
}

