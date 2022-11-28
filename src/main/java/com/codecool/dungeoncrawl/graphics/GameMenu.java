package com.codecool.dungeoncrawl.graphics;

import com.codecool.dungeoncrawl.Game;
import com.codecool.dungeoncrawl.Movements;
import com.codecool.dungeoncrawl.RightUiPanel;
import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.util.SoundUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class GameMenu {
    public static GameMap map = MapLoader.loadMap("/map1.txt");
    public static GameCamera gameCamera = new GameCamera(0, 0, map);
    private static GameMap map1;
    public static Stage stage;
    public static Button startButton = new Button("Start the Game");
    public static Button backButton = new Button("Back to Menu");
    public static Button rulesButton = new Button("Show game rules");
    public static Button exitGameButton = new Button("Exit Game");
    private static Movements movements;
    private static Thread thread;
    public static RightUiPanel rightUI = new RightUiPanel(GameMenu.map.getPlayer(), GameMenu.map);
    public static Canvas canvas = new Canvas(
            25 * Tiles.TILE_WIDTH,
            21 * Tiles.TILE_WIDTH);

    public static void setWidthAndHeight(BorderPane menu) {
        menu.setPrefWidth(1084);
        menu.setPrefHeight(768);
    }

    public void mainMenu(Stage primaryStage) throws FileNotFoundException, RuntimeException {
        Button startGameButton = new Button("Start the Game");
        startGameButton.setId("buttons");
        startGameButton.setId("buttons");

        startGameButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            try {
                gameSettings(primaryStage);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });

        showRules(primaryStage);
        exitGame();

        VBox buttons = new VBox(startGameButton, rulesButton, exitGameButton);

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
                map1 = MapLoader.loadMap("/map1.txt");
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
        rightUI = new RightUiPanel(map.getPlayer(), map);
        Movements.start();
        Game.setupDbManager();
        SoundUtils.playContinuously(SoundUtils.BACKGROUND, 0.5f);
        GridPane ui = rightUI;
        rightUI.pickUpButton.setOnAction(mousedown -> {
            map.getPlayer().pickUpItem();
            rightUI.hideButton();;
        });
        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        Game.refresh();
        scene.setOnKeyPressed(Game::onKeyPressed);
        scene.setOnKeyReleased(Game::onKeyReleased);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
        canvas.setScaleX(1.2);
        canvas.setScaleY(1.2);
        movements = new Movements(map, new Game());
        thread = new Thread(movements);
        thread.start();
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
