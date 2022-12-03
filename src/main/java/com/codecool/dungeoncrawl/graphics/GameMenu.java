package com.codecool.dungeoncrawl.graphics;

import com.codecool.dungeoncrawl.*;
import com.codecool.dungeoncrawl.file.FileChooserJson;
import com.codecool.dungeoncrawl.file.FileSaver;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.controller.SaveController;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.json.GameJSONToMap;
import com.codecool.dungeoncrawl.logic.util.SoundUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.FileNotFoundException;
import java.io.IOException;

public class GameMenu {
    public static GameMap map = MapLoader.loadMap("/map1.txt", false);
    public static GameCamera gameCamera = new GameCamera(0, 0, map);
    private static GameMap map1;
    public static Stage stage;
    public static Button startButton = new Button("Start the Game");
    public static Button backButton = new Button("Back to Menu");
    public static Button rulesButton = new Button("Show game rules");
    public static Button loadGameButton = new Button("Load saved game");
    public static Button exitGameButton = new Button("Exit Game");
    private static Movements movements;
    private static Thread thread;
    public static RightUiPanel rightUI = new RightUiPanel(GameMenu.map.getPlayer());
    public static FileSaver fileSaver = new FileSaver();
    public static FileChooserJson fileLoader = new FileChooserJson();
    public static Canvas canvas = new Canvas(
            25 * Tiles.TILE_WIDTH,
            21 * Tiles.TILE_WIDTH);

    public static void setWidthAndHeight(BorderPane menu) {
        menu.setPrefWidth(1084);
        menu.setPrefHeight(768);
    }

    public void mainMenu(Stage primaryStage) throws FileNotFoundException, RuntimeException {
        SoundUtils.playContinuously(SoundUtils.MENU_BACKGROUND, .6f);
        Button startGameButton = new Button("Start the Game");
        startGameButton.setId("buttons");

        startGameButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            try {
                gameSettings(primaryStage);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });

        loadGameAddListener();
        showRules(primaryStage);
        exitGame();

