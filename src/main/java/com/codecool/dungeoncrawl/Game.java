package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.graphics.GameCamera;
import com.codecool.dungeoncrawl.graphics.GameMenu;
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
    private static GameDatabaseManager dbManager;
    private static final GameMenu gameMenu = new GameMenu();
    static GraphicsContext context = GameMenu.canvas.getGraphicsContext2D();

    public Game() {

    }
    private static void checkIfMonstersHealth(List<Actor> enemies) {
        // loop backwards to avoid ConcurrentModificationException
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Actor enemy = enemies.get(i);
            if (enemy.isDead()) {
                enemies.remove(enemy);
                enemy.getCell().setActor(null);
                if (enemy instanceof DarkLord) {
                    ((DarkLord) enemy).removePhantoms(gameMenu.map);
                }
            }
        }
    }

    public static String getNextMap(List maps) {
        int mapsSize = maps.size();
        String mapName = "";

        if (mapsSize % 2 != 0) {
            mapName = "/map2.txt";
        } else {
            mapName = "/map3.txt";
        }

        return mapName;
    }

    public static String getPreviousMap(List maps) {
        int mapsSize = maps.size();
        String mapName = "";

        if (mapsSize % 2 != 0) {
            mapName = "/map2.txt";
        } else {
            mapName = "/map1.txt";
        }

        return mapName;
    }

    public static void setMap(){
        MapLoader.maps.add(gameMenu.map);
        List maps = MapLoader.maps;
        String mapName = getNextMap(maps);
        GameMap map2 = MapLoader.loadMap(mapName);
        gameMenu.map = map2;
    }

    public static void setPreviousMap(){
        MapLoader.maps.add(gameMenu.map);
        List maps = MapLoader.maps;
        String mapName = getPreviousMap(maps);
        GameMap map2 = MapLoader.loadMap(mapName);
        gameMenu.map = map2;
    }


    public static void onKeyReleased(KeyEvent keyEvent) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        KeyCombination exitCombinationWin = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);
        if (exitCombinationMac.match(keyEvent)
                || exitCombinationWin.match(keyEvent)
                || keyEvent.getCode() == KeyCode.ESCAPE) {
            System.exit(0);
        }
    }

    public static void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case W:
            case UP:
                gameMenu.map.getPlayer().move(Direction.NORTH);
                refresh();
                break;
            case S:
            case DOWN:
                gameMenu.map.getPlayer().move(Direction.SOUTH);
                refresh();
                break;
            case A:
            case LEFT:
                gameMenu.map.getPlayer().move(Direction.WEST);
                refresh();
                break;
            case D:
            case RIGHT:
                gameMenu.map.getPlayer().move(Direction.EAST);
                refresh();
                break;
        }
        checkIfOnItem();
    }

    public static void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }


    public static void refresh() {
        GameMenu.gameCamera.centerOnPlayer(gameMenu.map.getPlayer(), gameMenu.map);
        moveMonsters();
        checkIfMonstersHealth(gameMenu.map.getEnemies());
        if (!gameMenu.map.getPlayer().isAlive()) {
            try {
                gameMenu.gameOver(gameMenu.stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        checkForWin(gameMenu.stage);
        float xOffset = GameMenu.gameCamera.getxOffset();
        float yOffset = GameMenu.gameCamera.getyOffset();
        context.setFill(Color.rgb(32, 62, 84));
        context.fillRect(0, 0, GameMenu.canvas.getWidth(), GameMenu.canvas.getHeight());
        for (int x = 0; x < gameMenu.map.getWidth(); x++) {
            for (int y = 0; y < gameMenu.map.getHeight(); y++) {
                Cell cell = gameMenu.map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), (int) (x - xOffset), (int) (y - yOffset));
                } else if (cell.getDoor() != null) {
                    if (cell.getDoor() instanceof NormalDoor)
                        gameMenu.map.getPlayer().openClosedDoor(cell.getNormalDoor());
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
        GameMenu.rightUI.setTextForRightUI(gameMenu.map.getPlayer());
        GameMenu.map.getPlayer().setRightUiPanel(GameMenu.rightUI);
    }

    private static void checkIfOnItem() {
        if (gameMenu.map.getPlayer().getCell().getItem() != null) {
            GameMenu.rightUI.showButton();
        } else {
            GameMenu.rightUI.hideButton();
        }
    }

    public static void moveMonsters() {
        List<Actor> enemies = gameMenu.map.getEnemies();
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).move(gameMenu.map);
        }
    }

    public static void checkForWin(Stage primaryStage) {
        if (gameMenu.map.getPlayer().inventoryToString().contains("crown")) {
            Movements.stop();
            gameMenu.youWon(primaryStage);
        }
    }

}
