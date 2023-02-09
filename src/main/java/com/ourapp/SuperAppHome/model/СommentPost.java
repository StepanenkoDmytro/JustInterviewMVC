package com.ourapp.SuperAppHome.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "comments")
@Data
public class СommentPost extends BaseEntity{

    @Column(name = "comment")
    @NotBlank(message = "Comment can not be empty")
    @Size(max = 200)
    private String comment;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "post_id")
    private Post post;

    public СommentPost() {
    }

    public СommentPost(String comment) {
        this.comment = comment;
    }
}
