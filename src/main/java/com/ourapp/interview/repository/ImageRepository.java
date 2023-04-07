package com.ourapp.interview.repository;

import com.ourapp.interview.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
