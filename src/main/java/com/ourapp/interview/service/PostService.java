package com.ourapp.SuperAppHome.service;

import com.ourapp.SuperAppHome.model.blog_part.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();

    void savePost(Post post);
    Post getPost(Long id);
    void deletePost(long id);
    boolean existsPostById(long id);
}
