package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.graphics.GameMenu;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.DarkLord;
import com.codecool.dungeoncrawl.logic.doors.NormalDoor;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.model.EnemyModel;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.ItemModel;
import com.codecool.dungeoncrawl.model.PlayerModel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final GameMenu gameMenu = new GameMenu();
    public static String mapNameJSON = "map1";
    public static List<String> visitedMaps = new ArrayList<>();
    public static GameState gameState = new GameState(mapNameJSON, System.currentTimeMillis(), createPlayerModelJSON());
    private static GraphicsContext context = GameMenu.canvas.getGraphicsContext2D();


    public static GameState createNewGameStateJSON() {
        gameState = new GameState(mapNameJSON, System.currentTimeMillis(), createPlayerModelJSON());
        return gameState;
    }

    public static PlayerModel createPlayerModelJSON() {
        PlayerModel playerModel = new PlayerModel(GameMenu.map.getPlayer());
        return playerModel;
    }

    public static List<EnemyModel> createEnemyModelsJSON(GameMap map) {
        List<EnemyModel> enemiesOnMap = new ArrayList<>();
        for (Actor enemy : map.getEnemies()) {
            EnemyModel enemyModel = new EnemyModel(enemy);
            enemiesOnMap.add(enemyModel);
        }
        return enemiesOnMap;
    }

    public static List<ItemModel> createItemModelsJSON(GameMap map) {
        List<ItemModel> itemsOnMap = new ArrayList<>();
        for (Item item : map.getItems()) {
            ItemModel itemModel  = new ItemModel(item.getCell().getItem().toString(), item.getX(), item.getY());
            itemsOnMap.add(itemModel);
        }
        return itemsOnMap;
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

    public static String getBonusMap(){
        return "/bonus_map.txt";
    }

    public static void setMap() {
        MapLoader.maps.add(GameMenu.map);
        List<GameMap> maps = MapLoader.maps;
        String mapName = getNextMap(maps);
        mapNameJSON = mapName;

        if (!visitedMaps.contains(mapNameJSON)) {
            visitedMaps.add(mapNameJSON);
        }
        GameMenu.map = MapLoader.loadMap(mapName, false);
    }

    public static void setPreviousMap() {
        MapLoader.maps.add(GameMenu.map);
        List maps = MapLoader.maps;
        String mapName = getPreviousMap(maps);
        mapNameJSON = mapName;
        GameMap map2 = MapLoader.loadMap(mapName, true);
        gameMenu.map = map2;
    }

    public static void setBonusMap() {
        MapLoader.maps.add(GameMenu.map);
        String mapName = getBonusMap();
        mapNameJSON = mapName;
        if (!visitedMaps.contains(mapNameJSON)) {
            visitedMaps.add(mapNameJSON);
        }
        GameMap bonusMap = MapLoader.loadMap(mapName, false);
        gameMenu.map = bonusMap;
    }

    public static void onKeyReleased(KeyEvent keyEvent) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.Q, KeyCombination.SHORTCUT_DOWN);
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
