package com.ourapp.SuperAppHome.repository;

import com.ourapp.SuperAppHome.model.blog_part.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long> {
    Optional<Tag> findByName(String name);
}
