package org.example.strategy;

import java.util.Random;

public class WinningStrategy implements Strategy {
    private Random random;

    private boolean won = false;

    private Hand prevHand;

    public WinningStrategy(int seed) {
        this.random = new Random(seed);
    }

    @Override
    public Hand nextHand() {
        // 输了的话，随机出手势；赢了的话，继续用上一局的手势
        if (!won) {
            prevHand = Hand.getHand(random.nextInt(3));
        }
        return prevHand;
    }

    @Override
    public void study(boolean win) {
        won = win;
    }
}
