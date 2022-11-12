package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;


public class Skeleton extends Actor {
    private boolean canMove;

    public Skeleton(Cell cell) {
        super(cell);
        this.canMove = true;
        cell.setSkeleton(this);
        this.setStrength(5);
        this.setHealth(20);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
