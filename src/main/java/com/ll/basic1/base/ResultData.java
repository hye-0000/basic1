package com.ll.basic1.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResultData {
    private final String resultCode;
    private final String msg;
    private final Object data;

    public static ResultData of(String resultCode, String msg) {
        return new ResultData(resultCode, msg, null);
    }

    public static ResultData of(String resultCode, String msg, Object data) {
        return new ResultData(resultCode, msg, data);
    }

    public boolean isSuccess() {
        return resultCode.startsWith("S-");
    }
}