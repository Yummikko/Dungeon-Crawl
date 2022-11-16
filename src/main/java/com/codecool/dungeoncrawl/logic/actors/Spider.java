package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Spider extends Actor{
    public Spider(Cell cell) {
        super(cell);
        this.setStrength(5);
        this.setHealth(10);
    }

    @Override
    public String getTileName() {
        return "spider";
    }
}
