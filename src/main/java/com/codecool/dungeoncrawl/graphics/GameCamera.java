package com.codecool.dungeoncrawl.graphics;

import com.codecool.dungeoncrawl.Game;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;

import java.io.File;

public class GameCamera {

    private final GameMap map;
    private float xOffset;
    private float yOffset;
    private GameMap firstLevel = MapLoader.loadMap("/map1.txt", false);
    private GameMap thirdLevel = MapLoader.loadMap("/map3.txt", false);
    private GameMap bonusMap = MapLoader.loadMap("/bonus_map.txt", false);

    public GameCamera(float xOffset, float yOffset, GameMap map) {
        this.map = map;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void centerOnPlayer(Player player, GameMap map) {
         if (map.getWidth() == firstLevel.getWidth() || map.getWidth() == thirdLevel.getWidth() || map.getWidth() == bonusMap.getWidth()) {
            xOffset = player.getX() - map.getWidth() / 2 + 2;
            yOffset = player.getY() - map.getHeight() / 2 + 2;
        } else {
            xOffset = player.getX() - map.getWidth() / 2 + 32;
            yOffset = player.getY() - map.getHeight() / 2 + 4;
        }
    }

    public float getxOffset() {
        return xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

}
