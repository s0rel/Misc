package org.example.factorymethod;

import org.example.factorymethod.framework.Factory;
import org.example.factorymethod.framework.Product;

public class Main {
    public static void main(String[] args) {
        Factory factory = new IDCardFactory();
        Product card1 = factory.create("Tom");
        Product card2 = factory.create("Jerry");
        card1.use();
        card2.use();
    }
}
