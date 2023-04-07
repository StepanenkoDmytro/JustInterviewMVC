package com.ourapp.SuperAppHome.model.interview;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;
    @Column(name = "question_name")
    private String question;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name = "section_id")
    private Section section;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.REFRESH,CascadeType.MERGE},
            mappedBy = "question")
    private List<SourceForQuestion> links;

    public Question() {
    }

    public Question(String question, Section section) {
        this.question = question;
        this.section = section;
    }
    public void addSourceToQuestion(SourceForQuestion source){
        if(links == null){
            links = new ArrayList<>();
        }
        links.add(source);
        source.setQuestion(this);
    }
}
