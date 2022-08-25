package org.example.builder;

public enum BuilderEnum {
    PLAIN(0),
    HTML(1),
    FRAME(2);

    private final int code;

    BuilderEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
