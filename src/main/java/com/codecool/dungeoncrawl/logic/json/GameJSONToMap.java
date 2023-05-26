package com.codecool.dungeoncrawl.logic.json;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

import com.codecool.dungeoncrawl.Game;
import com.codecool.dungeoncrawl.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GameJSONToMap {
    public static String currentMap;
    private String maps;
    static List<ItemModel> itemsOnMap = new ArrayList<>();
    static List<EnemyModel> enemiesList = new ArrayList<>();
    static List<PlayerModel> playerData = new ArrayList<>();
    static List<OpenDoorModel> openedDoors = new ArrayList<>();
    static List<InventoryModel> inventoryPlayer = new ArrayList<>();

    public void parseJsonToMap(String filePath) {
        clearData();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String jsonString;
        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(filePath)) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            currentMap = (String) jsonObject.get("map");

            maps = (String) jsonObject.get("discoveredMaps");

            // Player
            PlayerModel playerModel;
            Iterator<Object> playerStats = getDataFromJSONArray(jsonObject, "player");
            while (playerStats.hasNext()) {
                jsonString = playerStats.next().toString();
                playerModel = gson.fromJson(jsonString, PlayerModel.class);
                playerData.add(playerModel);
            }
            //Enemies
            EnemyModel enemyModel;
            Iterator<Object> enemies = getDataFromJSONArray(jsonObject, "enemiesLeft");
            while (enemies.hasNext()) {
                jsonString = enemies.next().toString();
                enemyModel = gson.fromJson(jsonString, EnemyModel.class);
                enemiesList.add(enemyModel);
            }
            //Items
            ItemModel itemModel;
            Iterator<Object> items = getDataFromJSONArray(jsonObject, "itemsLeft");
            while (items.hasNext()) {
                jsonString = items.next().toString();
                itemModel = gson.fromJson(jsonString, ItemModel.class);
                itemsOnMap.add(itemModel);
            }
            //Open Doors
            OpenDoorModel openDoorModel;
            Iterator<Object> openDoors = getDataFromJSONArray(jsonObject, "openDoors");
            while (openDoors.hasNext()) {
                jsonString = openDoors.next().toString();
                openDoorModel = gson.fromJson(jsonString, OpenDoorModel.class);
                openedDoors.add(openDoorModel);
            }
            //Inventory
            InventoryModel inventoryModel;
            Iterator<Object> itemsInInventory = getDataFromJSONArray(jsonObject, "inventory");
            while (itemsInInventory.hasNext()) {
                jsonString = itemsInInventory.next().toString();
                inventoryModel = gson.fromJson(jsonString, InventoryModel.class);
                inventoryPlayer.add(inventoryModel);
            }
            Game.loadFromJson(currentMap.substring(1));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public static Iterator<Object> getDataFromJSONArray(JSONObject jsonObject, String objName) {
        JSONArray data = (JSONArray) jsonObject.get(objName);
        Iterator<Object> iterator = data.iterator();
        return iterator;
    }
    public void clearData() {
        currentMap = null;
        maps = null;
        itemsOnMap.clear();
        inventoryPlayer.clear();
        enemiesList.clear();
        playerData.clear();
        openedDoors.clear();
    }
}
