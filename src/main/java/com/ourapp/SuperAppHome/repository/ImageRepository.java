package com.ourapp.SuperAppHome.repository;

import com.ourapp.SuperAppHome.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
