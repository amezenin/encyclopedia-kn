package com.knits.product.controller;

import com.knits.product.exceptions.UserException;
import com.knits.product.service.ArticleService;
import com.knits.product.service.UserService;
import com.knits.product.service.dto.ArticleDTO;
import com.knits.product.service.dto.RoleDTO;
import com.knits.product.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping(value = "/articles", produces = {"application/json"})
    @CrossOrigin
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        log.debug("REST request to get all Articles");
        return ResponseEntity
                .ok()
                .body(articleService.findAll());
    }

    @PostMapping(value = "/articles", produces = {"application/json"}, consumes = { "application/json"})
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    //second save method for fast testing
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO articleDTO) {
        log.debug("REST request to createArticle Article ");
        if (articleDTO == null) {
            throw new UserException("Article data are missing");
        }
        return ResponseEntity
                .ok()
                .body(articleService.save(articleDTO));
    }

    @PostMapping(value = "/articles/users/{userId}", produces = {"application/json"}, consumes = { "application/json"})
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

    @GetMapping("/articles/users/{userId}")
    @CrossOrigin
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public List<ArticleDTO> getArticlesByUserId(@PathVariable(value = "userId") Long userId){
        return articleService.getArticlesByUserId(userId);
    }

    @GetMapping(value = "/articles/{id}", produces = {"application/json"})
    @CrossOrigin
    public ResponseEntity<ArticleDTO> getArticleById( @PathVariable(value = "id", required = true) final Long id) {

        log.debug("REST request to get Article : {}", id);
        ArticleDTO articleFound = articleService.findById(id);
        return ResponseEntity
                .ok()
                .body(articleFound);
    }



    @GetMapping(value = "/articles/{id}/users/{userId}", produces = {"application/json"})
    public ResponseEntity<ArticleDTO> getArticleByIdAndUserId(@PathVariable(value = "userId", required = true) final Long userId,
                                                     @PathVariable(value = "id") Long articleId) {

        log.debug("REST request to get Article : {}", articleId);
        ArticleDTO articleDTO = articleService.findByIdAndUserId(userId, articleId);
        return ResponseEntity
                .ok()
                .body(articleDTO);
    }

    @PutMapping(value = "/articles/{articleId}/users/{userId}", produces = {"application/json"}, consumes = { "application/json"})
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

    @PatchMapping(value = "/articles/{id}",  produces = {"application/json"},
            consumes = { "application/json", "application/merge-patch+json" })
    @CrossOrigin
    public ResponseEntity<ArticleDTO> partialUpdateArticle(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody ArticleDTO articleDTO){
        log.debug("REST request to updateArticle Article ");

        if (articleDTO == null) {
            throw new UserException("Article data are missing");
        }
        articleDTO.setId(id);

        return ResponseEntity
                .ok()
                .body(articleService.partialUpdate(articleDTO));
    }

    @DeleteMapping("/users/{userId}/articles/{articleId}")
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Void> deleteArticle(@PathVariable(value = "userId") Long userId,
                                              @PathVariable(value = "articleId") Long articleId) {
        log.debug("REST request to delete Article : {}", articleId);
        articleService.deleteByUser(userId, articleId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/articles/{articleId}")
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Void> deleteArticle(@PathVariable(value = "articleId") Long articleId) {
        log.debug("REST request to delete Article : {}", articleId);
        articleService.delete(articleId);
        return ResponseEntity.noContent().build();
    }


    //didnt use before. Want to try pageable
    @GetMapping(value = "/articles/{page}/{size}", produces = {"application/json"})
    public ResponseEntity<List<ArticleDTO>> getAllPaged(@PathVariable int page,
                                                           @PathVariable int size,
                                                           @RequestParam(defaultValue = "id") String sortBy) {
        log.debug("REST request to get all Articles");

        return ResponseEntity
                .ok()
                .body(articleService.findAllPaged(page, size, sortBy));
    }

}
