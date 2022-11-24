package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.graphics.GameCamera;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.DarkLord;
import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.doors.NormalDoor;
import com.codecool.dungeoncrawl.logic.util.SoundUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

public class Game {
    private static GameMap map;
    private GameCamera gameCamera;
    private Stage stage;
    private Main main = new Main();
    Canvas canvas = new Canvas(
            25 * Tiles.TILE_WIDTH,
            21 * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();

    Label nameLabel = new Label();
    Label healthLabel = new Label();
    Label strengthLabel = new Label();
    Label playerInventory = new Label("INVENTORY: ");
    Button pickUpButton = new Button("Pick up item");

    public void hideButton() {
        pickUpButton.setVisible(false);
    }

    public void showButton() {
        pickUpButton.setVisible(true);
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
    private void checkIfOnItem() {
        if (map.getPlayer().getCell().getItem() != null) {
            showButton();
        } else {
            hideButton();
        }
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
    public void youWon(Stage primaryStage) {
        SoundUtils.stopAll();
        SoundUtils.playSound(SoundUtils.GAME_WON, 0.6f);
        main.showEndGameScreen(primaryStage, "you-won.css");
    }
    public void moveMonsters() {
        List<Actor> enemies = map.getEnemies();
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).move(map);
        }
    }

    public void checkForWin(Stage primaryStage) {
        if (map.getPlayer().inventoryToString().contains("crown")) {
            youWon(primaryStage);
        }
    }

    public void gameOver(Stage primaryStage) {
        SoundUtils.stopAll();
        SoundUtils.playSound(SoundUtils.GAME_OVER, 1f);
        main.showEndGameScreen(primaryStage, "game-over.css");
    }
}
