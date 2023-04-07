package com.ourapp.interview.repository.interview;

import com.ourapp.interview.model.interview.Subtopic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Subtopic,Long> {

    Subtopic findBySectionName(String sectionName);
}
