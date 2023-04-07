package com.ourapp.SuperAppHome.repository;

import com.ourapp.SuperAppHome.model.user.UserFollows;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UserFollowsRepository extends JpaRepository<UserFollows, Long> {
    List<UserFollows> findAllByDistributor(Long distributor);
    Set<Long> findBySubscriber(Long subscriber);
}
