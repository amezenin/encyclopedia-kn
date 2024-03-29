package com.knits.product.service;

import com.knits.product.exceptions.ExceptionCodes;
import com.knits.product.exceptions.UserException;
import com.knits.product.entity.Article;
import com.knits.product.entity.Comment;
import com.knits.product.entity.User;
import com.knits.product.repository.CommentRepository;
import com.knits.product.repository.UserRepository;
import com.knits.product.service.dto.CommentDTO;
import com.knits.product.service.dto.UserDTO;
import com.knits.product.service.mapper.ArticleMapper;
import com.knits.product.service.mapper.CommentMapper;
import com.knits.product.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CommentService {


    private final CommentMapper commentMapper;

    private final UserMapper userMapper;

    private final ArticleMapper articleMapper;

    private final ArticleService articleService;

    private final UserService userService;

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    public List<CommentDTO> findAll() {
        return commentRepository.findAll().stream().map(commentMapper::toDto).collect(Collectors.toList());
    }

    public CommentDTO findById(Long id) {

        log.debug("Request User by id : {}", id);
        Comment comment = commentRepository.findById(id).orElseThrow(()
                -> new UserException("User#" + id + " not found", ExceptionCodes.USER_NOT_FOUND));
        return commentMapper.toDto(comment);
    }

    public CommentDTO save(long userId, long articleId, CommentDTO commentDTO) {
        log.debug("Request to save Comment : {}", commentDTO);
        Comment comment = getCommentEntity(commentDTO);

        User user = getUserEntity(userId);

        Article article = articleMapper.toEntity(articleService.findById(articleId));

        //set user
        comment.setUser(user);
        comment.setArticle(article);

        Comment newComment = commentRepository.save(comment);

        return commentMapper.toDto(newComment);
    }

    public List<CommentDTO> findCommentsByArticleId(long articleId) {
        // retrieve articles by userId
        List<Comment> comments = commentRepository.findByArticleId(articleId);

        // convert list of comment entities to list of comment dto's
        return commentMapper.toDto(comments);
    }

    public CommentDTO updateByUser(Long userId, Long commentId, CommentDTO commentDTO) {
        log.debug("Request to update Comment : {}", commentDTO);

        User user = getUserEntity(userId);

        Comment comment = getComment(commentId);

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new UserException("User's not found comment", ExceptionCodes.USER_NOT_FOUND);
        }

        commentMapper.partialUpdate(comment, commentDTO);

        //TODO: manage User relationship to check updates
        Comment updatedComment = commentRepository.save(comment);
        return commentMapper.toDto(updatedComment);
    }

    public CommentDTO partialUpdate(CommentDTO commentDTO) {
        log.debug("Request to partially update Comment : {}", commentDTO);
        Comment comment = getComment(commentDTO.getId());

        commentMapper.partialUpdate(comment, commentDTO); // problem here

       // user.addlikedComment(comment);
       // userRepository.save(user);

        //TODO: manage User relationship to check updates
        Comment updatedComment = commentRepository.save(comment);
        return commentMapper.toDto(updatedComment);
    }

    public void deleteByUser(Long userId, Long commentId) {
        log.debug("Delete Comment by id : {}", commentId);

        User user = getUserEntity(userId);

        Comment comment = getComment(commentId);

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new UserException("Comment was not created by User", ExceptionCodes.USER_NOT_FOUND);
        }

        commentRepository.deleteById(commentId);
    }

    public void delete(Long id) {
        log.debug("Delete Comment by id : {}", id);
        Comment comment = getComment(id);
        //for many to many, remove likes before delete
        for (User user1 : comment.getLikes()) {
            user1.removelikedComment(comment);
        }

        commentRepository.deleteById(id);
    }

    //helper methods
    private User getUserEntity(long userId) {
        return userMapper.toEntity(userService.findById(userId));
    }

    private Comment getCommentEntity(CommentDTO commentDTO) {
        return commentMapper.toEntity(commentDTO);
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(()
                -> new UserException("Comment#" + commentId + " not found", ExceptionCodes.USER_NOT_FOUND));
    }
}
