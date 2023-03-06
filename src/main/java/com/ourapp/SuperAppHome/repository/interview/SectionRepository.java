package com.ourapp.SuperAppHome.repository.interview;

import com.ourapp.SuperAppHome.model.interview.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section,Long> {

    Section findBySectionName(String sectionName);
}
