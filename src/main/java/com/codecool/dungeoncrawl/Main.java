package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.graphics.GameCamera;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.doors.NormalDoor;
import com.codecool.dungeoncrawl.logic.util.SoundUtils;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import com.codecool.dungeoncrawl.logic.actors.DarkLord;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class Main extends Application {
    GameDatabaseManager dbManager;

    public static GameMap map = MapLoader.loadMap("/map1.txt");
    Stage stage;
    static GameMap map1;
    static GameCamera gameCamera = new GameCamera(0, 0, map);

    Canvas canvas = new Canvas(
            25 * Tiles.TILE_WIDTH,
            21 * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label nameLabel = new Label();
    Label healthLabel = new Label();
    Label strengthLabel = new Label();
    Label playerInventory = new Label("INVENTORY: ");
    Button pickUpButton = new Button("Pick up item");
    Button startButton = new Button("Start the Game");
    Button backButton = new Button("Back to Menu");
    Button rulesButton = new Button("Show game rules");
    Button exitGameButton = new Button("Exit Game");


    public static void main(String[] args) { launch(args); }

    public void hideButton() { pickUpButton.setVisible(false); }

    public void showButton() { pickUpButton.setVisible(true); }


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


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        mainMenu(primaryStage);
    }

    public void gameStart(Stage primaryStage) {
        setupDbManager();
        SoundUtils.playContinuously(SoundUtils.BACKGROUND, 0.5f);
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));
        ui.setStyle("-fx-background-color: #6C8D9E; -fx-font-size: 18px; -fx-text-fill: #6B8D9E;");

        ui.add(new Label("Name: "),0, 0 );
        ui.add(nameLabel, 1, 0);
        ui.add(new Label("Health: "), 0, 1);
        ui.add(healthLabel, 1, 1);
        ui.add(new Label("Strength: "), 0, 2);
        ui.add(strengthLabel, 1, 2);

        ui.add(pickUpButton, 0, 5);
        pickUpButton.setOnAction(mousedown -> {
            map.getPlayer().pickUpItem();
            refresh();
        });
        pickUpButton.setFocusTraversable(false);
        ui.add(new Label("INVENTORY:"), 0, 7);
        ui.add(playerInventory, 0, 8);

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
    }

    
    private void onKeyReleased(KeyEvent keyEvent) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        KeyCombination exitCombinationWin = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);
        if (exitCombinationMac.match(keyEvent)
                || exitCombinationWin.match(keyEvent)
                || keyEvent.getCode() == KeyCode.ESCAPE) {
            exit();
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
    }
    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }
    private void checkIfOnItem() {
        if (map.getPlayer().getCell().getItem() != null) {
            showButton();
        } else {
            hideButton();
        }
    }

    public void gameOver(Stage primaryStage) {
        SoundUtils.stopAll();
        SoundUtils.playSound(SoundUtils.GAME_OVER, 1f);

        goBack(primaryStage);
        exitGame();


        HBox buttons = new HBox(backButton, exitGameButton);
        VBox menu = new VBox(buttons);

        BorderPane menuLayout = new BorderPane();
        menuLayout.setPrefWidth(1084);
        menuLayout.setPrefHeight(768);
        menuLayout.setCenter(menu);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(350,5,15,5));
        buttons.setSpacing(25);

        Scene scene = new Scene(menuLayout);
        scene.getStylesheets().add("game-over.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void youWon(Stage primaryStage) {
        SoundUtils.stopAll();
        SoundUtils.playSound(SoundUtils.GAME_WON, 0.6f);
        showEndGameScreen(primaryStage, "you-won.css");
    }

    private void showEndGameScreen(Stage primaryStage, String css) {
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

    private void refresh() {
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

        checkIfOnItem();
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
        nameLabel.setText("" + map.getPlayer().getName());
        healthLabel.setText("" + map.getPlayer().getHealth());
        strengthLabel.setText("" + map.getPlayer().getStrength());
        playerInventory.setText("" + map.getPlayer().inventoryToString());
    }

    private static void checkIfMonstersHealth(List<Enemy> enemies) {
        // loop backwards to avoid ConcurrentModificationException
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            if (enemy.isDead()) {
                enemies.remove(enemy);
                enemy.getCell().setActor(null);
                if (enemy instanceof DarkLord) {
                    ((DarkLord) enemy).removePhantoms(map);
                }
            }
        }
    }

    public void checkForWin(Stage primaryStage) {
        if (map.getPlayer().inventoryToString().contains("crown")) {
            youWon(primaryStage);
        }
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

    public void exitGame(){
        exitGameButton.setId("buttons");
        exitGameButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            System.exit(0);
        });
    }

    public void moveMonsters() {
        List<Enemy> enemies = map.getEnemies();
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).move(map);
        }
    }
    public static String getNextMap(List maps) {
        int mapsSize = maps.size();
        String mapName = "";
        switch (mapsSize) {
            case 1:
                mapName = "/map2.txt";
                break;
            case 2:
                mapName = "/map3.txt";
                break;
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

    private void exit() {
        try {
            stop();
        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
    }

}
