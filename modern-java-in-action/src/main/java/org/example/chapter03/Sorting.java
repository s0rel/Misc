package org.example.chapter03;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;

public class Sorting {
    public static void main(String... args) {
        List<Apple> inventory = new ArrayList<>(List.of(
                new Apple(80, Color.GREEN),
                new Apple(155, Color.GREEN),
                new Apple(120, Color.RED)
        ));

        // 传递代码
        inventory.sort(new AppleComparator());
        System.out.println(inventory);

        // 使用匿名类
        inventory.set(1, new Apple(30, Color.GREEN));
        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple a1, Apple a2) {
                return a1.getWeight() - a2.getWeight();
            }
        });
        System.out.println(inventory);

        // 使用 Lambda 表达式
        inventory.set(1, new Apple(20, Color.RED));
        inventory.sort((a1, a2) -> a1.getWeight() - a2.getWeight());
        System.out.println(inventory);

        // 使用方法引用
        inventory.set(1, new Apple(10, Color.RED));
        inventory.sort(comparing(Apple::getWeight));
        System.out.println(inventory);
    }
}
