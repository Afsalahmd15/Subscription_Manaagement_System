package com.odoo.brightloop.controller;

import com.odoo.brightloop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class RegisterController {
    
    private final UserService userService;
    public RegisterController(UserService userService){

        this.userService = userService;
    }
    @PostMapping("/register")
    public String register(
        @RequestParam String email,
        @RequestParam String password,
        @RequestParam String confirmPassword,
        Model model) {

        UserService.RegisterStatus status = userService.regUser(
            email, password, confirmPassword);

         switch (status) {

            case REGISTER_SUCCESS:
                model.addAttribute("registerUserSuccess",
                    "Registration successful!");
                 return "index";

            case EMAIL_EXISTS:
                model.addAttribute("registerFailed",
                    "Email already exists!");
                return "register";

            case PASSWORD_MISSMATCH:
                model.addAttribute("registerFailed",
                    "Passwords do not match!");
                 return "register";

            default:
                 model.addAttribute("registerFailed",
                    "Registration failed!");
                return "register";
    }
    }
}
