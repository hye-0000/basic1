package com.ll.basic1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    private int i;
    private int id;
    String name;
    int age;
    List<Person> people;

    public HomeController(){
        i = -1;
        people = new ArrayList<>();
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
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst()
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
