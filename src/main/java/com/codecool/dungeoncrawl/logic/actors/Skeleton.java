package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Skeleton extends Enemy {

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
