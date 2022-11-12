package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.doors.NormalDoor;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {
    public boolean gameOver = false;
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label strengthLabel = new Label();
    Button pickUpButton = new Button("Pick up item");
    Label playerInventory = new Label("INVENTORY: ");
    private List<Skeleton> skeletons = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    public void hideButton() {
        pickUpButton.setVisible(false);
    }

    public void showButton() {
        pickUpButton.setVisible(true);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);
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

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case W:
            case UP:
                map.getPlayer().move(0, -1);
                monsterMove();
                refresh();
                break;
            case S:
            case DOWN:
                map.getPlayer().move(0, 1);
                monsterMove();
                refresh();
                break;
            case A:
            case LEFT:
                map.getPlayer().move(-1, 0);
                monsterMove();
                refresh();
                break;
            case D:
            case RIGHT:
                map.getPlayer().move(1, 0);
                monsterMove();
                refresh();
                break;
        }
    }

    private void monsterMove() {
        Random rand = new Random();
        int min = 0;
        int max = 4;
        for (int i = 0; i < skeletons.size(); i++) {
            int randomPos = rand.nextInt(max - min) + min;
            Skeleton a = skeletons.get(i);
            map.setSkeleton(a);
            if (randomPos == 1) {
                System.out.println(map.getSkeleton());
                map.getSkeleton().move(0, 1);
            } else if (randomPos == 2) {
                map.getSkeleton().move(0, -1);
            } else if (randomPos == 3) {
                map.getSkeleton().move(1, 0);
            } else {
                map.getSkeleton().move(-1, 0);
            }
        }
    }

    private void openClosedDoor(NormalDoor door) {
        ArrayList<Item> inventory = map.getPlayer().getInventory();
        for (Item item : inventory) {
            if (item instanceof Key) {
                System.out.println("The Key is inside inventory!");
                if (!door.getIsOpen())
                    door.setIsOpen();
            }
        }
        if (door.getIsOpen())
            System.out.println("Player can enter through the door.");
    }

    private void checkIfOnItem() {
        if (map.getPlayer().getCell().getItem() != null) {
            showButton();
        } else {
            hideButton();
        }
    }

    private void refresh() {
        checkIfOnItem();
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    if (cell.getSkeleton() != null)
                        if (skeletons.size() <= 3)
                            skeletons.add(cell.getSkeleton());
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.getDoor() != null) {
                    if (cell.getDoor() instanceof NormalDoor)
                        openClosedDoor(cell.getNormalDoor());
                    Tiles.drawTile(context, cell.getDoor(), x, y);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else if (cell.getEnviroment() != null) {
                    Tiles.drawTile(context, cell.getEnviroment(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
        strengthLabel.setText("" + map.getPlayer().getStrength());
        playerInventory.setText("" + map.getPlayer().inventoryToString());
    }
}