        VBox buttons = new VBox(startGameButton, loadGameButton, rulesButton, exitGameButton);

        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);

        BorderPane menuLayout = new BorderPane();

        menuLayout.setCenter(buttons);
        menuLayout.setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 100), CornerRadii.EMPTY, Insets.EMPTY)));
        setWidthAndHeight(menuLayout);

        Scene scene = new Scene(menuLayout);
        scene.getStylesheets().add("style.css");

        primaryStage.setScene(scene);
        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    public void gameSettings(Stage primaryStage) throws FileNotFoundException {
        startButton.setId("buttons");
        backButton.setId("buttons");

        HBox buttons = new HBox(startButton, backButton);
        buttons.setSpacing(25);
        Text nameLabel = new Text("Please enter Your Nickname:");
        nameLabel.setId("text");

        TextField textField = new TextField();
        textField.setId("input");
        textField.setPrefWidth(100);

        VBox settings = new VBox(nameLabel, textField, buttons);
        settings.setAlignment(Pos.CENTER);

        startButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            try {
                map1 = MapLoader.loadMap("/map1.txt", false);
                map = map1;
                map.getPlayer().setName(textField.getText());
                gameStart(primaryStage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        backButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            try {
                mainMenu(primaryStage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        BorderPane menu = new BorderPane();

        setWidthAndHeight(menu);
        menu.setCenter(settings);

        buttons.setAlignment(Pos.CENTER);
        settings.setSpacing(25);
        Scene scene = new Scene(menu);

        scene.getStylesheets().add("style.css");

        primaryStage.setScene(scene);
        primaryStage.show();
        setStage(primaryStage);
    }

    public static void gameStart(Stage primaryStage) {
        SoundUtils.stopAll();
        Stage saveWindow = new Stage();
        Stage loadWindow = new Stage();
        rightUI = new RightUiPanel(map.getPlayer());
        Movements.start();
        SoundUtils.playContinuously(SoundUtils.BACKGROUND, 0.5f);
        GridPane ui = rightUI;
        rightUI.pickUpButton.setOnAction(mousedown -> {
            map.getPlayer().pickUpItem();
            rightUI.hideButton();
        });
        rightUI.exportButton.setOnAction(mousedown -> {
            try {
                fileSaver.start(saveWindow);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            mousedown.consume();
        });
        rightUI.importButton.setOnAction(mousedown -> {
            try {
                fileLoader.start(loadWindow);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            mousedown.consume();
        });
        BorderPane borderPane = new BorderPane();
        map.getPlayer().setRightUiPanel(rightUI);
        rightUI.getInventory().setPlayer(map.getPlayer());
        borderPane.setCenter(canvas);
        borderPane.setRight(ui);
        map.getPlayer().setInventory(rightUI.getInventory().getItems());

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        Game.refresh();
        scene.setOnKeyPressed(Game::onKeyPressed);
        scene.setOnKeyReleased(Game::onKeyReleased);
        scene.setOnKeyReleased(GameMenu::showSaveGameStage);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
        canvas.setScaleX(1.2);
        canvas.setScaleY(1.2);
        movements = new Movements(map, new Game());
        thread = new Thread(movements);
        thread.start();
        Game.visitedMaps.clear();
        Game.mapNameJSON = "/map1.txt";
        if (!Game.visitedMaps.contains(Game.mapNameJSON)) {
            Game.visitedMaps.add(Game.mapNameJSON);
        }
    }

    public static void showSaveGameStage(KeyEvent keyEvent) {
        KeyCombination combination = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
        if (combination.match(keyEvent)) {
            loadFxmlStage("save_view.fxml", "Save game");
        }
    }

    public static void showLoadGameStage() {
        loadFxmlStage("load_view.fxml", "Load game");
    }

    private static void loadFxmlStage(String fxmlLocation, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SaveController.class.getResource(fxmlLocation));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage1 = new Stage();
            stage1.setTitle(title);
            stage1.setScene(scene);
            stage1.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGameJSON(GameMap map) {
        SoundUtils.stopAll();
        Stage saveWindow = new Stage();
        Stage loadWindow = new Stage();
        rightUI = new RightUiPanel(map.getPlayer());
        Movements.start();
        SoundUtils.playContinuously(SoundUtils.BACKGROUND, 0.5f);
        GridPane ui = rightUI;
        rightUI.pickUpButton.setOnAction(mousedown -> {
            map.getPlayer().pickUpItem();
            rightUI.hideButton();
        });
        rightUI.exportButton.setOnAction(mousedown -> {
            try {
                fileSaver.start(saveWindow);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            mousedown.consume();
        });
        rightUI.importButton.setOnAction(mousedown -> {
            try {
                fileLoader.start(loadWindow);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            mousedown.consume();
        });
        BorderPane borderPane = new BorderPane();
        map.getPlayer().setRightUiPanel(rightUI);
        rightUI.getInventory().setPlayer(map.getPlayer());
        for (int i = 0; i < map.getPlayer().getInventory().size(); i++) {
            Item item = map.getPlayer().getInventory().get(i);
            rightUI.getInventory().add(item);
        }
        borderPane.setCenter(canvas);
        borderPane.setRight(ui);
        map.getPlayer().setInventory(rightUI.getInventory().getItems());

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        Game.refresh();
        scene.setOnKeyPressed(Game::onKeyPressed);
        scene.setOnKeyReleased(Game::onKeyReleased);
        scene.setOnKeyReleased(GameMenu::showSaveGameStage);

        stage.setTitle("Dungeon Crawl");
        stage.show();
        canvas.setScaleX(1.2);
        canvas.setScaleY(1.2);
        movements = new Movements(map, new Game());
        thread = new Thread(movements);
        thread.start();
        Game.visitedMaps.clear();
        Game.mapNameJSON = GameJSONToMap.currentMap;
        if (!Game.visitedMaps.contains(Game.mapNameJSON)) {
            Game.visitedMaps.add(Game.mapNameJSON);
        }
        Game.playerCell = map.getCell(map.getPlayer().getX(), map.getPlayer().getY());
        Game.playerCell.setActor(map.getPlayer());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void gameRules(Stage primaryStage) throws FileNotFoundException {
        startButton.setId("buttons");

        startButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            try {
                gameSettings(primaryStage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        goBack(primaryStage);

        VBox buttons = new VBox(startButton, backButton);

        buttons.setAlignment(Pos.BOTTOM_CENTER);
        buttons.setSpacing(5);

        BorderPane rulesLayout = new BorderPane();

        rulesLayout.setCenter(buttons);
        setWidthAndHeight(rulesLayout);

        Scene scene = new Scene(rulesLayout);
        scene.getStylesheets().add("rules.css");

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showRules(Stage primaryStage) {
        rulesButton.setId("buttons");
        rulesButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            try {
                gameRules(primaryStage);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
    }

    public void loadGameAddListener() {
        loadGameButton.setId("buttons");
        loadGameButton.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> showLoadGameStage());
    }

    public void goBack(Stage primaryStage) {
        backButton.setId("buttons");
        backButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            try {
                map.getPlayer().setHealth(10);
                mainMenu(primaryStage);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
    }

    public static void exitGame(){
        exitGameButton.setId("buttons");
        exitGameButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            System.exit(0);
        });
    }

    public void showEndGameScreen(Stage primaryStage, String css) {
        MapLoader.maps.clear();
        goBack(primaryStage);
        exitGame();

        HBox buttons = new HBox(backButton, exitGameButton);
        VBox menu = new VBox(buttons);

        BorderPane menuLayout = new BorderPane();
        setWidthAndHeight(menuLayout);
        menuLayout.setCenter(menu);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(350, 5, 15, 5));
        buttons.setSpacing(25);

        Scene scene = new Scene(menuLayout);
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void youWon(Stage primaryStage) {
        synchronized (GameMap.class) {
            Movements.stop();
            SoundUtils.stopAll();
            SoundUtils.playSound(SoundUtils.GAME_WON, 0.5f);
            showEndGameScreen(primaryStage, "you-won.css");
        }
    }

    public void gameOver(Stage primaryStage) throws InterruptedException {
        Movements.stop();
        SoundUtils.stopAll();
        showEndGameScreen(primaryStage, "game-over.css");
        synchronized (GameMap.class) {
            SoundUtils.playSound(SoundUtils.GAME_OVER, 1f);
        }
    }
}
