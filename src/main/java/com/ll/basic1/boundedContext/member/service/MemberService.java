package com.ll.basic1.boundedContext.member.service;

import com.ll.basic1.base.ResultData;
import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

//아래 클래스의 객체는 IoC 컨테이너에 의해 생성소멸 관리된다
@Service
@AllArgsConstructor
public class MemberService {
    private MemberRepository memberRepository;

    public ResultData tryLogin(String username, String password) {
        Member member = memberRepository.findByUsername(username).orElse(null); //값이 있으면 들어가고 없으면 null이 들어간다

        if (member == null) {
            return ResultData.of("F-2", "%s(은)는 존재하지 않는 회원입니다.".formatted(username));
        }

        if (!member.getPassword().equals(password)) {
            return ResultData.of("F-1", "비밀번호가 일치하지 않습니다.");
        }
        return ResultData.of("S-1", "%s 님 환영합니다.".formatted(username), member);
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username).orElse(null);
    }

    public Member findById(long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public Member join(String username, String password){
        Member member = Member.builder()
                .username(username)
                .password(password)
                .build();

        memberRepository.save(member);

        return member;
    }
}