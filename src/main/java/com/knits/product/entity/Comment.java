package com.knits.product.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "comment")
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "created_date")
    private Date createdDate = new Date();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToMany(mappedBy = "likedComments")
    List<User> likes;

    public void addLikes(User user) {
        likes.add(user);
    }

    public void removeLikes(User user) {
        likes.remove(user);
    }

}
