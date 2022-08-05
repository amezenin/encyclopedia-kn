package com.knits.product.controller;

import com.knits.product.entity.Comment;
import com.knits.product.exceptions.UserException;
import com.knits.product.service.ArticleService;
import com.knits.product.service.CommentService;
import com.knits.product.service.dto.ArticleDTO;
import com.knits.product.service.dto.CommentDTO;
import com.knits.product.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping(value = "/comments/all", produces = {"application/json"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @CrossOrigin
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        log.debug("REST request to get all Articles");
        return ResponseEntity
                .ok()
                .body(commentService.findAll());
    }

    @GetMapping(value = "/comments/{id}", produces = {"application/json"})
    @CrossOrigin
    public ResponseEntity<CommentDTO> getCommentById( @PathVariable(value = "id", required = true) final Long id) {

        log.debug("REST request to get Comment : {}", id);
        CommentDTO commentDTO = commentService.findById(id);
        return ResponseEntity
                .ok()
                .body(commentDTO);
    }

    @PostMapping(value = "/comments/articles/{articleId}/users/{userId}", produces = {"application/json"}, consumes = { "application/json"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @CrossOrigin
    public ResponseEntity<CommentDTO> createComment(@PathVariable(value = "userId") long userId,
                                                    @PathVariable(value = "articleId") long articleId,
                                                    @RequestBody CommentDTO commentDTO) {
        log.debug("REST request to createComment Comment ");
        if (commentDTO == null) {
            throw new UserException("Comment data are missing");
        }
        return ResponseEntity
                .ok()
                .body(commentService.save(userId, articleId, commentDTO));
    }

    @GetMapping(value = "/articles/{articleId}/comments", produces = {"application/json"})
    public ResponseEntity<List<CommentDTO>> getCommentsByArticleId(@PathVariable(value = "articleId") Long articleId) {

        log.debug("REST request to get Comments : {}", articleId);
        List<CommentDTO> commentsDTO = commentService.findCommentsByArticleId(articleId);
        return ResponseEntity
                .ok()
                .body(commentsDTO);

    }

    @PutMapping(value = "/users/{userId}/comments/{commentId}", produces = {"application/json"}, consumes = { "application/json"})
    public ResponseEntity<CommentDTO> updateCommentByUser(@PathVariable(value = "userId") Long userId,
                                                    @PathVariable(value = "commentId") Long commentId,
                                                    @RequestBody CommentDTO commentDTO) {
        log.debug("REST request to updateComment Comment ");
        if (commentDTO == null) {
            throw new UserException("Comment data are missing");
        }
        return ResponseEntity
                .ok()
                .body(commentService.updateByUser(userId, commentId, commentDTO));
    }

    @PatchMapping(value = "/comments/{id}",  produces = {"application/json"},
            consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @CrossOrigin
    public ResponseEntity<CommentDTO> partialUpdateComment(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody CommentDTO commentDTO){
        log.debug("REST request to updateUser User ");

        if (commentDTO == null) {
            throw new UserException("Comment data are missing");
        }
        commentDTO.setId(id);
        return ResponseEntity
                .ok()
                .body(commentService.partialUpdate(commentDTO));
    }

    @DeleteMapping("/users/{userId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable(value = "userId") Long userId,
                                              @PathVariable(value = "commentId") Long commentId) {
        log.debug("REST request to delete Comment : {}", commentId);
        commentService.deleteByUser(userId, commentId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/comments/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @CrossOrigin
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Comment : {}", id);
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
