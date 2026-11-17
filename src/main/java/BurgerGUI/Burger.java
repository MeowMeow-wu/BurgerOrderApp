package BurgerGUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Burger {
    private final String name;
    private final List<Ingredient> ingredients;

    public Burger(String name) {
        this.name = name;
        this.ingredients = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }

    public boolean containsIngredient(Ingredient ingredient) {
        return ingredients.contains(ingredient);
    }

    public boolean addIngredient(Ingredient ingredient) {
        if (ingredient == null || containsIngredient(ingredient)) {
            return false;
        }
        return ingredients.add(ingredient);
    }

    public boolean removeIngredient(Ingredient ingredient) {
        if (ingredient == null || ingredient.isRequired()) {
            return false;
        }
        return ingredients.remove(ingredient);
    }

    public double calculateUnitPrice() {
        return ingredients.stream().mapToDouble(Ingredient::getPrice).sum();
    }

    public int calculateUnitCalories() {
        return ingredients.stream().mapToInt(Ingredient::getCalories).sum();
    }

    // Factory method demonstrating object creation and returning an object
    public static Burger createStandardBurger(String type) {
        Burger burger;
        switch (type.trim().toLowerCase()) {
            case "beef burger":
                burger = new Burger("Beef Burger");
                burger.addIngredient(new Ingredient("Brioche Bun", 1.50, 160, "Bread", true));
                burger.addIngredient(new Ingredient("Beef Patty", 4.00, 290, "Patty", true));
                burger.addIngredient(new Ingredient("Cheddar Cheese", 0.80, 110, "Dairy", false));
                burger.addIngredient(new Ingredient("Lettuce", 0.30, 10, "Vegetable", false));
                burger.addIngredient(new Ingredient("BBQ Sauce", 0.40, 50, "Sauce", false));
                break;

            case "chicken burger":
                burger = new Burger("Chicken Burger");
                burger.addIngredient(new Ingredient("Sesame Bun", 1.20, 150, "Bread", true));
                burger.addIngredient(new Ingredient("Crispy Chicken Patty", 3.50, 240, "Patty", true));
                burger.addIngredient(new Ingredient("Mayonnaise", 0.40, 90, "Sauce", false));
                burger.addIngredient(new Ingredient("Pickles", 0.30, 5, "Vegetable", false));
                burger.addIngredient(new Ingredient("Lettuce", 0.30, 10, "Vegetable", false));
                break;

            case "fish burger":
                burger = new Burger("Fish Burger");
                burger.addIngredient(new Ingredient("Standard Bun", 1.00, 140, "Bread", true));
                burger.addIngredient(new Ingredient("Breaded Fish Fillet", 3.20, 210, "Patty", true));
                burger.addIngredient(new Ingredient("Tartar Sauce", 0.50, 80, "Sauce", false));
                burger.addIngredient(new Ingredient("Cheddar Cheese", 0.80, 110, "Dairy", false));
                break;

            default:
                throw new IllegalArgumentException("Unknown burger type: " + type);
        }
        return burger;
    }
}