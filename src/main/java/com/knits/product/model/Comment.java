package com.knits.product.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoteComment> voteCommentList = new ArrayList<>();

    public void addVoteComment(VoteComment voteComment) {
        voteCommentList.add(voteComment);
        voteComment.setComment(this);
    }

    public void removeVoteComment(VoteComment voteComment) {
        voteCommentList.remove(voteComment);
        voteComment.setComment(null);
    }
}
