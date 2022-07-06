package com.knits.product.repository;

import com.knits.product.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@EntityScan(basePackages = {"com.knits.product.model"})
@EnableJpaRepositories(basePackages = {"com.knits.product.repository"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create",
        "spring.datasource.url=jdbc:mysql://localhost:3306/encyclopedia"
})
@Slf4j
//help to clean and fill the DB before each test
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ModelTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private VoteArticleRepository voteArticleRepository;

    @Autowired
    private VoteCommentRepository voteCommentRepository;

    @Test
    @Rollback(value = false)
    public void saveDataIntoEachTableWithRelationships(){

        User user = new User();
        user.setLogin("anmezhenin");
        user.setPassword("password");
        user.setFirstName("Anton");
        user.setLastName("Mezhenin");
        user.setEmail("anton.mezhenin@gmail.com");

        Article article = new Article();
        article.setTitle("First article");
        article.setContent("Body");
        article.setUser(user);

        Comment comment = new Comment();
        comment.setContent("Good article!");
        comment.setUser(user);
        comment.setArticle(article);

        VoteArticle voteArticle = new VoteArticle();
        voteArticle.setStatus(1);

        VoteComment voteComment = new VoteComment();
        voteComment.setStatus(-1);

        user.addArticle(article);
        user.addComment(comment);
        user.addVoteArticle(voteArticle);
        article.addVoteArticle(voteArticle);
        article.addComment(comment);
        user.addVoteComment(voteComment);
        comment.addVoteComment(voteComment);

        userRepository.save(user);
        articleRepository.save(article);
        commentRepository.save(comment);
        voteArticleRepository.save(voteArticle);
        voteCommentRepository.save(voteComment);

        Article savedArticle = articleRepository.getById(52L);

        log.info("Article description: {} ",savedArticle.getTitle());
        savedArticle.getCommentList().forEach(bl
                -> log.info("Comments Found: {} ",bl.getContent()));
        savedArticle.getCommentList().forEach(bl
                -> log.info("Comment like: {} ",bl.getVoteCommentList().get(0).getStatus()));

    }

}

