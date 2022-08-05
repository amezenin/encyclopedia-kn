package com.knits.product.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "article")
@Data
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title", length = 254)
    private String title;

    @Column(name = "content", length = 1200)
    private String content;

    @Column(name = "created_date")
    private Date createdDate = new Date();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "article",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @ManyToMany
    //@ToString.Exclude
    @JoinTable(
            name = "article_like",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    List<User> likes = new ArrayList<>();

    //private Integer likeCount;

    public void addLikes(User user) {
        likes.add(user);
    }

    public void removeLikes(User user) {
        likes.remove(user);
    }

    public void addComment(Comment comment) {
        commentList.add(comment);
        comment.setArticle(this);
    }

    public void removeComment(Comment comment) {
        commentList.remove(comment);
        comment.setArticle(null);
    }



}
