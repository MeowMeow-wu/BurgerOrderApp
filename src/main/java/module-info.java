module com.example.javafx {
    requires javafx.controls;
    requires javafx.fxml;

    opens BurgerGUI to javafx.fxml;
    exports BurgerGUI;
}