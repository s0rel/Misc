package org.example.strategy;

public interface Strategy {
    Hand nextHand();

    void study(boolean win);
}
