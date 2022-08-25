package org.example.bridge.answer.question3;


public class Main {
    public static void main(String[] args) {
        IncreaseDisplay d5 = new IncreaseDisplay(new CharDisplayImpl('<', '*', '>'), 1);
        IncreaseDisplay d6 = new IncreaseDisplay(new CharDisplayImpl('|', '#', '-'), 2);
        d5.increaseDisplay(4);
        d6.increaseDisplay(6);
    }
}
