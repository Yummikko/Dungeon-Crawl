package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

public class Octopus extends Actor implements Enemy {

    public Octopus(Cell cell) {
        super(cell);
        this.setStrength(5);
        this.setHealth(45);
    }

    @Override
    public void move(GameMap map) {
        // octopus doesn't move
    }

    @Override
    public String getTileName() {
        return "octopus";
    }

}
