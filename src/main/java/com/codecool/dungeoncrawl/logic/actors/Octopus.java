package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Octopus extends Actor{
    public Octopus(Cell cell) {
        super(cell);
        this.setStrength(5);
        this.setHealth(45);
    }

    @Override
    public String getTileName() {
        return "octopus";
    }
}
