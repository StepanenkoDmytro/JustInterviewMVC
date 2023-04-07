package com.ourapp.interview.repository;

import com.ourapp.interview.model.blog_part.СommentToPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentPostService extends JpaRepository<СommentToPost,Long> {
}
