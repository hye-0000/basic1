package com.ll.basic1.boundedContext.article.controller;

import com.ll.basic1.base.ResultData;
import com.ll.basic1.boundedContext.article.entity.Article;
import com.ll.basic1.boundedContext.article.repository.ArticleRepository;
import com.ll.basic1.boundedContext.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/article")
@RequiredArgsConstructor    //필드 중에서 final 붙은 것만 인자로 입력받는 생성자 생성
public class ArticleController {
    private final ArticleService articleService;
    @GetMapping("/write")
    @ResponseBody
    public ResultData write(String title, String body){

        Article createdArticle = articleService.write(title, body);    //insert 혹은 update가 일어남
        return ResultData.of("S-1", "1번글이 생성되었습니다.", createdArticle);
    }
}
