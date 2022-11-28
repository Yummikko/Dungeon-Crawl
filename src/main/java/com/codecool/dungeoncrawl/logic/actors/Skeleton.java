package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.GameMap;

public class Skeleton extends Actor implements Enemy {

    public Skeleton(Cell cell) {
        super(cell);
        this.setStrength(2);
        this.setHealth(10);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
