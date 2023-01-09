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
@RequestMapping("/login")
public class LoginController {
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
        return "login";
    }

    @PostMapping("")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session
    ) throws Exception {
        User user = userService.loginUser(username, password);

        if (user ==null) {
            session.setAttribute("Error", "Username or Password incorrect");
            return "redirect:/login";
        } else {
            session.setAttribute("Username", user.getUsername());
            session.setAttribute("Fullname", user.getFullname());
            return "redirect:/home";
        }
    }
}
