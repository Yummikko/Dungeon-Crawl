package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Lich;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.doors.NormalDoor;
import com.codecool.dungeoncrawl.logic.doors.OpenDoor;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.sound.sampled.Line;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main extends Application {
    static GameMap map = MapLoader.loadMap("/map1.txt");
    public boolean gameOver = false;
    public final List<Skeleton> skeletons = new ArrayList<>();
    public final List<Lich> lichs = new ArrayList<>();
    GameMap map1;
//    GameMap map = MapLoader.loadMap("/map1.txt");
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label nameLabel = new Label();
    Label healthLabel = new Label();
    Label strengthLabel = new Label();
    Button pickUpButton = new Button("Pick up item");
    Label playerInventory = new Label("INVENTORY: ");


    Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    public void hideButton() {
        pickUpButton.setVisible(false);
    }

    public void showButton() {
        pickUpButton.setVisible(true);
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
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });

        BorderPane menu = new BorderPane();

        //menu.setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 100), CornerRadii.EMPTY, Insets.EMPTY)));
        menu.setPrefWidth(1084);
        menu.setPrefHeight(768);
        menu.setCenter(settings);

        buttons.setAlignment(Pos.CENTER);
        settings.setSpacing(25);
        Scene scene = new Scene(menu);

        scene.getStylesheets().add("style.css");

        primaryStage.setScene(scene);
        //primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    public void mainMenu(Stage primaryStage) throws FileNotFoundException, RuntimeException {
        Button startGameButton = new Button("Start new game");
        Button exitGameButton = new Button("Exit Game");

        startGameButton.setId("buttons");
        exitGameButton.setId("buttons");

        startGameButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            try {
                gameSettings(primaryStage);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
        exitGameButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            System.exit(0);
        });

        VBox buttons = new VBox(startGameButton, exitGameButton);

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


    public void gameStart(Stage primaryStage) throws Exception {
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

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }


    // And From your main() method or any other method

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case W:
            case UP:
                map.getPlayer().move(0, -1);
                Skeleton.monsterMove(skeletons, map);
                Lich.magicMovement(lichs, map, map.getPlayer());
                Actor.checkIfMonstersHealth(skeletons, lichs);
                refresh();
                break;
            case S:
            case DOWN:
                map.getPlayer().move(0, 1);
                Skeleton.monsterMove(skeletons, map);
                Lich.magicMovement(lichs, map, map.getPlayer());
                Actor.checkIfMonstersHealth(skeletons, lichs);
                refresh();
                break;
            case A:
            case LEFT:
                map.getPlayer().move(-1, 0);
                Skeleton.monsterMove(skeletons, map);
                Lich.magicMovement(lichs, map, map.getPlayer());
                Actor.checkIfMonstersHealth(skeletons, lichs);
                refresh();
                break;
            case D:
            case RIGHT:
                map.getPlayer().move(1, 0);
                Skeleton.monsterMove(skeletons, map);
                Lich.magicMovement(lichs, map, map.getPlayer());
                Actor.checkIfMonstersHealth(skeletons, lichs);
                refresh();
                break;
        }
    }

    private void checkIfOnItem() {
        if (map.getPlayer().getCell().getItem() != null) {
            showButton();
        } else {
            hideButton();
        }
    }

    public void gameOver(Stage primaryStage) throws Exception {

        Button backToMenu = new Button("Back to Menu");
        Button exitGameButton = new Button("Exit Game");


        backToMenu.setId("buttons");
        exitGameButton.setId("buttons");

        backToMenu.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            try {
                map.getPlayer().setHealth(10);
                mainMenu(primaryStage);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
        exitGameButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {
            System.exit(0);
        });


        HBox buttons = new HBox(backToMenu, exitGameButton);

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
        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
        skeletons.clear();
        lichs.clear();
        map.getSkeletons().clear();
        map.getLichs().clear();
    }


    private void refresh() {

        if(map.getPlayer().getHealth() <= 0 ) {
            try {
                SoundUtils.playSound(SoundUtils.GAME_OVER, 1f);
                gameOver(stage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        checkIfOnItem();
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    if (cell.getSkeleton() != null)
                        if (skeletons.size() < map.getSkeletons().size())
                            skeletons.add(cell.getSkeleton());
                    if (cell.getLich() != null)
                        if (lichs.size() < map.getLichs().size())
                            lichs.add(cell.getLich());
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.getDoor() != null) {
                    if (cell.getDoor() instanceof NormalDoor)
                        map.getPlayer().openClosedDoor(cell.getNormalDoor());
                    Tiles.drawTile(context, cell.getDoor(), x, y);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else if (cell.getEnviroment() != null) {
                    Tiles.drawTile(context, cell.getEnviroment(), x, y);
                }
                else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        nameLabel.setText("" + map.getPlayer().getName());
        healthLabel.setText("" + map.getPlayer().getHealth());
        strengthLabel.setText("" + map.getPlayer().getStrength());
        playerInventory.setText("" + map.getPlayer().inventoryToString());
    }

    public static void setMap(GameMap map){
        Main.map = map;
    }

}
