package com.ll.basic1.boundedContext.home.controller;

import com.ll.basic1.boundedContext.member.entity.Member;
import com.ll.basic1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;

@Controller
public class HomeController {
    private int i;
    private int id;
    String name;
    int age;
    List<Person> people;
    private MemberService memberService;

    public HomeController(){
        i = -1;
        people = new ArrayList<>();
        memberService = new MemberService();
    }
    //@GetMapping
    //개발자가 스프링부트에게 말함
    // /home/main이라는 요청이 오면 아래 메소드를 실행해줘
    @GetMapping("/home/main")
    //@ResponseBody
    //아래 메소드 실행 후 그 리턴값을 응답으로 삼아줘
    @ResponseBody
    public String showMain(){
        return "안녕하세요";
    }

    @GetMapping("home/main2")
    @ResponseBody
    public String showMain2(){
        return "반갑습니다";
    }

    @GetMapping("home/main3")
    @ResponseBody
    public String showMain3(){
        return "즐거웠습니다.";
    }

    @GetMapping("home/increase")
    @ResponseBody
    public int showNum(){//int값은 String화 되어서 클라이언트(브라우저) 전달됨
        return ++i;
    }

    @GetMapping("home/plus")
    @ResponseBody
    public int showPlus(@RequestParam(defaultValue = "0") int a, @RequestParam int b) {
        return a + b;
    }


    @GetMapping("home/addPerson")
    @ResponseBody
    public String addPerson(String name, int age){
        Person p = new Person(name, age);
        System.out.println(p);
        people.add(p);
        return "%d번 사람이 추가 되었습니다.".formatted(p.getId());
//        this.name = name;
//        this.age = age;
//        Map<String, Object> person = new LinkedHashMap<>(){{
//            put("id", id++);
//            put("name", name);
//            put("age", age);
//        }};
//        List<Map<String, Object>> list = new ArrayList<>();
//        for(int i = 0; i<= person.size(); i++){
//            list.add(person);
//        }
//        list.add(person);
//        return list;
    }
    @GetMapping("home/people")
    @ResponseBody
    public List<Person> showPeople(){
        return people;
    }

    @GetMapping("home/removePerson")
    @ResponseBody
    public String removePerson(int id){
        boolean removed = people.removeIf(person -> person.getId() == id);
        if(removed == false){
            return "%d번 사람이 존재하지 않습니다.".formatted(id);
        }
        /*
        * stream 안쓴 버전
        * for(Person p : people){
        *   if(p.getId() == id) people.remove(p);
        * }
        * */
        return "%d번 사람이 삭제 되었습니다.".formatted(id);
    }
    @GetMapping("home/modifyPerson")
    @ResponseBody
    public String removePerson(int id, String name, int age){
        Person found = people
                .stream()   //stream화
                .filter(p -> p.getId() == id)   //p.getId() == id인 것 찾기
                .findFirst()    //조건에 일치하는 요소들 중 순서가 가장 앞에 있는 것
                .orElse(null);

        if (found == null) {
            return "%d번 사람이 존재하지 않습니다.".formatted(id);
        }

        found.setName(name);
        found.setAge(age);

        return "%d번 사람이 수정되었습니다.".formatted(id);
//        for(Person p : people){
//            if(p.getId() == id){
//                people.set(id, new Person(name, age));
//            }
//        }
//        return "%d번 사람이 수정 되었습니다.".formatted(id);
    }

    @GetMapping("/home/reqAndResp")
    @ResponseBody
    public void showReqAndResp(HttpServletRequest rq, HttpServletResponse resp) throws IOException{
        int age = Integer.parseInt(rq.getParameter("age").replaceAll(" ", ""));
        resp.getWriter().append("Hello, you are %d years old.".formatted(age));
    }

    @GetMapping("/home/reqAndRespV2")
    @ResponseBody
    public String showReqAndRespV2(int age){
        return "Hello, you are %d years old.".formatted(age);
    }

    @GetMapping("/home/cookie/increase")
    @ResponseBody
    public int showCookieIncrease(HttpServletRequest rq, HttpServletResponse resp) throws IOException{
        int cntInCookie = 0;
        //고객이 가져온 쿠키에서 count 쿠키를 찾고 값을 가져온다
        if(rq.getCookies() != null){
            cntInCookie = Arrays.stream(rq.getCookies())
                    .filter(cookie -> cookie.getName().equals("count"))
                    .map(Cookie::getValue)
                    .mapToInt(Integer::parseInt)
                    .findFirst()
                    .orElse(0);
        }
        int newCntInCookie = cntInCookie + 1;

        //클라이언트가 가져온 count 값에 1을 더한 쿠폰을 만들어서 고객에게 보낸다
        //쉽게 말해서 클라이언트에 저장되어 있는 cnt 쿠폰의 값을 1 증가시킨다
        //이렇게 브라우저의 쿠키값을 변경하면 재방문싱데 스프링부트가 다시 그 값을 받게 되어있다
        resp.addCookie(new Cookie("count", newCntInCookie + ""));

        return newCntInCookie;
    }

    @GetMapping("/member/login1")
    @ResponseBody
    public Map<String, String> login(String username, String password){
        String userName = "user1";
        String userPw = "1234";
        Map<String, String> result = new LinkedHashMap<>();
        if(userName.equals(username) && userPw.equals(password)){
            result.put("resultCode", "S-1");
            result.put("msg", "user1님 환영합니다");
        } else if (userName.equals(username) && !userPw.equals(password)) {
            result.put("resultCode", "F-1");
            result.put("msg", "비밀번호가 일치하지 않습니다.");
        }else if(!userName.equals(username)){
            result.put("resultCode", "F-2");
            result.put("msg", "%s(은)는 존재하지 않는 회원입니다.".formatted(this.name));
        }
        return result;
    }

    @GetMapping("/home/user1")
    @ResponseBody
    public Member showUser1() {
        return memberService.findByUsername("user1");
    }
}

@AllArgsConstructor
@Getter
@ToString
class Person{
    private final int id;
    private static int lastId;
    @Setter
    private String name;
    @Setter
    private int age;
    static{
        lastId = 0;
    }
    Person(String name, int age){
        this(++lastId, name, age);
    }
}

