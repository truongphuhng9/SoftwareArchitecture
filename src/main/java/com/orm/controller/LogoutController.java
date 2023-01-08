package com.orm.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout")
public class LogoutController {

    @PostMapping("")
    public String logout(
            HttpSession session
    ){
        session.removeAttribute("Username");
        return "redirect:/login";
    }
}
