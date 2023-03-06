package com.ourapp.SuperAppHome.repository.interview;

import com.ourapp.SuperAppHome.model.interview.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
