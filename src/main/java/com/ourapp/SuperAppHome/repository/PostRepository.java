package com.ourapp.SuperAppHome.repository;

import com.ourapp.SuperAppHome.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
    Post findByTitle(String title);
}
