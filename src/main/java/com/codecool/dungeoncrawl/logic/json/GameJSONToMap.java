package com.codecool.dungeoncrawl.logic.json;

import java.io.File;    // for reading file data
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GameJSONToMap {
    public static void parseJsonToMap(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filePath);
        System.out.println(file);
        try {
            // read JSON data from file using fileObj and map it using ObjectMapper and TypeReference classes
            Map<String, Object> gameData = mapper.readValue(
                    file, new TypeReference<Map<String, Object>>() {
                    });
            System.out.println("Map: " + gameData.get("map"));
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
