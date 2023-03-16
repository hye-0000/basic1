package com.ll.basic1.boundedContext.member.service;

import com.ll.basic1.base.ResultData;
import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;

//아래 클래스의 객체는 IoC 컨테이너에 의해 생성소멸 관리된다
@Service
public class MemberService {
    private MemberRepository memberRepository;
    public MemberService(){
        memberRepository = new MemberRepository();
    }

    public ResultData tryLogin(String username, String password) {
        Member member = memberRepository.findByUsername(username);

        if (member == null) {
            return ResultData.of("F-2", "%s(은)는 존재하지 않는 회원입니다.".formatted(username));
        }

        if (!member.getPassword().equals(password)) {
            return ResultData.of("F-1", "비밀번호가 일치하지 않습니다.");
        }
        return ResultData.of("S-1", "%s 님 환영합니다.".formatted(username), member);
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public Member findById(long id) {
        return memberRepository.findById(id);
    }
}