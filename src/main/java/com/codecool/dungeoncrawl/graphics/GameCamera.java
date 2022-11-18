package com.codecool.dungeoncrawl.graphics;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class GameCamera {

    private GameMap map;
    private float xOffset, yOffset;

    public GameCamera(float xOffset, float yOffset, GameMap map) {
        this.map = map;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void centerOnPlayer(Player player, GameMap map) {
         if (map.getWidth() == 89) {
            xOffset = player.getX() - map.getWidth() / 2 + 32;
            yOffset = player.getY() - map.getHeight() / 2 + 4;
        } else {
            xOffset = player.getX() - map.getWidth() / 2 + 2;
            yOffset = player.getY() - map.getHeight() / 2 + 2;
        }
    }


    public float getxOffset() {
        return xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

}
