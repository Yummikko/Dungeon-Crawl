package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.GameMap;

public abstract class Enemy extends Actor {

    protected Enemy(Cell cell) {
        super(cell);
    }

    public void move(GameMap map) {
        Direction direction = Direction.getRandomDirection();
        move(direction);
    }

    @Override
    public void move(Direction direction) {
        Cell nextCell = cell.getNeighbour(direction);
        if (nextCell == null || isNotWalkable(nextCell) || nextCell.getActor() instanceof Enemy) return;
        if (nextCell.getActor() instanceof Player) {
            nextCell.getActor().fightWithMonster(this);
            return;
        }
        moveActor(nextCell);
    }

}

