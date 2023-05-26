package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.GameMap;

public class Phantom extends Actor implements Enemy {

    public Phantom(Cell cell) {
        super(cell);
        this.setHealth(8);
        this.setStrength(3);
    }

    @Override
    public String getTileName() {
        return "phantom";
    }

}
