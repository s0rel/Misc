package org.example.chapter06;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Dish {
    public static final List<Dish> menu = List.of(
            new Dish("pork", false, 800, Type.MEAT),
            new Dish("beef", false, 700, Type.MEAT),
            new Dish("chicken", false, 400, Type.MEAT),
            new Dish("french fries", true, 530, Type.OTHER),
            new Dish("rice", true, 350, Type.OTHER),
            new Dish("season fruit", true, 120, Type.OTHER),
            new Dish("pizza", true, 550, Type.OTHER),
            new Dish("prawns", false, 400, Type.FISH),
            new Dish("salmon", false, 450, Type.FISH)
    );

    public static final Map<String, List<String>> dishTags = new HashMap<>();

    static {
        dishTags.put("pork", List.of("greasy", "salty"));
        dishTags.put("beef", List.of("salty", "roasted"));
        dishTags.put("chicken", List.of("fried", "crisp"));
        dishTags.put("french fries", List.of("greasy", "fried"));
        dishTags.put("rice", List.of("light", "natural"));
        dishTags.put("season fruit", List.of("fresh", "natural"));
        dishTags.put("pizza", List.of("tasty", "salty"));
        dishTags.put("prawns", List.of("tasty", "roasted"));
        dishTags.put("salmon", List.of("delicious", "fresh"));
    }

    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    public Dish(String name, boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return name;
    }
}
