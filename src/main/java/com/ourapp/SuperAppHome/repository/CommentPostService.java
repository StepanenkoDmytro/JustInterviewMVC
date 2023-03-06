package com.ourapp.SuperAppHome.repository;

import com.ourapp.SuperAppHome.model.СommentToPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentPostService extends JpaRepository<СommentToPost,Long> {
}
