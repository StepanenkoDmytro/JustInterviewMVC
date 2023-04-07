package com.ourapp.interview.repository;

import com.ourapp.interview.model.blog_part.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByTitle(String title);

    //    @Query("select t from Test t join User u where u.username = :username")
//    @Query("select p.title\n" +
//            "from ourapp.posts p join ourapp.posts_tags pt\n" +
//            "on p.id = pt.post_id\n" +
//            "join ourapp.tags t\n" +
//            "on pt.tags_id = t.id\n" +
//            "where t.tag_name = ?1")
    @Query(value = "select p.id\n" +
            "from ourapp.posts p join ourapp.posts_tags pt\n" +
            "on p.id = pt.post_id\n" +
            "join ourapp.tags t\n" +
            "on pt.tags_id = t.id\n" +
            "where t.tag_name = :tagname",
            nativeQuery = true)
    List<Long> findAllPostsByTagNameL(@Param("tagname") String tagname);

    @Query(value = "select p.title\n" +
            "from ourapp.posts p join ourapp.posts_tags pt\n" +
            "on p.id = pt.post_id\n" +
            "join ourapp.tags t\n" +
            "on pt.tags_id = t.id\n" +
            "where t.tag_name = :tagname",
            nativeQuery = true)
    List<String> findAllPostsByTagNameS(@Param("tagname") String tagname);
}
