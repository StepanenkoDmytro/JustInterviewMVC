package com.ourapp.SuperAppHome.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "posts")
@Data
public class Post extends BaseEntity{
    @Column(name = "title")
    private String title;
    @Column(name = "anons")
    private String anons;
    @Column(name = "full_text")
    private String fullText;

    public Post() {
    }

    public Post(String title, String anons, String fullText) {
        this.title = title;
        this.anons = anons;
        this.fullText = fullText;
    }
}
