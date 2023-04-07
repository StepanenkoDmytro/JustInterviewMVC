package com.ourapp.SuperAppHome.model.interview;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "source_for_question")
@Data
public class SourceForQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "source_id")
    private Long id;

    @Column(name = "source_link")
    private String link;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name = "question_id")
    private Question question;
}
