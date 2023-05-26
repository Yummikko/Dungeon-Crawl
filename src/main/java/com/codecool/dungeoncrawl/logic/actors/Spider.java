package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

public class Spider extends Actor implements Enemy {

    public Spider(Cell cell) {
        super(cell);
        this.setStrength(3);
        this.setHealth(15);
    }

    @Override
    public void move(GameMap map) {
        // spider doesn't move
    }

    @Override
    public String getTileName() {
        return "spider";
    }
}
