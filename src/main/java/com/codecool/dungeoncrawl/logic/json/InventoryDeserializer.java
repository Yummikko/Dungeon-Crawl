package com.codecool.dungeoncrawl.logic.json;

import com.codecool.dungeoncrawl.model.InventoryModel;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class InventoryDeserializer implements JsonDeserializer {
    private Gson gson;
    private Map<String, Class<? extends InventoryModel>> inventory;

    public InventoryDeserializer() {
        this.gson = new Gson();
        this.inventory = new HashMap<>();
    }

    public InventoryModel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject inventoryObject = jsonElement.getAsJsonObject();
        return gson.fromJson(inventoryObject, InventoryModel.class);
    }
}
