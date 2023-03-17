package com.ll.basic1.boundedContext.article.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


//DB에 저장되는 row 하나하나를 담을 객체
//즉, Entity는 테이블의 구조를 따라가야함
@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)  //이걸 해줘야 createDate/LastModifiedDate가 동작함
public class Article {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO_INCRETMENT
    private long id;
    @CreatedDate    //자동으로 데이터가 등록 될 때 들어감.그냥 외우기!(알아두기)
    private LocalDateTime creatDate;
    @LastModifiedDate   //자동으로 데이터가 수정 될 때 들어감.그냥 외우기!(알아두기)
    private LocalDateTime modifyDate;
    private String title;
    private String body;

}
