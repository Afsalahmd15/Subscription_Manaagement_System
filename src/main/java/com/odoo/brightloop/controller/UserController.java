package com.odoo.brightloop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.odoo.brightloop.model.UserModel;
import com.odoo.brightloop.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String emailId,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        UserModel user = userService.login(emailId, password);

        if (user == null) {
            model.addAttribute("LoginFailed",
                    "Invalid email or password!");
            return "index";
        }

        // Create session
        session.setAttribute("userId", user.getUserId());
        session.setAttribute("emailId", user.getEmailId());
        session.setAttribute("role", user.getRole());

        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(HttpSession session) {

        // Check whether user is logged in
        if (session.getAttribute("userId") == null) {
            return "redirect:/index";
        }

        Integer userId = (Integer) session.getAttribute("userId");
        String emailId = (String) session.getAttribute("emailId");
        String role = (String) session.getAttribute("role");

        return "home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/index";
    }

    @GetMapping("/")
    public String loginPage() {
       
        return "index";
    }
}