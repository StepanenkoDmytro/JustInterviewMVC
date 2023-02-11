package com.ourapp.SuperAppHome.controller;

import com.ourapp.SuperAppHome.model.*;
import com.ourapp.SuperAppHome.repository.CommentPostService;
import com.ourapp.SuperAppHome.repository.PostRepository;
import com.ourapp.SuperAppHome.repository.UserFollowsRepository;
import com.ourapp.SuperAppHome.service.PostService;
import com.ourapp.SuperAppHome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1")
public class BlogController {

    private final PostService postService;
    private final CommentPostService commentPostService;
    private final UserService userService;
    private final UserFollowsRepository userFollowsRepository;
    private final PostRepository postRepository;

    @Autowired
    public BlogController(PostService postService, CommentPostService commentPostService, UserService userService,UserFollowsRepository userFollowsRepository,
                          PostRepository postRepository) {
        this.postService = postService;
        this.commentPostService = commentPostService;
        this.userService = userService;
        this.userFollowsRepository = userFollowsRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/blog")
    public String main(Model model){
        List<Post> allPosts = postService.getAllPosts();
        model.addAttribute("allPosts", allPosts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String addNewPost(Model model){
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String savePost(@AuthenticationPrincipal UserDetails userDetails,
                           @RequestParam String name,
                           @RequestParam String anons,
                           @RequestParam String full_text,
                           Model model){
        User user = userService.getUserByEmail(userDetails.getUsername()).get();
        Post post = new Post(name,anons,full_text);
        post.setAuthor(user);
        post.setStatus(Status.ACTIVE);
        postService.savePost(post);
        return "redirect:/api/v1/blog";
    }

    @GetMapping("/blog/{id}")
    public String getPost(@AuthenticationPrincipal UserDetails userDetails,
                          @PathVariable(value = "id") long id, Model model){
        if(!postService.existsPostById(id)){
            return "redirect:/api/v1/blog";//переписати на свою 404 сторінку
        }
        Post post = postService.getPost(id);
        User observer = userService.getUserByEmail(userDetails.getUsername()).get();

        Set<Long> postPermission = userFollowsRepository.findAllByDistributor(post.getId())
                .stream().map(UserFollows::getSubscriber).collect(Collectors.toSet());
        postPermission.add(post.getAuthor().getId());
        if(!postPermission.contains(observer.getId())) {
            System.out.println("You don't have permissions");
            return "redirect:/api/v1/blog";
        }
        List<СommentPost> comments = post.getComments();
        int size = comments.size();
        model.addAttribute("post",post);
        model.addAttribute("comments",comments);
        model.addAttribute("sizeList",size);
        return "blog-details";
    }
    @GetMapping("/blog/{id}/edit")
    public String getEditPost(@AuthenticationPrincipal UserDetails userDetails,
                              @PathVariable(value = "id") long id, Model model){
        if(!postService.existsPostById(id)){
            return "redirect:/api/v1/blog";//переписати на свою 404 сторінку
        }
        Post post = postService.getPost(id);
        User user = post.getAuthor();
        if(!userDetails.getUsername().equals(user.getEmail())){
            System.out.println("You are not author of this post");
            return "redirect:/api/v1/blog";
        }
        model.addAttribute("post",post);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String updatePost(@PathVariable(name = "id") long id,
                             @RequestParam String title,
                             @RequestParam String anons,
                             @RequestParam String full_text,
                             Model model){

        Post post = postService.getPost(id);
        post.setTitle(title);
        post.setAnons(anons);
        post.setFullText(full_text);
        postService.savePost(post);
        return "redirect:/api/v1/blog";
    }
    @PostMapping("/blog/{id}/remove")
    public String removePost(@PathVariable(name = "id") long id, Model model){
        postService.deletePost(id);
        return "redirect:/api/v1/blog";
    }

    @PostMapping("/blog/addcomment")
    public String postAddComment(@AuthenticationPrincipal UserDetails userDetails,
                                 @RequestParam(name = "id_post") long id,
                                 @RequestParam String comment,
                                 Model model){
        User user = userService.getUserByEmail(userDetails.getUsername()).get();
        Post post = postService.getPost(id);
        СommentPost commentPost = new СommentPost(comment);
        commentPost.setStatus(Status.ACTIVE);
        commentPost.setAuthor(user);
        post.addCommentToPost(commentPost);
        postService.savePost(post);
        return "redirect:/api/v1/blog/" + id;
    }
    @PostMapping("/blog/delcomment")
    public String postDelComment(@RequestParam(name = "comment_id") long id,
                                 @RequestParam(name = "post_id") long id_post,
                                 Model model){
        commentPostService.deleteById(id);
        return "redirect:/api/v1/blog/" + id_post;
    }
}