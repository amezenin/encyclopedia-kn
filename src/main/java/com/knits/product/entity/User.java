package com.knits.product.entity;

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
    private List<Article> articleList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "comment_like",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private List<Comment> likedComments = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "article_like",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id"))
    private List<Article> likedArticles = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roleList = new ArrayList<>();

    public void addlikedArticle(Article article) {
        likedArticles.add(article);
    }

    public void removelikedArticle(Article article) {
        likedArticles.remove(article);
    }

    public void addlikedComment(Comment comment) {
        likedComments.add(comment);
    }

    public void removelikedComment(Comment comment) { likedComments.remove(comment); }

    public void addRole(Role role) { roleList.add(role); }

    public void removeRole(Role role) { roleList.remove(role); }

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

}
