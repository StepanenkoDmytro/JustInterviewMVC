package com.ourapp.SuperAppHome.controller;

import com.ourapp.SuperAppHome.exception.RoleNotFoundException;
import com.ourapp.SuperAppHome.model.user.Role;
import com.ourapp.SuperAppHome.model.user.Status;
import com.ourapp.SuperAppHome.model.user.User;
import com.ourapp.SuperAppHome.repository.RoleRepository;
import com.ourapp.SuperAppHome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/api/v1")
public class AdminController {
    private UserService userService;
    private RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin-main";
    }

    @GetMapping("/admin/{id}/edit")
    public String adminEditPage(@PathVariable(name = "id") long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);

        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "admin-user-edit";
    }

    @PostMapping("/admin/{id}/remove")
    public String removeUser(@PathVariable(name = "id") long id, Model model) {
        userService.deleteUser(id);
        return "redirect:/api/v1/admin";
    }

    @PostMapping("/admin/edit/setrole")
    public String adminSetRole(@RequestParam(name = "id_user") long idUser,
                               @RequestParam(name = "roleID") long roleId,
                               Model model) {
        User user = userService.getUserById(idUser);
        Role role = roleRepository.findById(roleId).orElseThrow(
                () -> new RoleNotFoundException(String.format("Role with id = %d not found", roleId)));

        user.addRoleToUser(role);
        userService.saveUser(user);

        return String.format("redirect:/api/v1/admin/%d/edit", idUser);
    }

    @PostMapping("/admin/edit/removerole")
    public String adminRemoveRole(@RequestParam(name = "id_user") long id,
                                  @RequestParam(name = "id_role") long roleId) {

        User user = userService.getUserById(id);
        Role role = roleRepository.findById(roleId).orElseThrow(
                () -> new RoleNotFoundException(String.format("Role with id = %d not found", roleId)));

        user.removeRoleFromUser(role);
        userService.saveUser(user);
        return String.format("redirect:/api/v1/admin/%d/edit", id);
    }

    @PostMapping("/admin/edit/status")
    public String adminEditStatus(@RequestParam(name = "status") String status,
                                  @RequestParam(name = "id_user") long id) {
        User user = userService.getUserById(id);
        List<String> enumNames = Stream.of(Status.values())
                .map(Status::name)
                .collect(Collectors.toList());
        if (enumNames.contains(status)) {
            user.setStatus(Status.valueOf(status));
            userService.saveUser(user);
        }
        return String.format("redirect:/api/v1/admin/%d/edit", id);
    }
}
