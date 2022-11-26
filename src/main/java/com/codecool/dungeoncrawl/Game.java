package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.graphics.GameCamera;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.DarkLord;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.doors.NormalDoor;
import com.codecool.dungeoncrawl.logic.util.SoundUtils;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public class Game {
    GameDatabaseManager dbManager;
    private static GameMap map = MapLoader.loadMap("/map1.txt");
    private static GameMap map1;
    private GameCamera gameCamera = new GameCamera(0, 0, map);
    private Stage stage;
    private Movements movements;
    private Thread thread;
    private static RightUiPanel rightUI = new RightUiPanel(map.getPlayer(), map);
    Canvas canvas = new Canvas(
            25 * Tiles.TILE_WIDTH,
            21 * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();

    Button startButton = new Button("Start the Game");
    Button backButton = new Button("Back to Menu");
    Button rulesButton = new Button("Show game rules");
    Button exitGameButton = new Button("Exit Game");

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private static void checkIfMonstersHealth(List<Actor> enemies) {
        // loop backwards to avoid ConcurrentModificationException
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Actor enemy = enemies.get(i);
            if (enemy.isDead()) {
                enemies.remove(enemy);
                enemy.getCell().setActor(null);
                if (enemy instanceof DarkLord) {
                    ((DarkLord) enemy).removePhantoms(map);
                }
            }
        }
    }

    public static String getNextMap(List maps) {
        int mapsSize = maps.size();
        String mapName = "";
        switch (mapsSize) {
            case 1 -> mapName = "/map2.txt";
            case 2 -> mapName = "/map3.txt";
        }
        return mapName;
    }
    public static void setMap(){
        MapLoader.maps.add(map);
        List maps = MapLoader.maps;
        String mapName = getNextMap(maps);
        GameMap map2 = MapLoader.loadMap(mapName);
        map = map2;
    }

    public void mainMenu(Stage primaryStage) throws FileNotFoundException, RuntimeException {
        Button startGameButton = new Button("Start the Game");
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
        menuLayout.setPrefWidth(1084);
        menuLayout.setPrefHeight(768);

        Scene scene = new Scene(menuLayout);
        scene.getStylesheets().add("style.css");

        primaryStage.setScene(scene);
        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    public void gameSettings(Stage primaryStage) throws FileNotFoundException {
        Button startButton = new Button("Start the Game");
        Button backButton = new Button("Back to Menu");

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

        menu.setPrefWidth(1084);
        menu.setPrefHeight(768);
        menu.setCenter(settings);

        buttons.setAlignment(Pos.CENTER);
        settings.setSpacing(25);
        Scene scene = new Scene(menu);

        scene.getStylesheets().add("style.css");

        primaryStage.setScene(scene);
        primaryStage.show();
        setStage(primaryStage);
    }

    private void onKeyReleased(KeyEvent keyEvent) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        KeyCombination exitCombinationWin = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);
        if (exitCombinationMac.match(keyEvent)
                || exitCombinationWin.match(keyEvent)
                || keyEvent.getCode() == KeyCode.ESCAPE) {
            System.exit(0);
        }
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case W:
            case UP:
                map.getPlayer().move(Direction.NORTH);
                refresh();
                break;
            case S:
            case DOWN:
                map.getPlayer().move(Direction.SOUTH);
                refresh();
                break;
            case A:
            case LEFT:
                map.getPlayer().move(Direction.WEST);
                refresh();
                break;
            case D:
            case RIGHT:
                map.getPlayer().move(Direction.EAST);
                refresh();
                break;
        }
        checkIfOnItem();
    }

    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }
    public void gameStart(Stage primaryStage) {
        rightUI = new RightUiPanel(map.getPlayer(), map);
        Movements.start();
        setupDbManager();
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
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);
        scene.setOnKeyReleased(this::onKeyReleased);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
        canvas.setScaleX(1.2);
        canvas.setScaleY(1.2);
        movements = new Movements(map, this);
        thread = new Thread(movements);
        thread.start();
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
        rulesLayout.setPrefWidth(1084);
        rulesLayout.setPrefHeight(768);

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
    void showEndGameScreen(Stage primaryStage, String css) {
        MapLoader.maps.clear();
        goBack(primaryStage);
        exitGame();

        HBox buttons = new HBox(backButton, exitGameButton);
        VBox menu = new VBox(buttons);

        BorderPane menuLayout = new BorderPane();
        menuLayout.setPrefWidth(1084);
        menuLayout.setPrefHeight(768);
        menuLayout.setCenter(menu);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(350, 5, 15, 5));
        buttons.setSpacing(25);

        Scene scene = new Scene(menuLayout);
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    void refresh() {
        gameCamera.centerOnPlayer(map.getPlayer(), map);
        moveMonsters();
        checkIfMonstersHealth(map.getEnemies());
        if (!map.getPlayer().isAlive()) {
            try {
                SoundUtils.playSound(SoundUtils.GAME_OVER, 1f);
                gameOver(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        checkForWin(stage);
        float xOffset = gameCamera.getxOffset();
        float yOffset = gameCamera.getyOffset();
        context.setFill(Color.rgb(32, 62, 84));
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), (int) (x - xOffset), (int) (y - yOffset));
                } else if (cell.getDoor() != null) {
                    if (cell.getDoor() instanceof NormalDoor)
                        map.getPlayer().openClosedDoor(cell.getNormalDoor());
                    Tiles.drawTile(context, cell.getDoor(), (int) (x - xOffset), (int) (y - yOffset));
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), (int) (x - xOffset), (int) (y - yOffset));
                } else if (cell.getEnviroment() != null) {
                    Tiles.drawTile(context, cell.getEnviroment(), (int) (x - xOffset), (int) (y - yOffset));
                } else {
                    Tiles.drawTile(context, cell, (int) (x - xOffset), (int) (y - yOffset));
                }
            }
        }
        rightUI.setTextForRightUI(map.getPlayer());
    }
    private void checkIfOnItem() {
        if (map.getPlayer().getCell().getItem() != null) {
            rightUI.showButton();
        } else {
            rightUI.hideButton();
        }
    }


    public void exitGame(){
        exitGameButton.setId("buttons");
        exitGameButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            System.exit(0);
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

    public void youWon(Stage primaryStage) {
        synchronized (GameMap.class) {
            Movements.stop();
            SoundUtils.stopAll();
            SoundUtils.playSound(SoundUtils.GAME_WON, 0.5f);
            showEndGameScreen(primaryStage, "you-won.css");
        }
    }
    public void moveMonsters() {
        List<Actor> enemies = map.getEnemies();
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).move(map);
        }
    }

    public void checkForWin(Stage primaryStage) {
        if (map.getPlayer().inventoryToString().contains("crown")) {
            Movements.stop();
            youWon(primaryStage);
        }
    }

    public void gameOver(Stage primaryStage) {
        synchronized (this) {
            Movements.stop();
            SoundUtils.stopAll();
            SoundUtils.playSound(SoundUtils.GAME_OVER, 1f);
            showEndGameScreen(primaryStage, "game-over.css");
        }
    }

}
