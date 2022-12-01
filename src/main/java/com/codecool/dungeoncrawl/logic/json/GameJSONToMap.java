package com.codecool.dungeoncrawl.logic.json;

import java.io.File;    // for reading file data
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GameJSONToMap {
    public static void parseJsonToMap(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filePath);
        System.out.println(file);
    }
}
