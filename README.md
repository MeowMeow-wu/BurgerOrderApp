# BurgerOrderApp

A JavaFX desktop application built with Maven for customizing and ordering burgers interactively.

## Features
- Interactive JavaFX GUI (`BurgerGUI.BurgerOrderApp`)
- Modular domain models (`Burger`, `Ingredient`) for building custom burger orders
- Maven Wrapper included (`mvnw` / `mvnw.cmd`) so no global Maven install is required

## Project Structure
```text
src/main/java/
├── BurgerGUI/
│   ├── Burger.java
│   ├── BurgerOrderApp.java
│   └── Ingredient.java
├── com/example/javafx/
│   ├── HelloApplication.java
│   └── HelloController.java
└── module-info.java
```

## Tech Stack
- **Java** & **JavaFX 25** (`javafx-controls`, `javafx-fxml`)
- **Maven** (`pom.xml` + Maven Wrapper)
- **JUnit 5** (`junit-jupiter`)

## Getting Started

### Prerequisites
- JDK 21+ (matching your JavaFX runtime)

### Run Locally
```bash
# macOS / Linux
./mvnw clean javafx:run

# Windows (PowerShell / CMD)
.\mvnw.cmd clean javafx:run
```

### Run Tests
```bash
./mvnw test
```