package org.example.bridge.answer.question1;

import org.example.bridge.CountDisplay;
import org.example.bridge.DisplayImpl;

import java.util.Random;

public class RandomCountDisplay extends CountDisplay {
    private Random random = new Random();

    public RandomCountDisplay(DisplayImpl impl) {
        super(impl);
    }

    public void randomDisplay(int times) {
        multiDisplay(random.nextInt(times));
    }
}
