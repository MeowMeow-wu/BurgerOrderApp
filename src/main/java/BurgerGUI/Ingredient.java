package BurgerGUI;

import java.util.Objects;

public class Ingredient {
    private final String name;
    private final double price;
    private final int calories;
    private final String category; // e.g., "Bread", "Patty", "Cheese", "Veggies", "Sauce"
    private final boolean required;

    public Ingredient(String name, double price, int calories, String category, boolean required) {
        this.name = name;
        this.price = price;
        this.calories = calories;
        this.category = category;
        this.required = required;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getCalories() {
        return calories;
    }

    public String getCategory() {
        return category;
    }

    public boolean isRequired() {
        return required;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("%s (+$%.2f, %d kcal)%s",
                name, price, calories, required ? " [Fixed]" : "");
    }
}