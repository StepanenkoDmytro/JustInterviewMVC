package com.ourapp.SuperAppHome.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.REFRESH,CascadeType.MERGE},
            mappedBy = "post")
    private List<СommentToPost> comments;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "author_id")
    private User author;

    public Post() {
    }

    public Post(String title, String anons, String fullText) {
        this.title = title;
        this.anons = anons;
        this.fullText = fullText;
    }

    public void addCommentToPost(СommentToPost comment){
        if(comments == null){
            comments = new ArrayList<>();
        }
        comments.add(comment);
        comment.setPost(this);
    }

//    public void addImageToPost(Image image) {
//        image.setPost(this);
//        images.add(image);
//    }
}
