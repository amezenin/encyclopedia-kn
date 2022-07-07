package com.knits.product.service;

import com.knits.product.exceptions.ExceptionCodes;
import com.knits.product.exceptions.UserException;
import com.knits.product.model.Article;
import com.knits.product.model.User;
import com.knits.product.repository.ArticleRepository;
import com.knits.product.repository.UserRepository;
import com.knits.product.service.dto.ArticleDTO;
import com.knits.product.service.dto.UserDTO;
import com.knits.product.service.mapper.ArticleMapper;
import com.knits.product.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleMapper articleMapper;

    private final UserMapper userMapper;

    private final ArticleRepository articleRepository;

    private final UserService userService;

    public ArticleDTO save(long userId, ArticleDTO articleDTO) {
        log.debug("Request to save Article : {}", articleDTO);
        Article article = articleMapper.toEntity(articleDTO);

        User user = getUser(userId);

        //set user
        article.setUser(user);

        Article newArticle = articleRepository.save(article);

        return articleMapper.toDto(newArticle);
    }


    public List<ArticleDTO> findAll() {
        return articleRepository.findAll().stream().map(articleMapper::toDto).collect(Collectors.toList());
    }

    public ArticleDTO findById(Long id) {

        log.debug("Request Article by id : {}", id);
        Article article = getArticle(id);
        return articleMapper.toDto(article);
    }



    public ArticleDTO findByIdAndUserId(Long userId, Long articleId) {

        log.debug("Request Article by id : {}", articleId);
        User user = getUser(userId);

        Article article = getArticle(articleId);

        if (!article.getUser().getId().equals(user.getId())) {
            throw new UserException("Users not found comment", ExceptionCodes.USER_NOT_FOUND);
        }

        return articleMapper.toDto(article);
    }


    public List<ArticleDTO> getArticlesByUserId(long userId) {
        // retrieve articles by userId
        List<Article> articles = articleRepository.findByUserId(userId);

        // convert list of comment entities to list of comment dto's
        return articleMapper.toDto(articles);
    }

    public ArticleDTO update(Long userId, Long articleId, ArticleDTO articleDTO) {
        log.debug("Request to update Article : {}", articleDTO);

        User user = getUser(userId);

        Article article = getArticle(articleId);

        if (!article.getUser().getId().equals(user.getId())) {
            throw new UserException("Users not found articles", ExceptionCodes.USER_NOT_FOUND);
        }

        articleMapper.partialUpdate(article, articleDTO);

        //TODO: manage User relationship to check updates
        Article updatedArticle = articleRepository.save(article);
        return articleMapper.toDto(updatedArticle);
    }

    public void delete(Long userId, Long articleId) {
        log.debug("Delete Article by id : {}", articleId);

        User user = getUser(userId);

        Article article = getArticle(articleId);

        if (!article.getUser().getId().equals(user.getId())) {
            throw new UserException("Article was not created by User", ExceptionCodes.USER_NOT_FOUND);
        }

        articleRepository.deleteById(articleId);
    }

    //didnt use before. want to try
    public List<ArticleDTO> findAllPaged(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Article> pagedResult = articleRepository.findAll(paging);

        return pagedResult.stream().map(articleMapper::toDto).collect(Collectors.toList());
    }

    //helper methods
    private Article getArticle(Long id) {
        return articleRepository.findById(id).orElseThrow(()
                -> new UserException("Article#" + id + " not found", ExceptionCodes.USER_NOT_FOUND));
    }

    private User getUser(long userId) {
        return userMapper.toEntity(userService.findById(userId));
    }


}
