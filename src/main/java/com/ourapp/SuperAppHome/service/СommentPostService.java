package com.ourapp.SuperAppHome.service;

import com.ourapp.SuperAppHome.model.СommentToPost;

import java.util.List;

public interface СommentPostService {
    List<СommentToPost> getAllComments();

    void saveСomment(СommentToPost comment);
    СommentToPost getСommentById(long id);
    void deleteСomment(long id);
}
