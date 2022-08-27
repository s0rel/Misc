package org.example.strategy;

import java.util.Arrays;

public enum HandEnum {
    GUU(0, "石头"),
    CHO(1, "剪刀"),
    PAA(2, "布");

    private int code;

    private String desc;

    HandEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(int code) {
        return Arrays.stream(values())
                .filter(x -> x.getCode() == code)
                .map(HandEnum::getDesc)
                .findFirst()
                .orElse("");
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
