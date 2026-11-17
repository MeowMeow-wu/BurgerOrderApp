package BurgerGUI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class BurgerOrderApp extends Application {

    private Burger currentBurger;

    // GUI 控件
    private ComboBox<String> burgerSelector;
    private ListView<Ingredient> includedListView;
    private ListView<Ingredient> availableListView;
    private Spinner<Integer> quantitySpinner;
    private TextArea summaryArea;
    private Label feedbackLabel;

    // 可选配料池（供用户追加）
    private final List<Ingredient> extraIngredientsPool = Arrays.asList(
            new Ingredient("Cheddar Cheese", 0.80, 110, "Dairy", false),
            new Ingredient("Crispy Bacon", 1.50, 140, "Meat", false),
            new Ingredient("Fried Egg", 1.00, 90, "Dairy", false),
            new Ingredient("Grilled Mushrooms", 0.70, 35, "Vegetable", false),
            new Ingredient("Jalapenos", 0.40, 15, "Vegetable", false),
            new Ingredient("Lettuce", 0.30, 10, "Vegetable", false),
            new Ingredient("Tomatoes", 0.40, 20, "Vegetable", false),
            new Ingredient("Pickles", 0.30, 5, "Vegetable", false),
            new Ingredient("BBQ Sauce", 0.40, 50, "Sauce", false),
            new Ingredient("Mayonnaise", 0.40, 90, "Sauce", false),
            new Ingredient("Tartar Sauce", 0.50, 80, "Sauce", false)
    );

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(16));

        // 顶部：汉堡选择区域
        HBox topBox = new HBox(12);
        topBox.setAlignment(Pos.CENTER_LEFT);
        topBox.setPadding(new Insets(0, 0, 12, 0));

        Label selectLabel = new Label("Select Base Burger:");
        burgerSelector = new ComboBox<>();
        burgerSelector.getItems().addAll("Beef Burger", "Chicken Burger", "Fish Burger");
        burgerSelector.setPromptText("-- Choose Burger --");

        topBox.getChildren().addAll(selectLabel, burgerSelector);
        root.setTop(topBox);

        // 中部：配料增删界面
        HBox centerBox = new HBox(16);
        centerBox.setAlignment(Pos.CENTER);

        // 左侧列表：当前汉堡包含的配料
        VBox includedBox = new VBox(6);
        Label incLabel = new Label("Current Ingredients (in burger):");
        includedListView = new ListView<>();
        includedListView.setPrefWidth(260);
        includedBox.getChildren().addAll(incLabel, includedListView);

        // 中间控制按钮
        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        Button addButton = new Button("<< Add");
        Button removeButton = new Button("Remove >>");
        addButton.setMinWidth(90);
        removeButton.setMinWidth(90);
        buttonBox.getChildren().addAll(addButton, removeButton);

        // 右侧列表：可添加的额外配料
        VBox availableBox = new VBox(6);
        Label availLabel = new Label("Available Extras:");
        availableListView = new ListView<>();
        availableListView.setPrefWidth(260);
        availableBox.getChildren().addAll(availLabel, availableListView);

        centerBox.getChildren().addAll(includedBox, buttonBox, availableBox);
        root.setCenter(centerBox);

        // 底部：数量、按钮与订单小票
        VBox bottomBox = new VBox(10);
        bottomBox.setPadding(new Insets(12, 0, 0, 0));

        HBox orderControls = new HBox(12);
        orderControls.setAlignment(Pos.CENTER_LEFT);

        Label qtyLabel = new Label("Quantity (1-5):");
        quantitySpinner = new Spinner<>(1, 5, 1);
        quantitySpinner.setEditable(false);
        quantitySpinner.setPrefWidth(70);

        Button calculateButton = new Button("Calculate Order");
        calculateButton.setStyle("-fx-base: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

        Button clearButton = new Button("Clear Order");
        clearButton.setStyle("-fx-base: #f44336; -fx-text-fill: white;");

        orderControls.getChildren().addAll(qtyLabel, quantitySpinner, calculateButton, clearButton);

        feedbackLabel = new Label();
        feedbackLabel.setStyle("-fx-font-weight: bold;");

        summaryArea = new TextArea();
        summaryArea.setEditable(false);
        summaryArea.setPrefRowCount(6);
        summaryArea.setPromptText("Order summary will appear here...");

        bottomBox.getChildren().addAll(orderControls, feedbackLabel, summaryArea);
        root.setBottom(bottomBox);

        // 事件监听绑定
        burgerSelector.setOnAction(e -> handleBurgerSelection());
        addButton.setOnAction(e -> handleAddIngredient());
        removeButton.setOnAction(e -> handleRemoveIngredient());
        calculateButton.setOnAction(e -> handleCalculateOrder());
        clearButton.setOnAction(e -> handleClearOrder());

        Scene scene = new Scene(root, 680, 560);
        primaryStage.setTitle("Gourmet Burger Order Station");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleBurgerSelection() {
        String selectedType = burgerSelector.getValue();
        if (selectedType == null) return;

        currentBurger = Burger.createStandardBurger(selectedType);
        updateIngredientLists();
        setFeedback("Base burger loaded: " + currentBurger.getName(), Color.GREEN);
        summaryArea.clear();
    }

    private void handleAddIngredient() {
        if (currentBurger == null) {
            setFeedback("Please select a burger first.", Color.RED);
            return;
        }

        Ingredient selected = availableListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            setFeedback("Select an ingredient from 'Available Extras' to add.", Color.ORANGE);
            return;
        }

        boolean success = currentBurger.addIngredient(selected);
        if (success) {
            updateIngredientLists();
            setFeedback("Added: " + selected.getName(), Color.GREEN);
        } else {
            setFeedback(selected.getName() + " is already in the burger.", Color.RED);
        }
    }

    private void handleRemoveIngredient() {
        if (currentBurger == null) {
            setFeedback("Please select a burger first.", Color.RED);
            return;
        }

        Ingredient selected = includedListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            setFeedback("Select an ingredient from 'Current Ingredients' to remove.", Color.ORANGE);
            return;
        }

        if (selected.isRequired()) {
            setFeedback("Cannot remove " + selected.getName() + " (Mandatory base ingredient).", Color.RED);
            return;
        }

        boolean success = currentBurger.removeIngredient(selected);
        if (success) {
            updateIngredientLists();
            setFeedback("Removed: " + selected.getName(), Color.GREEN);
        }
    }

    private void handleCalculateOrder() {
        if (currentBurger == null) {
            setFeedback("Cannot calculate order without selecting a burger.", Color.RED);
            return;
        }

        int quantity = quantitySpinner.getValue();
        double unitPrice = currentBurger.calculateUnitPrice();
        int unitCalories = currentBurger.calculateUnitCalories();
        double totalPrice = unitPrice * quantity;
        int totalCalories = unitCalories * quantity;

        StringBuilder sb = new StringBuilder();
        sb.append("================ ORDER SUMMARY ================\n");
        sb.append(String.format("Burger: %s (Quantity: %d)\n", currentBurger.getName(), quantity));
        sb.append("Custom Ingredients:\n");
        for (Ingredient ing : currentBurger.getIngredients()) {
            sb.append(String.format("  - %s ($%.2f, %d kcal)\n", ing.getName(), ing.getPrice(), ing.getCalories()));
        }
        sb.append("-----------------------------------------------\n");
        sb.append(String.format("Single Burger: $%.2f | %d kcal\n", unitPrice, unitCalories));
        sb.append(String.format("TOTAL PRICE:   $%.2f\n", totalPrice));
        sb.append(String.format("TOTAL CALORIES:%d kcal\n", totalCalories));
        sb.append("===============================================");

        summaryArea.setText(sb.toString());
        setFeedback("Order calculated successfully.", Color.GREEN);
    }

    private void handleClearOrder() {
        currentBurger = null;
        burgerSelector.setValue(null);
        includedListView.getItems().clear();
        availableListView.getItems().clear();
        quantitySpinner.getValueFactory().setValue(1);
        summaryArea.clear();
        setFeedback("Order cleared.", Color.BLUE);
    }

    private void updateIngredientLists() {
        includedListView.setItems(FXCollections.observableArrayList(currentBurger.getIngredients()));

        ObservableList<Ingredient> availableItems = FXCollections.observableArrayList();
        for (Ingredient ing : extraIngredientsPool) {
            if (!currentBurger.containsIngredient(ing)) {
                availableItems.add(ing);
            }
        }
        availableListView.setItems(availableItems);
    }

    private void setFeedback(String message, Color color) {
        feedbackLabel.setText(message);
        feedbackLabel.setTextFill(color);
    }

    public static void main(String[] args) {
        launch(args);
    }
}