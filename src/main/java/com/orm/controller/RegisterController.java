package com.orm.controller;

import com.orm.entity.User;
import com.orm.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String index(
            HttpSession session,
            ModelMap model
    ) {
        System.out.println();

        model.addAttribute("error", session.getAttribute("Error"));
        session.removeAttribute("Error");
        return "register";
    }

    @PostMapping("")
    public String register(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("fullname") String fullname,
            HttpSession session
    ) throws Exception {
        User user = userService.registerUser(username, password, fullname);

        if (user == null) {
            session.setAttribute("Error", "Register username fail, Username may exists");
            return "redirect:/register";
        } else {
            session.setAttribute("Username", user.getUsername());
            session.setAttribute("Fullname", user.getFullname());
            return "redirect:/home";
        }
    }
}
