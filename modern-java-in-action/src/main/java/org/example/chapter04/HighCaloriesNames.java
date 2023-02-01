package org.example.chapter04;

import java.util.List;

import static org.example.chapter04.Dish.menu;

public class HighCaloriesNames {
    public static void main(String[] args) {
        List<String> names = menu.stream()
                .filter(dish -> {
                    System.out.println("filtering " + dish.getName());
                    return dish.getCalories() > 300;
                })
                .map(dish -> {
                    System.out.println("mapping " + dish.getName());
                    return dish.getName();
                })
                .limit(3)
                .toList();
        System.out.println(names);
    }
}
