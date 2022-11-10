package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Octopus extends Actor{
    public Octopus(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "octopus";
    }
}
