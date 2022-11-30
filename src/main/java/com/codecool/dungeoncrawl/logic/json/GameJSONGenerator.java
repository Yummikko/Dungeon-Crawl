package com.codecool.dungeoncrawl.logic.json;


import com.codecool.dungeoncrawl.Game;
import com.codecool.dungeoncrawl.graphics.GameMenu;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Date;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonWriter;

public class GameJSONGenerator {

    public static String fileName;
    public static File writeDataToJson() throws FileNotFoundException {

        GameState gameState = Game.createNewGameStateJSON();
        PlayerModel playerModel = Game.createPlayerModelJSON();
        JsonObjectBuilder gameStateBuilder = Json.createObjectBuilder();
        JsonObjectBuilder playerBuilder = Json.createObjectBuilder();
        Date date = new Date(gameState.getSavedAt());
        gameStateBuilder.add("map", gameState.getCurrentMap())
                .add("date", date.toString())
                .add("player", playerModel.getPlayerName());

        playerBuilder.add("playerName", playerModel.getPlayerName())
                .add("positionX", playerModel.getX())
                .add("positionY", playerModel.getY())
                .add("health", playerModel.getHp());

        gameStateBuilder.add("player", playerBuilder);

        JsonObject empJsonObject = gameStateBuilder.build();

        File file = new File(empJsonObject.toString());
        System.out.println("GameState JSON String\n"+empJsonObject);
        return file;
    }


    public static GameState createGameState(String map, long saveAt, PlayerModel player) {
        GameState gameState = new GameState(map, saveAt, player);

        return gameState;
    }

    public static PlayerModel createPlayer(Player player) {
        PlayerModel playerModel = new PlayerModel(player);
        return playerModel;
    }
}
