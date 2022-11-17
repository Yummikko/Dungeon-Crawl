package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.doors.NormalDoor;
import com.codecool.dungeoncrawl.logic.doors.OpenDoor;
import com.codecool.dungeoncrawl.logic.util.SoundUtils;

import java.util.List;

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
        } else if (nextCell.getActor() == null) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        } else if (isEnemy(nextCell)) {
            if (cell.getActor() instanceof Player) {
                this.fightWithMonster(nextCell.getActor());
            }
        }
    }

    public static void checkIfMonstersHealth(List<Skeleton> skeletons, List<Lich> liches) {
        for (int i = 0; i < skeletons.size(); i++) {
            Skeleton skeleton = skeletons.get(i);
            if (skeleton.getHealth() <= 0) {
                skeletons.remove(skeleton);
                skeleton.getCell().setActor(null);
            }
        }
        for (int j = 0; j < liches.size(); j++) {
            Lich lich = liches.get(j);
            if (lich.getHealth() <= 0) {
                liches.remove(lich);
                lich.getCell().setActor(null);
            }
        }
    }

    protected void fightWithMonster(Actor actor) {
        System.out.println(actor.getTileName());
        actor.setHealth(actor.getHealth() - this.getStrength());
        System.out.println("Enemy life = " + actor.getHealth());
        if (actor.getHealth() > 0) {
            this.setHealth(this.getHealth() - actor.getStrength());
            playHitSound();
            if (this.getHealth() < 1) this.setAlive(false);
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
        return name; }

    public void setName(String name) { this.name = name; }

    protected static boolean isWall(Cell nextCell) {
        return nextCell.getType().equals(CellType.WALL);
    }

    protected static boolean isEnemy(Cell nextCell) {
        return nextCell.getActor() != null;
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
