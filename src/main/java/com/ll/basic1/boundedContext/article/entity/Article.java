package com.ll.basic1.boundedContext.article.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;


//DB에 저장되는 row 하나하나를 담을 객체
//즉, Entity는 테이블의 구조를 따라가야함
@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO_INCRETMENT
    private long id;
    private LocalDateTime creatDate;
    private LocalDateTime modifyDate;
    private String title;
    private String body;

}
