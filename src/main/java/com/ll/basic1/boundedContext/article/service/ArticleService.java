package com.ll.basic1.boundedContext.article.service;

import com.ll.basic1.boundedContext.article.entity.Article;
import com.ll.basic1.boundedContext.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    public Article write(String title, String body) {
        Article article = Article
                .builder()
//                .creatDate(LocalDateTime.now())   @createDate를 해줬기 때문에 안해줘도 됨
//                .modifyDate(LocalDateTime.now())
                .title(title)
                .body(body)
                .build();
        /* 위와 같은 코드
        Article article2 = new Article(title, body);
        article2.setTitle(title);
        article2.setBody(body);
        */
        articleRepository.save(article);
        return article;
    }
}
