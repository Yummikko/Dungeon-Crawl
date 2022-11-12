package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

import java.util.Objects;

public abstract class Actor implements Drawable {
    protected Cell cell;
    private int health = 10;
    private int strength = 3;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getOpenDoor() != null) {

            nextCell.setActor(this);
        } else if (isWallOrEnemy(nextCell)) {
            return;
        }
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    private static boolean isWallOrEnemy(Cell nextCell) {
        return nextCell.getType().equals(CellType.WALL) || nextCell.getNormalDoor() != null || nextCell.getActor() != null;
    }
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }
}
