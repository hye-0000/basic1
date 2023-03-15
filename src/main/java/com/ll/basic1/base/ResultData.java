package com.ll.basic1.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResultData {
    private final String resultCode;
    private final String msg;

    public static ResultData of(String resultCode, String msg) {
        return new ResultData(resultCode, msg);
    }
}