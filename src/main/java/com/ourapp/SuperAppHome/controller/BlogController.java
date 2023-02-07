package com.ourapp.SuperAppHome.controller;

import com.ourapp.SuperAppHome.model.Post;
import com.ourapp.SuperAppHome.model.Status;
import com.ourapp.SuperAppHome.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class BlogController {

    private final PostService postService;

    @Autowired
    public BlogController(PostService postService) {
        this.postService = postService;
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
    public String savePost(@RequestParam String name,
                           @RequestParam String anons,
                           @RequestParam String full_text,
                           Model model){
        Post post = new Post(name,anons,full_text);
        post.setStatus(Status.ACTIVE);
        postService.savePost(post);
        return "redirect:/api/v1/blog";
    }

    @GetMapping("/blog/{id}")
    public String getPost(@PathVariable(value = "id") long id, Model model){
        if(!postService.existsPostById(id)){
            return "redirect:/api/v1/blog";//переписати на свою 404 сторінку
        }
        Post post = postService.getPost(id);
        model.addAttribute("post",post);
        return "blog-details";
    }
    @GetMapping("/blog/{id}/edit")
    public String getEditPost(@PathVariable(value = "id") long id, Model model){
        if(!postService.existsPostById(id)){
            return "redirect:/api/v1/blog";//переписати на свою 404 сторінку
        }
        Post post = postService.getPost(id);
        model.addAttribute("post",post);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String updatePost(@PathVariable(name = "id") long id,
//                             @RequestParam String title,
                             @RequestParam String anons,
                             @RequestParam String full_text,
                             Model model){
        Post post = postService.getPost(id);
//        post.setTitle(title);
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
}