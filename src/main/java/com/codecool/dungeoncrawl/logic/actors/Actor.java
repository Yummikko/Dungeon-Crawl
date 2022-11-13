package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.doors.Door;
import com.codecool.dungeoncrawl.logic.doors.NormalDoor;
import com.codecool.dungeoncrawl.logic.doors.OpenDoor;

import java.text.Normalizer;

public abstract class Actor implements Drawable {
    protected String name;
    protected Cell cell;
    protected int health = 10;
    protected int strength = 3;
    protected boolean hasWeapon = false;
    protected boolean hasKey = false;
    protected boolean isAlive = true;

    protected Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getNormalDoor() != null) {
            NormalDoor door = nextCell.getNormalDoor();
            if(door.getIsOpen()) {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
                door.setCell(new OpenDoor(door.getCell()).getCell());
            }
        }
        if (isWall(nextCell)) {
            return;
        }
        if (isEnemy(nextCell)) { // todo do playera
            if (cell.getActor() instanceof Player) {
                this.fightWithMonster(nextCell.getActor());
            }
        }
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    private void fightWithMonster(Actor actor) {
        actor.setHealth(actor.getHealth() - this.getStrength());
        if (actor.getHealth() > 0) {
            this.setHealth(this.getHealth() - actor.getStrength());
            if (this.getHealth() < 1) this.setAlive(false);
        } else {
            actor.getCell().setActor(null);
        }
    }

    public String getName() {
        return name; }

    public void setName(String name) { this.name = name; }

    private static boolean isWall(Cell nextCell) {
        return nextCell.getType().equals(CellType.WALL);
    }

    private static boolean isEnemy(Cell nextCell) {
        return nextCell.getActor() != null;
    }


    private static boolean isWallOrEnemy(Cell nextCell) {
        return nextCell.getType().equals(CellType.WALL) || nextCell.getActor() != null;
    }
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAlive(boolean alive) { isAlive = alive; }

    public int getStrength() { return strength; }

    public void setStrength(int strength) { this.strength = strength;}

    public void setHasKey(boolean hasKey) { this.hasKey = hasKey; }

    public void setHasWeapon(boolean hasWeapon) { this.hasWeapon = hasWeapon; }
    //public boolean isHasKey() { return hasKey; }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public boolean isAlive() { return isAlive; }

}
