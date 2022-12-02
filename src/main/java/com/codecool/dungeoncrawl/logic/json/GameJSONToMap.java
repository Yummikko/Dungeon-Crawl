package com.codecool.dungeoncrawl.logic.json;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

import com.codecool.dungeoncrawl.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GameJSONToMap {
    private String currentMap;
    private String maps;
    List<ItemModel> itemsOnMap = new ArrayList<>();
    List<EnemyModel> enemiesList = new ArrayList<>();
    List<PlayerModel> playerData = new ArrayList<>();
    List<OpenDoorModel> openedDoors = new ArrayList<>();
    List<InventoryModel> inventoryPlayer = new ArrayList<>();

    public void parseJsonToMap(String filePath) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String jsonString;
        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(filePath)) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            System.out.println(jsonObject);

            currentMap = (String) jsonObject.get("map");
            System.out.println(currentMap);

            maps = (String) jsonObject.get("discoveredMaps");
            System.out.println(maps);

//            ArrayList<InventoryModel> inventory = (ArrayList<InventoryModel>) jsonObject.get("inventory");
//            System.out.println(inventory);

            // Player
            PlayerModel playerModel;
            Iterator<Object> playerStats = getDataFromJSONArray(jsonObject, "player");
            while (playerStats.hasNext()) {
                jsonString = playerStats.next().toString();
                playerModel = gson.fromJson(jsonString, PlayerModel.class);
                playerData.add(playerModel);
            }
            System.out.println(playerData);
            //Enemies
            EnemyModel enemyModel;
            Iterator<Object> enemies = getDataFromJSONArray(jsonObject, "enemiesLeft");
            while (enemies.hasNext()) {
                jsonString = enemies.next().toString();
                enemyModel = gson.fromJson(jsonString, EnemyModel.class);
                System.out.println(enemyModel.getClass());
                enemiesList.add(enemyModel);
            }
            System.out.println(enemiesList);
            //Items
            ItemModel itemModel;
            Iterator<Object> items = getDataFromJSONArray(jsonObject, "itemsLeft");
            while (items.hasNext()) {
                jsonString = items.next().toString();
                itemModel = gson.fromJson(jsonString, ItemModel.class);
                itemsOnMap.add(itemModel);
            }
            System.out.println(itemsOnMap);
            //Open Doors
            OpenDoorModel openDoorModel;
            Iterator<Object> openDoors = getDataFromJSONArray(jsonObject, "openDoors");
            while (openDoors.hasNext()) {
                jsonString = openDoors.next().toString();
                openDoorModel = gson.fromJson(jsonString, OpenDoorModel.class);
                openedDoors.add(openDoorModel);
            }
            System.out.println(openedDoors);
            //Inventory
            InventoryModel inventoryModel;
            Iterator<Object> itemsInInventory = getDataFromJSONArray(jsonObject, "inventory");
            while (itemsInInventory.hasNext()) {
                jsonString = itemsInInventory.next().toString();
                inventoryModel = gson.fromJson(jsonString, InventoryModel.class);
                inventoryPlayer.add(inventoryModel);
            }
            System.out.println(inventoryPlayer);
            //TODO: remember to assign new MapLoaders' variables to globally declared variables here
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

}
