package com.ourapp.SuperAppHome.repository;

import com.ourapp.SuperAppHome.model.СommentPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentPostService extends JpaRepository<СommentPost,Long> {
}
