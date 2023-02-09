package com.ourapp.SuperAppHome.controller;

import com.ourapp.SuperAppHome.model.Post;
import com.ourapp.SuperAppHome.model.Status;
import com.ourapp.SuperAppHome.model.User;
import com.ourapp.SuperAppHome.model.СommentPost;
import com.ourapp.SuperAppHome.repository.CommentPostService;
import com.ourapp.SuperAppHome.service.PostService;
import com.ourapp.SuperAppHome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class BlogController {

    private final PostService postService;
    private final CommentPostService commentPostService;
    private final UserService userService;

    @Autowired
    public BlogController(PostService postService, CommentPostService commentPostService, UserService userService) {
        this.postService = postService;
        this.commentPostService = commentPostService;
        this.userService = userService;
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
    public String getPost(@PathVariable(value = "id") long id, Model model){
        if(!postService.existsPostById(id)){
            return "redirect:/api/v1/blog";//переписати на свою 404 сторінку
        }
        Post post = postService.getPost(id);
        List<СommentPost> comments = post.getComments();
        int size = comments.size();
        model.addAttribute("post",post);
        model.addAttribute("comments",comments);
        model.addAttribute("sizeList",size);
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