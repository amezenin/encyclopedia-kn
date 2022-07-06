package com.knits.product.model;

import lombok.Data;

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

    @Column(name = "content")
    private String content;

    @Column(name = "created_date")
    private Date createdDate = new Date();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoteArticle> voteArticleList = new ArrayList<>();

    public void addComment(Comment comment) {
        commentList.add(comment);
        comment.setArticle(this);
    }

    public void removeComment(Comment comment) {
        commentList.remove(comment);
        comment.setArticle(null);
    }

    public void addVoteArticle(VoteArticle voteArticle) {
        voteArticleList.add(voteArticle);
        voteArticle.setArticle(this);
    }

    public void removeVoteArticle(VoteArticle voteArticle) {
        voteArticleList.remove(voteArticle);
        voteArticle.setArticle(null);
    }

}
