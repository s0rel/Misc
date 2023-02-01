package org.example.chapter05;


import java.util.List;

import static org.example.chapter05.Dish.menu;


public class Filtering {
    public static void main(String... args) {
        // Filtering with predicate
        System.out.println("Filtering with a predicate");
        List<Dish> vegetarianMenu = menu.stream()
                .filter(Dish::isVegetarian)
                .toList();
        vegetarianMenu.forEach(System.out::println);

        // Filtering unique elements
        System.out.println("Filtering unique elements:");
        List<Integer> numbers = List.of(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .forEach(System.out::println);

        // Slicing a stream
        // This list is sorted in ascending order of number of calories!
        List<Dish> specialMenu = List.of(
                new Dish("season fruit", true, 120, Type.OTHER),
                new Dish("prawns", false, 300, Type.FISH),
                new Dish("rice", true, 350, Type.OTHER),
                new Dish("chicken", false, 400, Type.MEAT),
                new Dish("french fries", true, 530, Type.OTHER));
        System.out.println("Filtered sorted menu:");
        List<Dish> filteredMenu = specialMenu.stream()
                .filter(dish -> dish.getCalories() < 320)
                .toList();
        filteredMenu.forEach(System.out::println);

        System.out.println("Sorted menu sliced with takeWhile():");
        List<Dish> slicedMenu1 = specialMenu.stream()
                .takeWhile(dish -> dish.getCalories() < 320)
                .toList();
        slicedMenu1.forEach(System.out::println);

        System.out.println("Sorted menu sliced with dropWhile():");
        List<Dish> slicedMenu2 = specialMenu.stream()
                .dropWhile(dish -> dish.getCalories() < 320)
                .toList();
        slicedMenu2.forEach(System.out::println);

        // Truncating a stream
        List<Dish> dishesLimit3 = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .limit(3)
                .toList();
        System.out.println("Truncating a stream:");
        dishesLimit3.forEach(System.out::println);

        // Skipping elements
        List<Dish> dishesSkip2 = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(2)
                .toList();
        System.out.println("Skipping elements:");
        dishesSkip2.forEach(System.out::println);
    }
}
