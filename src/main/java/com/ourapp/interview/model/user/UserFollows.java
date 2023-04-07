package com.ourapp.SuperAppHome.model.user;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_followers")
@Data
public class UserFollows {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "distributor_id")
    private Long distributor;


    @Column(name = "subscriber_id")
    private Long subscriber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public UserFollows() {
    }

    public UserFollows(Long distributor, Long subscriber, Status status) {
        this.distributor = distributor;
        this.subscriber = subscriber;
        this.status = status;
    }

}
