package com.knits.product.controller;

import com.knits.product.exceptions.UserException;
import com.knits.product.service.ArticleService;
import com.knits.product.service.UserService;
import com.knits.product.service.dto.ArticleDTO;
import com.knits.product.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping(value = "/articles/all", produces = {"application/json"})

    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        log.debug("REST request to get all Articles");
        return ResponseEntity
                .ok()
                .body(articleService.findAll());
    }

    @PostMapping(value = "/users/{userId}/articles", produces = {"application/json"}, consumes = { "application/json"})
    public ResponseEntity<ArticleDTO> createArticle(@PathVariable(value = "userId") long userId,
                                                 @RequestBody ArticleDTO articleDTO) {
        log.debug("REST request to createUser Article ");
        if (articleDTO == null) {
            throw new UserException("Article data are missing");
        }
        return ResponseEntity
                .ok()
                .body(articleService.save(userId, articleDTO));
    }

    @GetMapping("/users/{userId}/articles")
    public List<ArticleDTO> getArticlesByUserId(@PathVariable(value = "userId") Long userId){
        return articleService.getArticlesByUserId(userId);
    }

    @GetMapping(value = "/articles/{id}", produces = {"application/json"})
    public ResponseEntity<ArticleDTO> getArticleById( @PathVariable(value = "id", required = true) final Long id) {

        log.debug("REST request to get Article : {}", id);
        ArticleDTO articleFound = articleService.findById(id);
        return ResponseEntity
                .ok()
                .body(articleFound);
    }



    @GetMapping(value = "users/{userId}/articles/{id}", produces = {"application/json"})
    public ResponseEntity<ArticleDTO> getArticleByIdAndUserId(@PathVariable(value = "userId", required = true) final Long userId,
                                                     @PathVariable(value = "id") Long articleId) {

        log.debug("REST request to get Article : {}", articleId);
        ArticleDTO articleDTO = articleService.findByIdAndUserId(userId, articleId);
        return ResponseEntity
                .ok()
                .body(articleDTO);
    }

    @PutMapping(value = "/users/{userId}/articles/{articleId}", produces = {"application/json"}, consumes = { "application/json"})
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable(value = "userId") Long userId,
                                                    @PathVariable(value = "articleId") Long articleId,
                                                    @RequestBody ArticleDTO articleDTO) {
        log.debug("REST request to updateUser User ");
        if (articleDTO == null) {
            throw new UserException("Article data are missing");
        }
        return ResponseEntity
                .ok()
                .body(articleService.update(userId, articleId, articleDTO));
    }

    @DeleteMapping("/users/{userId}/articles/{articleId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable(value = "userId") Long userId,
                                              @PathVariable(value = "articleId") Long articleId) {
        log.debug("REST request to delete Article : {}", articleId);
        articleService.delete(userId, articleId);
        return ResponseEntity.noContent().build();
    }


    //didnt use before. Want to try pageable
    @GetMapping(value = "/articles/{pageNo}/{pageSize}", produces = {"application/json"})
    public ResponseEntity<List<ArticleDTO>> getAllPaged(@PathVariable int pageNo,
                                                           @PathVariable int pageSize,
                                                           @RequestParam(defaultValue = "id") String sortBy) {
        log.debug("REST request to get all Articles");

        return ResponseEntity
                .ok()
                .body(articleService.findAllPaged(pageNo, pageSize, sortBy));
    }


}
