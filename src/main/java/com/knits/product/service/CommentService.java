package com.knits.product.service;

import com.knits.product.exceptions.ExceptionCodes;
import com.knits.product.exceptions.UserException;
import com.knits.product.model.Article;
import com.knits.product.model.Comment;
import com.knits.product.model.User;
import com.knits.product.repository.ArticleRepository;
import com.knits.product.repository.CommentRepository;
import com.knits.product.repository.UserRepository;
import com.knits.product.service.dto.ArticleDTO;
import com.knits.product.service.dto.CommentDTO;
import com.knits.product.service.mapper.ArticleMapper;
import com.knits.product.service.mapper.CommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    public List<CommentDTO> findAll() {
        return commentRepository.findAll().stream().map(commentMapper::toDto).collect(Collectors.toList());
    }

    public CommentDTO save(long userId, long articleId, CommentDTO commentDTO) {
        log.debug("Request to save Comment : {}", commentDTO);
        Comment comment = commentMapper.toEntity(commentDTO);

        User user = userRepository.findById(userId).orElseThrow(()
                -> new UserException("User#" + userId + " not found", ExceptionCodes.USER_NOT_FOUND));

        Article article = articleRepository.findById(articleId).orElseThrow(()
                -> new UserException("Article#" + articleId + " not found", ExceptionCodes.USER_NOT_FOUND));

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

    public CommentDTO update(Long userId, Long commentId, CommentDTO commentDTO) {
        log.debug("Request to update Comment : {}", commentDTO);

        User user = userRepository.findById(userId).orElseThrow(()
                -> new UserException("User#" + userId + " not found", ExceptionCodes.USER_NOT_FOUND));

        Comment comment = commentRepository.findById(commentId).orElseThrow(()
                -> new UserException("Comment#" + commentId + " not found", ExceptionCodes.USER_NOT_FOUND));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new UserException("User's not found comment", ExceptionCodes.USER_NOT_FOUND);
        }

        commentMapper.partialUpdate(comment, commentDTO);

        //TODO: manage User relationship to check updates
        Comment updatedComment = commentRepository.save(comment);
        return commentMapper.toDto(updatedComment);
    }

    public void delete(Long userId, Long commentId) {
        log.debug("Delete Comment by id : {}", commentId);

        User user = userRepository.findById(userId).orElseThrow(()
                -> new UserException("User#" + userId + " not found", ExceptionCodes.USER_NOT_FOUND));

        Comment comment = commentRepository.findById(commentId).orElseThrow(()
                -> new UserException("Comment#" + commentId + " not found", ExceptionCodes.USER_NOT_FOUND));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new UserException("Comment was not created by User", ExceptionCodes.USER_NOT_FOUND);
        }

        commentRepository.deleteById(commentId);
    }
}
