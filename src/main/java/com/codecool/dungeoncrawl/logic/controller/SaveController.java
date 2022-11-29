package com.codecool.dungeoncrawl.logic.controller;

import com.codecool.dungeoncrawl.dao.GameDaoJdbc;
import com.codecool.dungeoncrawl.graphics.GameMenu;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.List;

public class SaveController {

    private final GameDaoJdbc gameDaoJdbc = new GameDaoJdbc();

    @FXML
    private Button cancel_btn;

    @FXML
    private Button save_btn;

    @FXML
    void saveGame() {
        System.out.println("saving game");
        int currentMap = MapLoader.maps.size();
        GameMap map;
        if (currentMap == 0) {
            map = GameMenu.map;
        } else {
            map = MapLoader.maps.get(currentMap - 1);
        }
        Player player = map.getPlayer();
        List<Actor> enemies = map.getEnemies();
        List<Item> items = map.getItems();
        gameDaoJdbc.add(player, enemies, items, currentMap);
        System.out.println("game saved");
        closeStage();
    }

    @FXML
    void cancelSaving() {
        System.out.println("closing save screen");
        closeStage();
    }

    private void closeStage() {
        Stage window = (Stage) cancel_btn.getScene().getWindow();
        window.close();
    }

}
