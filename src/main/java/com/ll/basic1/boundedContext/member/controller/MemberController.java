package com.ll.basic1.boundedContext.member.controller;

import com.ll.basic1.base.ResultData;
import com.ll.basic1.base.rq.Rq;
import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@AllArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;


//    @GetMapping("member/me")
//    @ResponseBody
//    public ResultData showMe(){
//        long loginedMemberId = rq.getSessionAsLong("loginedMemberId", 0);
//
//        boolean isLogined = loginedMemberId > 0;
//
//        //        if(rq.getCookies() != null){
////            loginMemberId = Arrays.stream(rq.getCookies())
////                    .filter(cookie -> cookie.getName().equals("loginMemberId"))
////                    .map(Cookie::getValue)
////                    .mapToLong(Long::parseLong)
////                    .findFirst()
////                    .orElse(0);
////        }
////        boolean isLogin = loginMemberId > 0;
//
//
//        if (isLogined == false)
//            return ResultData.of("F-1", "로그인 후 이용해주세요.");
//
//        Member member = memberService.findById(loginedMemberId);
//
//        return ResultData.of("S-1", "당신의 username(은)는 %s 입니다.".formatted(member.getUsername()));
//    }

    @GetMapping("/member/login")
    public String showLogin(String username, String password) {
        return "usr/member/login.html";
//        if(rq.isLogined()){
//            return """
//                    <h1> 이미 로그인 되었습니다.
//                    """.stripIndent();
//        }
//        return """
//                <h1>로그인</h1>
//                <form action="/member/dologin">
//                    <input type="text" name="username" placeholder="ID">
//                    <input type="text" name="password" placeholder="PW">
//                    <input type="submit" value="login">
//                </form>
//                """.stripIndent();
    }

    @PostMapping("/member/login")
    @ResponseBody
    public ResultData login(String username, String password) {
//        Map<String, Object> rsData = new LinkedHashMap<>() {{
//            put("resultCode", "S-1");
//            put("msg", "%s 님 환영합니다.".formatted(username));
//        }};
        if ( username == null || username.trim().length() == 0 ) {
            return ResultData.of("F-3", "username(을)를 입력해주세요.");
        }

        if ( password == null || password.trim().length() == 0 ) {
            return ResultData.of("F-4", "password(을)를 입력해주세요.");
        }

        ResultData resultData = memberService.tryLogin(username, password);

        if(resultData.isSuccess()){
            Member member = (Member) resultData.getData();
            rq.setSession("loginedMemberId", member.getId());
        }
        return resultData;
//        if(resultData.isSuccess()){
//            long memberId = (long)resultData.getData();
//            res.addCookie(new Cookie("loginMemberId", memberId + ""));
//
//        }
//        Rq rq = new Rq(req, resp);
//        rq.setCookie("name", username);
//        rq.setCookie("age", 23);
    }

    @GetMapping("member/logout")
    @ResponseBody
    public ResultData logout(){
        boolean cookieRemoved = rq.removeSession("loginedMemberId");
//        if(rq.getCookies() != null){
//            Arrays.stream(rq.getCookies())
//                    .filter(cookie -> cookie.getName().equals("loginMemberId"))
//                    .forEach(cookie -> {
//                        cookie.setMaxAge(0);
//                        rsp.addCookie(cookie);
//                    });
//        }
        if(cookieRemoved == false){
            return ResultData.of("S-2", "이미 로그아웃 상태입니다.");
        }
        return ResultData.of("S-1", "로그아웃 되었습니다.");
    }

    // 디버깅용 함수
    @GetMapping("/member/session")
    @ResponseBody
    public String showSession() {
        return rq.getSessionDebugContents().replaceAll("\n", "<br>");
    }

    @GetMapping("/member/me")
    public String showMe(Model model){
        long loginedMemberId = rq.getLoginedMemberId();
        Member member = memberService.findById(loginedMemberId);
        model.addAttribute("member", member); //html에 변수를 전달하고 싶을때 me라는 이름으로 member에 접근할 수 있다
        return "usr/member/me";
    }
}