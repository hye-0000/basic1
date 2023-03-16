package com.ll.basic1.base.rq;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public class Rq {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;

    public boolean removeCookie(String name) {
        if (req.getCookies() != null) { //쿠기가 있는지 체크
            Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals(name))    //쿠키 중에 이름이 일치하는 것을 찾음
                    .forEach(cookie -> {
                        cookie.setMaxAge(0);
                        resp.addCookie(cookie);
                    });
            return Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals(name))
                    .count() > 0;
        }
        return false;
    }

    public String getCookie(String name, String defaultValue){
        if(req.getCookies() == null) return defaultValue;

        return Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals(name))//쿠키에서 일치하는 값을 찾음
                .map(Cookie::getValue)//값을 꺼내옴
                .findFirst()
                .orElse(defaultValue);//없으면 defaultValue를 넣어줌
    }

    public long getCookieAsLong(String name, long defaultValue) {
        String value = getCookie(name, null);

        if ( value == null ) {
            return defaultValue;
        }

        try {
            return Long.parseLong(value);
        }
        catch ( NumberFormatException e ) {
            return defaultValue;
        }
    }

    public void setCookie(String name, long value) {
        setCookie(name, value + "");
    }

    public void setCookie(String name, String value) {
        resp.addCookie(new Cookie(name, value));
    }
}
