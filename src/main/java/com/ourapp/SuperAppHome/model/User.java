package com.ourapp.SuperAppHome.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "users")
@Data
public class User extends BaseEntity {

    @Column(name = "username")
    @NotEmpty(message = "User's name cannot be empty.")
    @Size(min = 3, max = 100,
            message = "length should be between 3 to 100")
    private String username;

    @Column(name = "first_name")
    @NotBlank(message = "User's first name cannot be empty.")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "User's last name cannot be empty.")
    private String lastName;

    @Column(name = "email")
    @NotBlank(message = "User's email cannot be empty.")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "User's password cannot be empty.")
    @Size(min = 7, message = "Password should be min 7 symbols")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE},
            mappedBy = "author")
    private List<Post> posts;

    public void addPostToUser(Post post) {
        if (posts == null) {
            posts = new ArrayList<>();
        }
        posts.add(post);
        post.setAuthor(this);
    }
}
