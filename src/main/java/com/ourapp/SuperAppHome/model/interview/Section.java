package com.ourapp.SuperAppHome.model.interview;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sections")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private Long id;
    @Column(name = "section_name")
    private String sectionName;
    @Column(name = "section_desc")
    private String sectionDesc;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name = "topic_fk_id")
    private Topic topic;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.REFRESH,CascadeType.MERGE},
            mappedBy = "section")
    private List<Question> questions;

    public Section() {
    }

    public Section(String sectionName, String sectionDesc, Topic topic) {
        this.sectionName = sectionName;
        this.sectionDesc = sectionDesc;
        this.topic = topic;
    }

    public void addQuestionToSection(Question question){
        if(questions == null){
            questions = new ArrayList<>();
        }
        questions.add(question);
        question.setSection(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionDesc() {
        return sectionDesc;
    }

    public void setSectionDesc(String sectionDesc) {
        this.sectionDesc = sectionDesc;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Section{" +
                "sectionName='" + sectionName + '\'' +
                ", sectionDesc='" + sectionDesc + '\'' +
                ", topic=" + topic +
                '}';
    }
}
