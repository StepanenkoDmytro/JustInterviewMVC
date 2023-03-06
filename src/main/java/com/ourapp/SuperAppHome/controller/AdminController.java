package com.ourapp.SuperAppHome.controller;

import com.ourapp.SuperAppHome.model.Role;
import com.ourapp.SuperAppHome.model.Status;
import com.ourapp.SuperAppHome.model.User;
import com.ourapp.SuperAppHome.repository.RoleRepository;
import com.ourapp.SuperAppHome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/api/v1")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/admin")
    public String adminPage(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users",users);
        return "admin-main";
    }

    @GetMapping("/admin/{id}/edit")
    public String adminEditPage(@PathVariable(name = "id") long id, Model model) {
        Optional<User> optional = userService.getUserById(id);
        if(optional.isEmpty()){
            throw new UsernameNotFoundException("User with ID: " + id + " not found");
        }
        User user = optional.get();
        model.addAttribute("user",user);

        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "admin-user-edit";
    }
    @PostMapping("/admin/{id}/edit")
    public String adminEdit(@PathVariable(name = "id") long id,
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
        return "redirect:/api/v1/admin";
    }

    @PostMapping("/admin/{id}/remove")
    public String removeUser(@PathVariable(name = "id") long id, Model model){
        userService.deleteUser(id);
        return "redirect:/api/v1/admin";
    }

    @PostMapping("/admin/edit/setrole")
    public String adminSetRole(@RequestParam(name = "id_user") long id_user,
                               @RequestParam(name = "roleID") long role_id,
                               Model model){
        User user = userService.getUserById(id_user).orElse(null);
        List<Role> userRoles = user.getRoles();
        Role role = roleRepository.findById(role_id).get();
        userRoles.add(role);
        user.setRoles(userRoles);
        userService.saveUser(user);

        return "redirect:/api/v1/admin/" + id_user + "/edit";
    }

    @PostMapping("/admin/edit/removerole")
    public String adminRemoveRole(@RequestParam(name = "id_user") long id,
                                  @RequestParam(name = "id_role") long id_role){

        User user = userService.getUserById(id).orElse(null);
        List<Role> userRoles = user.getRoles();
        Role role = roleRepository.findById(id_role).get();
        userRoles.remove(role);
        user.setRoles(userRoles);
        userService.saveUser(user);
        return "redirect:/api/v1/admin/" + id + "/edit";
    }

    @PostMapping("/admin/edit/status")
    public String adminEditStatus(@RequestParam(name = "status") String status,
                                  @RequestParam(name = "id_user") long id){
        User user = userService.getUserById(id).get();
        List<String> enumNames = Stream.of(Status.values())
                .map(Status::name)
                .collect(Collectors.toList());
        if(enumNames.contains(status)){
            user.setStatus(Status.valueOf(status));
            userService.saveUser(user);
        }
        return "redirect:/api/v1/admin/" + id + "/edit";
    }
}
