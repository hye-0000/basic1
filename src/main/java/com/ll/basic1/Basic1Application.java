package com.ll.basic1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing  //얘를 해줘야 @EntityListeners(AuditingEntityListener.class)요 어노에티션이 먹음
public class Basic1Application {

    public static void main(String[] args) {
        SpringApplication.run(Basic1Application.class, args);
    }

}
