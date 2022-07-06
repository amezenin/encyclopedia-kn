package com.knits.product.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A user.
 */
@Entity
@Table(name = "user")
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(length = 254, unique = true)
    private String email;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "user")
    private List<Article> articleList= new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList= new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<VoteArticle> voteArticleList= new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<VoteComment> voteCommentList= new ArrayList<>();

    public void addArticle(Article article) {
        articleList.add(article);
        article.setUser(this);
    }

    public void removeArticle(Article article) {
        articleList.remove(article);
        article.setUser(null);
    }

    public void addComment(Comment comment) {
        commentList.add(comment);
        comment.setUser(this);
    }

    public void removeComment(Comment comment) {
        commentList.remove(comment);
        comment.setUser(null);
    }

    public void addVoteArticle(VoteArticle voteArticle) {
        voteArticleList.add(voteArticle);
        voteArticle.setUser(this);
    }

    public void removeVoteArticle(VoteArticle voteArticle) {
        voteArticleList.remove(voteArticle);
        voteArticle.setUser(null);
    }

    public void addVoteComment(VoteComment voteComment) {
        voteCommentList.add(voteComment);
        voteComment.setUser(this);
    }

    public void removeVoteComment(VoteComment voteComment) {
        voteCommentList.remove(voteComment);
        voteComment.setUser(null);
    }

}
