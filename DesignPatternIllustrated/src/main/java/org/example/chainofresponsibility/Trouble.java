package org.example.chainofresponsibility;

public class Trouble {
    private int number;

    public Trouble(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Trouble{" +
                "number=" + number +
                '}';
    }
}