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
        user.setLogin("admin");
        user.setPassword("$2a$12$mwKpnPivhXCmq4bNVdfYFOO3novitFed9aUpBFDRaeoDcXpQYhw7O");
        user.setFirstName("Anton");
        user.setLastName("Mezhenin");
        user.setActive(true);
        user.setEmail("anton.mezhenin@gmail.com");


        User user2 = new User();
        user2.setLogin("user");
        user2.setPassword("$2a$12$c29Km1PFfaPc1gwIpGyLqe5fRlKdX8STl2MhecYF9TwsklqAqmBVu");
        user2.setFirstName("Jonny");
        user2.setLastName("Arcan");
        user2.setActive(true);
        user2.setEmail("user@gmail.com");


        User bannedUser = new User();
        bannedUser.setLogin("banned");
        bannedUser.setPassword("$2a$12$c29Km1PFfaPc1gwIpGyLqe5fRlKdX8STl2MhecYF9TwsklqAqmBVu");
        bannedUser.setFirstName("Jonny");
        bannedUser.setLastName("Arcan");
        bannedUser.setActive(false);
        bannedUser.setEmail("banned@gmail.com");


        Role role = new Role();
        role.setName("ROLE_ADMIN");

        Role role2 = new Role();
        role2.setName("ROLE_USER");

        //articles
        Article article = new Article();
        article.setTitle("Siberian sturgeon");
        article.setContent("The Siberian sturgeon (Acipenser baerii) is a species of sturgeon in the family Acipenseridae.");
        article.setUser(user);

        Article article2 = new Article();
        article2.setTitle("Wels catfish");
        article2.setContent("The wels catfish (Silurus glanis), also called sheatfish or just wels, is a large species " +
                "of catfish native to wide areas of central, southern, and eastern Europe, in the basins of the Baltic," +
                " Black and Caspian Seas.");
        article2.setUser(user2);

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
        user.addRole(role2);
        user2.addRole(role2);
        bannedUser.addRole(role2);
        user.addArticle(article);
        user2.addArticle(article2);
        user.addComment(comment);
        user.addComment(comment2);
        user.addComment(comment3);
        user.addlikedArticle(article);
        user.addlikedArticle(article2);
        user.addlikedComment(comment);
        user.addlikedComment(comment2);
        user2.addlikedArticle(article);
        user2.addlikedArticle(article2);
        user2.addlikedComment(comment);
        user2.addlikedComment(comment2);
        article.addComment(comment);
        article.addComment(comment2);
        article2.addComment(comment3);


        userRepository.save(user);
        userRepository.save(user2);
        userRepository.save(bannedUser);
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

