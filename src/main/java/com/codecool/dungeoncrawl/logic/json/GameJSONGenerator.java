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
import java.text.SimpleDateFormat;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;

public class GameJSONGenerator {

    public static String fileName;
    public static File writeDataToJson() throws FileNotFoundException {

        GameState gameState = Game.createNewGameStateJSON();
        PlayerModel playerModel = Game.createPlayerModelJSON();
        JsonObjectBuilder gameStateBuilder = Json.createObjectBuilder();
        JsonObjectBuilder playerBuilder = Json.createObjectBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date(gameState.getSavedAt());
        String savedTime = sdf.format(now);
        gameStateBuilder.add("map", gameState.getCurrentMap())
                .add("date", savedTime)
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
