package com.ourapp.SuperAppHome.controller;

import com.ourapp.SuperAppHome.model.Status;
import com.ourapp.SuperAppHome.model.UserFollows;
import com.ourapp.SuperAppHome.model.User;
import com.ourapp.SuperAppHome.repository.UserFollowsRepository;
import com.ourapp.SuperAppHome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserFollowsRepository userFollowsRepository;

    @Autowired
    public UserController(UserService userService, UserFollowsRepository userFollowsRepository) {
        this.userService = userService;
        this.userFollowsRepository = userFollowsRepository;
    }

    @GetMapping("")
    public String getProfileUserPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.getUserByEmail(userDetails.getUsername()).get();
        List<UserFollows> followsList = userFollowsRepository.findAllByDistributor(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("followList",getFollowList(followsList));
        model.addAttribute("askFollowList",getAskFollowList(followsList));
        return "user-main";
    }

    @GetMapping("/{id}")
    public String getUserPage(@AuthenticationPrincipal UserDetails userDetails,
                              @PathVariable(name = "id") long id,
                              Model model) {
        User user = userService.getUserById(id).get();
        User observer = userService.getUserByEmail(userDetails.getUsername()).get();
        if(user.equals(observer)){
            return "redirect:/api/v1/user";
        }
        model.addAttribute("user", user);
        model.addAttribute("observer", observer);
        return "user-get";
    }

    @GetMapping("/{id}/edit")
    public String userEditPage(@PathVariable(name = "id") long id, Model model) {
        User user = userService.getUserById(id).get();
        model.addAttribute("user", user);
        return "user-edit";
    }

    @PostMapping("/{id}/edit")
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

    @PostMapping("/follow")
    public String addFriend(@RequestParam(name = "id_observer") long id_observer,
                            @RequestParam(name = "id") long id) {
        User user = userService.getUserById(id).get();
        User observer = userService.getUserById(id_observer).get();
        UserFollows userFollows = new UserFollows(user.getId(), observer.getId(), Status.NOT_ACTIVE);
        userFollowsRepository.save(userFollows);
        return "redirect:/api/v1/user/" + id;
    }

    @PostMapping("/follow/{id}/no")
    public String followAnswerNo(@PathVariable("id") long id,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername()).get();
        User observer = userService.getUserById(id).get();
        List<UserFollows> userFollows = userFollowsRepository.findAllByDistributor(user.getId());
        for(UserFollows u : userFollows){
            if(u.getSubscriber().equals(observer.getId())) {
                u.setStatus(Status.DELETED);
                userFollowsRepository.save(u);
            }
        }
        return "redirect:/api/v1/user/";
    }

    @PostMapping("/follow/{id}/yes")
    public String followAnswerYes(@PathVariable("id") long id,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername()).get();
        User observer = userService.getUserById(id).get();
        List<UserFollows> userFollows = userFollowsRepository.findAllByDistributor(user.getId());
        for(UserFollows u : userFollows){
            if(u.getSubscriber().equals(observer.getId())) {
                u.setStatus(Status.ACTIVE);
                userFollowsRepository.save(u);
            }
        }
        return "redirect:/api/v1/user/";
    }

    public List<User> getAskFollowList(List<UserFollows> followsList){
        List<User> askFollowList = new ArrayList<>();
        for(UserFollows u : followsList){
            if(u.getStatus() == Status.NOT_ACTIVE)
                askFollowList.add(userService.getUserById(u.getSubscriber()).get());
        }
        return askFollowList;
    }

    public List<User> getFollowList(List<UserFollows> followsList){
        List<User> followList = new ArrayList<>();
        for(UserFollows u : followsList){
            if(u.getStatus() == Status.ACTIVE)
                followList.add(userService.getUserById(u.getSubscriber()).get());
        }
        return followList;
    }
}
