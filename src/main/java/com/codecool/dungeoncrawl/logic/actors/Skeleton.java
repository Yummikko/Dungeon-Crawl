package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;


public class Skeleton extends Actor {
    private boolean canMove;
    public Skeleton(Cell cell) {
        super(cell);
        this.canMove = true;
        cell.setSkeleton(this);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
