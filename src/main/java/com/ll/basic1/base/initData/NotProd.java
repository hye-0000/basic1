package com.ll.basic1.base.initData;

import com.ll.basic1.boundedContext.article.service.ArticleService;
import com.ll.basic1.boundedContext.member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration  //프로그램이 실행될때 최초에 한번, Bean이라고 있는 부분을 스캔해서 객체를 만듦, Bean이 만든 객체들은 IoC 컨테이너에서 관리
@Profile({"dev", "test"})   //오직 개발 환경과 테스트 환경에서 테스트해라
//NotProd: 개발환경이 아니고, 테스트 환경이 아닐 때만 실행
public class NotProd {
    @Bean
    CommandLineRunner initData(MemberService memberService, ArticleService articleService) {
        return args -> {
            memberService.join("user1", "1234");
            memberService.join("abc", "12345");
            memberService.join("test", "12346");
            memberService.join("love", "12347");
            memberService.join("like", "12348");
            memberService.join("giving", "12349");
            memberService.join("thanks", "123410");
            memberService.join("hello", "123411");
            memberService.join("good", "123412");
            memberService.join("peace", "123413");

            articleService.write("제목1", "내용1");
            articleService.write("제목2", "내용2");
        };
    }
}
