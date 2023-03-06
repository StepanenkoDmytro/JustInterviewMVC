package com.ourapp.SuperAppHome.service.impl;

import com.ourapp.SuperAppHome.model.СommentToPost;
import com.ourapp.SuperAppHome.repository.CommentPostService;
import com.ourapp.SuperAppHome.service.СommentPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class СommentPostServiceImpl implements СommentPostService {

    private final CommentPostService commentPostRepository;

    @Autowired
    public СommentPostServiceImpl(CommentPostService commentPostRepository) {
        this.commentPostRepository = commentPostRepository;
    }

    @Override
    public List<СommentToPost> getAllComments() {
        return commentPostRepository.findAll();
    }

    @Override
    public void saveСomment(СommentToPost comment) {
        commentPostRepository.save(comment);
    }

    @Override
    public СommentToPost getСommentById(long id) {
        return commentPostRepository.findById(id).get();
    }

    @Override
    public void deleteСomment(long id) {
        commentPostRepository.deleteById(id);
    }
}
