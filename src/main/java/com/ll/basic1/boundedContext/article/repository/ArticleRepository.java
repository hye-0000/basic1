package com.ll.basic1.boundedContext.article.repository;

import com.ll.basic1.boundedContext.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

//이 리포지토리를 통해서 DB와 통신할 수 있다
//이 클래스에서는
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
