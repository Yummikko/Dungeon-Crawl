package com.codecool.dungeoncrawl.logic.json;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GameJSONToMap {
    public void parseJsonToMap(String filePath) {
        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(filePath)) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            System.out.println(jsonObject);

            String map = (String) jsonObject.get("map");
            System.out.println(map);

            String maps = (String) jsonObject.get("discoveredMaps");
            System.out.println(maps);

            // loop array
            JSONArray playerData = (JSONArray) jsonObject.get("player");
            Iterator<Object> iterator = playerData.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }

            Iterator<Object> enemies = getDataFromJSONArray(jsonObject, "enemiesLeft");
            while (enemies.hasNext()) {
                System.out.println(enemies.next());
            }
            /* create 2d array that has same structure as json file
            hold all necessary data there
            think about how to load state of the previous map as well*/
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
