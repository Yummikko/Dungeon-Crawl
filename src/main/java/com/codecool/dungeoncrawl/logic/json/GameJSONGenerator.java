package com.codecool.dungeoncrawl.logic.json;


import com.codecool.dungeoncrawl.Game;
import com.codecool.dungeoncrawl.graphics.GameMenu;
import com.codecool.dungeoncrawl.logic.doors.OpenDoor;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.json.*;

public class GameJSONGenerator {

    public static File writeDataToJson() throws FileNotFoundException {
        /* create models */
        GameState gameState = Game.createNewGameStateJSON();
        PlayerModel playerModel = Game.createPlayerModelJSON();
        List<EnemyModel> enemyModels = Game.createEnemyModelsJSON(Game.gameMenu.map);
        List<ItemModel> itemModels = Game.createItemModelsJSON(Game.gameMenu.map);
        List<OpenDoorModel> openDoorModels = Game.createOpenDoorModelsJSON(Game.gameMenu.map);
        List<InventoryModel> inventoryModels = Game.createInventoryModelsJSON(Game.gameMenu.map);
        /* create JsonObjects */
        JsonObjectBuilder gameStateBuilder = Json.createObjectBuilder();
        JsonObjectBuilder playerBuilder = Json.createObjectBuilder();
        JsonObjectBuilder enemyBuilder = Json.createObjectBuilder();
        JsonObjectBuilder itemInventoryBuilder = Json.createObjectBuilder();
        JsonArrayBuilder inventoryBuilder = Json.createArrayBuilder();
        JsonArrayBuilder playerDataBuilder = Json.createArrayBuilder();
        JsonArrayBuilder enemiesBuilder = Json.createArrayBuilder();
        JsonObjectBuilder itemBuilder = Json.createObjectBuilder();
        JsonArrayBuilder itemsBuilder = Json.createArrayBuilder();
        JsonObjectBuilder openDoorBuilder = Json.createObjectBuilder();
        JsonArrayBuilder openDoorsBuilder = Json.createArrayBuilder();
        // necessary variables initialization
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date(gameState.getSavedAt());
        String savedTime = sdf.format(now);
        List<Item> inventory;
        List<OpenDoor> openDoors;
        if (GameMenu.map.getPlayer().getInventory() != null)
            inventory = GameMenu.map.getPlayer().getInventory();
        else
            inventory = new ArrayList<>();
        if (GameMenu.map.getOpenDoors() != null)
            openDoors = GameMenu.map.getOpenDoors();
        else
            openDoors = new ArrayList<>();
        /* Building JSON file */
        // create a new json from gamestate and player models
        gameStateBuilder.add("map", gameState.getCurrentMap())
                .add("date", savedTime)
                .add("discoveredMaps", Game.visitedMaps.toString());
        playerBuilder.add("playerName", playerModel.getPlayerName())
                .add("x", playerModel.getX())
                .add("y", playerModel.getY())
                .add("hp", playerModel.getHp())
                .add("strength", playerModel.getStrength());
        for (Item item : inventory) {
            itemInventoryBuilder.add("item", getItemTrueName(item.toString()));
            inventoryBuilder.add(itemInventoryBuilder);
        }
        for (OpenDoor openDoor : openDoors) {
            openDoorBuilder.add("x", openDoor.getCell().getX())
                    .add("y", openDoor.getCell().getY());
            openDoorsBuilder.add(openDoorBuilder);
        }
        playerDataBuilder.add(playerBuilder);
        gameStateBuilder.add("player", playerDataBuilder);
        gameStateBuilder.add("inventory", inventoryBuilder);
        gameStateBuilder.add("openDoors", openDoorsBuilder);
        // create a new json from enemyModels
        for (EnemyModel enemyModel : enemyModels) {
            enemyBuilder.add("enemyName", getEnemyName(enemyModel.toString()))
                    .add("x", enemyModel.getX())
                    .add("y", enemyModel.getY())
                    .add("hp", enemyModel.getHp());
            enemiesBuilder.add(enemyBuilder);
        }
        gameStateBuilder.add("enemiesLeft", enemiesBuilder);
        // create json from itemModels
        for (ItemModel itemModel : itemModels) {
            itemBuilder.add("itemName", getItemTrueName(itemModel.getItemName()))
                    .add("x", itemModel.getX())
                    .add("y", itemModel.getY());
            itemsBuilder.add(itemBuilder);
        }
        gameStateBuilder.add("itemsLeft", itemsBuilder);
        // Build the JSON
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
        else if (name.toLowerCase().contains("darklord"))
            enemyName = "darkLord";
        return enemyName;
    }

    public static String getItemTrueName(String name) {
        String itemName = "";
        if (name.toLowerCase().contains("weapon"))
            itemName = "weapon";
        else if (name.toLowerCase().contains("axe"))
            itemName = "axe";
        else if (name.toLowerCase().contains("key"))
            itemName = "key";
        else if (name.toLowerCase().contains("poison"))
            itemName = "poison";
        else if (name.toLowerCase().contains("shield"))
            itemName = "shield";
        else if (name.toLowerCase().contains("crown"))
            itemName = "crown";
        else if (name.toLowerCase().contains("food"))
            itemName = "food";
        return itemName;
    }

}
