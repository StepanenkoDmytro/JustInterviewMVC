package com.ourapp.SuperAppHome.model.interview;



import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topics")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Long id;
    @Column(name = "topic_name")
    private String name;
    @Column(name = "topic_desc")
    private String description;


    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.REFRESH,CascadeType.MERGE},
            mappedBy = "topic")
    private List<Section> sections;


    public Topic() {
    }

    public Topic(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getCountQuestionInTopic(){
        int result = 0;
        for(Section section : this.getSections()){
            result += section.getQuestions().size();
        }
        return result;
    }

    public void addSectionToTopic(Section section){
        if(sections == null){
            sections = new ArrayList<>();
        }
        sections.add(section);
        section.setTopic(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}