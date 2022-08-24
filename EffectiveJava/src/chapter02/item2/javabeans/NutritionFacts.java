package chapter02.item2.javabeans;

/**
 * JavaBeans 模式
 */
public class NutritionFacts {
    private int servingSize; // required
    private int servings; // required
    private int calories; // optional
    private int fat; // optional
    private int sodium; // optional
    private int carbohydrate; // optional

    public NutritionFacts() {
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }
}
