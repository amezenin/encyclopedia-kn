package com.knits.product.repository;

import com.knits.product.entity.*;
import lombok.extern.slf4j.Slf4j;
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
@EntityScan(basePackages = {"com.knits.product.entity"})
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
    private RoleRepository roleRepository;

    @Test
    @Rollback(value = false)
    public void saveDataIntoEachTableWithRelationships(){

        User user = new User();
        user.setLogin("anmezhenin");
        user.setPassword("password");
        user.setFirstName("Anton");
        user.setLastName("Mezhenin");
        user.setEmail("anton.mezhenin@gmail.com");

        User user2 = new User();
        user2.setLogin("user");
        user2.setPassword("user");
        user2.setFirstName("Jonny");
        user2.setLastName("Arcan");
        user2.setEmail("user@gmail.com");

        Role role = new Role();
        role.setRole("admin");

        Role role2 = new Role();
        role2.setRole("user");

        //articles
        Article article = new Article();
        article.setTitle("First article");
        article.setContent("Body");
        article.setUser(user);

        Article article2 = new Article();
        article2.setTitle("Second article");
        article2.setContent("Body");
        article2.setUser(user);

        //comments
        Comment comment = new Comment();
        comment.setContent("Good article!");
        comment.setUser(user);
        comment.setArticle(article);

        Comment comment2 = new Comment();
        comment2.setContent("Good article2!");
        comment2.setUser(user);
        comment2.setArticle(article);

        Comment comment3 = new Comment();
        comment3.setContent("Good article3!");
        comment3.setUser(user);
        comment3.setArticle(article2);


        user.addRole(role);
        user2.addRole(role2);
        user.addArticle(article);
        user.addArticle(article2);
        user.addComment(comment);
        user.addComment(comment2);
        user.addComment(comment3);
        user.addlikedArticle(article);
        user.addlikedArticle(article2);
        user.removelikedArticle(article2);
        user.addlikedComment(comment);
        user.addlikedComment(comment2);
        article.addComment(comment);
        article.addComment(comment2);
        article2.addComment(comment3);

        //user.addVoteComment(voteComment);
        //comment.addVoteComment(voteComment);


        userRepository.save(user);
        userRepository.save(user2);
        roleRepository.save(role);
        roleRepository.save(role2);
        articleRepository.save(article);
        articleRepository.save(article2);
        commentRepository.save(comment);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
        //voteArticleRepository.save(voteArticle);
        //voteCommentRepository.save(voteComment);

        Article savedArticle = articleRepository.getById(102L);

        log.info("Article description: {} ",savedArticle.getTitle());
        savedArticle.getCommentList().forEach(bl
                -> log.info("Comments Found: {} ",bl.getContent()));

    }

}

