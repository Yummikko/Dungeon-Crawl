package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.logic.util.SoundUtils;

import java.util.Set;

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

    protected static boolean isNotWalkable(Cell nextCell) {
        Set<CellType> walkableCells = Set.of(CellType.FLOOR, CellType.STAIRS, CellType.CROWN ,CellType.LADDER);
        return !walkableCells.contains(nextCell.getType());
    }

    protected static boolean isEnemy(Cell nextCell) {
        return nextCell.getActor() != null;
    }

    public void move(GameMap map) {
        Direction direction = Direction.getRandomDirection();
        move(direction);
    }

    public void move(Direction direction) {
        Cell nextCell = cell.getNeighbour(direction);
        if (nextCell == null || isNotWalkable(nextCell)) return;
        else if (nextCell.getActor() != null) return;
        if (nextCell.getActor() instanceof Player) {
            nextCell.getActor().fightWithMonster(this);
            return;
        }
        moveActor(nextCell);
    }

    void moveActor(Cell nextCell) {
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    protected void fightWithMonster(Actor actor) {
        actor.setHealth(actor.getHealth() - this.getStrength());
        playHitSound();
        if (actor.isAlive()) {
            this.setHealth(this.getHealth() - actor.getStrength());
            if (this.isDead()) this.setAlive(false);
        } else {
            actor.getCell().setActor(null);
        }
    }

    private void playHitSound() {
        if (this.hasWeapon) {
            SoundUtils.playSound(SoundUtils.SWORD_HIT, 0.8f);
        } else {
            SoundUtils.playSound(SoundUtils.HIT, 0.8f);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        if (isDead()) {
            setAlive(false);
        }
    }

    public boolean isDead() {
        return health <= 0;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    public void setHasWeapon(boolean hasWeapon) {
        this.hasWeapon = hasWeapon;
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

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean getHasWeapon() {
        return hasWeapon;
    }

    public boolean getHasKey() {
        return hasKey;
    }

}
