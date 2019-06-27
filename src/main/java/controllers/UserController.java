package controllers;

import dao.UserDao;
import model.Login;
import model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import services.ValidateAll;

@Controller
public class UserController {

    @GetMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("home", "login", new Login());
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute("login") Login login) {
        User user = UserDao.checkLogin(login);
        if (user == null) {
            ModelAndView modelAndView = new ModelAndView("error");
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("user");
            modelAndView.addObject("user", user);
            return modelAndView;
        }

    }

    @GetMapping("/signup")
    public ModelAndView showSignupPage() {
        ModelAndView modelAndView = new ModelAndView("signup");
        modelAndView.addObject("newUser", new User());

        return modelAndView;
    }

    @PostMapping("/signup")
    public ModelAndView signup(@ModelAttribute("newuser") User newuser) {
        ValidateAll validateAll = new ValidateAll();
        if (validateAll.validate(newuser).equals("pass")) {
            UserDao.getUsers().add(newuser);
            ModelAndView modelAndView = new ModelAndView("finish_signup");
            modelAndView.addObject("newuser", newuser);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("validate_error");
            modelAndView.addObject("validate_error", validateAll.validate(newuser));
            return modelAndView;
        }



    }

}
