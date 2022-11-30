package com.codecool.dungeoncrawl.logic.json;


import com.codecool.dungeoncrawl.Game;
import com.codecool.dungeoncrawl.graphics.GameMenu;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.model.EnemyModel;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.json.*;

public class GameJSONGenerator {

    public static String fileName;
    public static File writeDataToJson() throws FileNotFoundException {
        /* create models */
        GameState gameState = Game.createNewGameStateJSON();
        PlayerModel playerModel = Game.createPlayerModelJSON();
        List<EnemyModel> enemyModels = Game.createEnemyModelsJSON(GameMenu.map);
        /* create JsonObjects */
        JsonObjectBuilder gameStateBuilder = Json.createObjectBuilder();
        JsonObjectBuilder playerBuilder = Json.createObjectBuilder();
        JsonObjectBuilder enemyBuilder = Json.createObjectBuilder();
        JsonArrayBuilder enemiesBuilder = Json.createArrayBuilder();
        // necessary variables initialization
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date(gameState.getSavedAt());
        String savedTime = sdf.format(now);
        List<Item> inventory;
        if (GameMenu.map.getPlayer().getInventory() != null)
            inventory = GameMenu.map.getPlayer().getInventory();
        else
            inventory = new ArrayList<>();
        /* Building JSON file */
        gameStateBuilder.add("map", gameState.getCurrentMap())
                .add("date", savedTime)
                .add("player", playerModel.getPlayerName())
                .add("discoveredMaps", gameState.getDiscoveredMaps().toString());
        playerBuilder.add("playerName", playerModel.getPlayerName())
                .add("positionX", playerModel.getX())
                .add("positionY", playerModel.getY())
                .add("health", playerModel.getHp())
                .add("inventory", inventory.toString());
        gameStateBuilder.add("player", playerBuilder);
        // create a new json from enemyModels
        for (EnemyModel enemyModel : enemyModels) {
            enemyBuilder.add("enemyName", getEnemyName(enemyModel.toString()))
                    .add("positionX", enemyModel.getX())
                    .add("positionY", enemyModel.getY())
                    .add("health", enemyModel.getHp());
            enemiesBuilder.add(enemyBuilder);
        }
        gameStateBuilder.add("enemiesLeft", enemiesBuilder);
        JsonObject empJsonObject = gameStateBuilder.build();

        File file = new File(empJsonObject.toString());
        System.out.println("GameState JSON String\n"+empJsonObject);
        return file;
    }

    public static String getEnemyName(String name) {
        String enemyName = "";
        if (name.toLowerCase().contains("skeleton"))
            enemyName = "skeleton";
        else if (name.toLowerCase().contains("spider"))
            enemyName = "spider";
        else if (name.toLowerCase().contains("lich"))
            enemyName = "lich";
        else if (name.toLowerCase().contains("octopus"))
            enemyName = "octopus";
        else if (name.toLowerCase().contains("darkLord"))
            enemyName = "darkLord";
        return enemyName;
    }

}
