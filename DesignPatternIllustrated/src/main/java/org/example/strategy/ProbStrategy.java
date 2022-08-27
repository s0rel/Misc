package org.example.strategy;

import java.util.Random;

public class ProbStrategy implements Strategy {
    private Random random;

    private int prevHandValue = 0;

    private int currHandValue = 0;

    private int[][] histories = {
            {1, 1, 1,},
            {1, 1, 1,},
            {1, 1, 1,}
    };

    public ProbStrategy(int seed) {
        this.random = new Random(seed);
    }

    @Override
    public Hand nextHand() {
        int bet = random.nextInt(getSum(currHandValue));
        int handValue = 0;
        if (bet < histories[currHandValue][0]) {
            handValue = 0;
        } else if (bet < histories[currHandValue][0] + histories[currHandValue][1]) {
            handValue = 1;
        } else {
            handValue = 2;
        }

        prevHandValue = currHandValue;
        currHandValue = handValue;
        return Hand.getHand(handValue);
    }

    @Override
    public void study(boolean win) {
        if (win) {
            histories[prevHandValue][currHandValue]++;
        } else {
            histories[prevHandValue][(currHandValue + 1) % 3]++;
            histories[prevHandValue][(currHandValue + 2) % 3]++;
        }
    }

    private int getSum(int hv) {
        int sum = 0;
        for (int i = 0; i < 3; i++) {
            sum += histories[hv][i];
        }
        return sum;
    }
}
