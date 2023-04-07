package com.ourapp.interview.model.blog_part;

import com.ourapp.interview.model.BaseEntity;
import com.ourapp.interview.model.user.Status;
import com.ourapp.interview.model.user.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Entity
@Table(name = "posts")
@Data
public class Post extends BaseEntity {
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

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "posts_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id"))
    private List<Tag> tags;

    public Post() {
    }

    public Post(String title, String anons, String fullText) {
        this.title = title;
        this.anons = anons;
        this.fullText = fullText;
        this.setStatus(Status.ACTIVE);
    }

//    public void addTagToPost(String tag){
//        if(tags == null){
//            tags = new ArrayList<>();
//        }
//        String[] arrayTags = tag.split(" ");
//        for (String newTag : arrayTags) {
//            Tag tag1 = new Tag();
//            tag1.setName("#" + newTag);
//            tags.add(tag1);
//        }
//    }
public void addTagToPost(Tag tag){
    if(tags == null){
        tags = new ArrayList<>();
    }
        tags.add(tag);
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
