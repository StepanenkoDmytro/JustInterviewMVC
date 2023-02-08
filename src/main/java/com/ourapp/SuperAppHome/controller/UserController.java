package com.ourapp.SuperAppHome.controller;

import com.ourapp.SuperAppHome.model.User;
import com.ourapp.SuperAppHome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String getUserPage(@AuthenticationPrincipal UserDetails userDetails, Model model){
        User user = userService.getUserByEmail(userDetails.getUsername()).get();
        model.addAttribute("user",user);
        return "user-main";
    }

    @GetMapping("/user/{id}/edit")
    public String userEditPage(@PathVariable(name = "id") long id, Model model) {
        User user = userService.getUserById(id).get();
        model.addAttribute("user",user);
        return "user-edit";
    }

    @PostMapping("/user/{id}/edit")
    public String userEdit(@PathVariable(name = "id") long id,
                           @RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String first_name,
                           @RequestParam String last_name) {
        User user = userService.getUserById(id).get();
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(first_name);
        user.setLastName(last_name);
        userService.saveUser(user);
        return "redirect:/api/v1/user";
    }
}
