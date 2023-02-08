package com.ourapp.SuperAppHome.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "posts")
@Data
public class Post extends BaseEntity{
    @Column(name = "title")
    @NotBlank(message = "Post's title cannot be empty.")
    private String title;
    @Column(name = "anons")
    private String anons;
    @Column(name = "full_text")
    @NotBlank(message = "Post's text cannot be empty.")
    private String fullText;


    public Post() {
    }

    public Post(String title, String anons, String fullText) {
        this.title = title;
        this.anons = anons;
        this.fullText = fullText;
    }
}
