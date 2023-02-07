package com.ourapp.SuperAppHome.service;

import com.ourapp.SuperAppHome.model.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();

    void savePost(Post post);
    Post getPost(long id);
    void deletePost(long id);
    boolean existsPostById(long id);
}
