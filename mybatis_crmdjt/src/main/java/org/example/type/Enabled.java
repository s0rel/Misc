package org.example.type;

public enum Enabled {
    disabled(0),
    enabled(1);

    private final int value;

    Enabled(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
