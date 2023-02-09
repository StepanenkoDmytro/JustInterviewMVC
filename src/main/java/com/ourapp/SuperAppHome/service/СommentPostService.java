package com.ourapp.SuperAppHome.service;

import com.ourapp.SuperAppHome.model.СommentPost;

import java.util.List;

public interface СommentPostService {
    List<СommentPost> getAllComments();

    void saveСomment(СommentPost comment);
    СommentPost getСommentById(long id);
    void deleteСomment(long id);
}
