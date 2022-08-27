package org.example.strategy;

public class Hand {
    public static final Hand[] hands = {
            new Hand(HandEnum.GUU.getCode()),
            new Hand(HandEnum.CHO.getCode()),
            new Hand(HandEnum.PAA.getCode())
    };

    private int handValue;

    public Hand(int handValue) {
        this.handValue = handValue;
    }

    public static Hand getHand(int handValue) {
        return hands[handValue];
    }

    public boolean isStrongerThan(Hand hand) {
        return fight(hand) == 1;
    }

    public boolean isWeakerThan(Hand hand) {
        return fight(hand) == -1;
    }

    private int fight(Hand hand) {
        if (this.handValue == hand.handValue) {
            return 0;
        } else if ((this.handValue + 1) % 3 == hand.handValue) {
            return 1;
        } else {
            return -1;
        }
    }
}
