package com.ourapp.SuperAppHome.repository.interview;

import com.ourapp.SuperAppHome.model.interview.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic,Long> {
}
