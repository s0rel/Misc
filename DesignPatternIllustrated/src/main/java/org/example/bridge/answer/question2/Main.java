package org.example.bridge.answer.question2;


import org.example.bridge.CountDisplay;

public class Main {
    public static void main(String[] args) {
        CountDisplay d4 = new CountDisplay(new FileDisplayImpl("star.txt"));
        d4.multiDisplay(3);
    }
}
