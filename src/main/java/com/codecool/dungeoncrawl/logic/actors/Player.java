package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.doors.NormalDoor;
import com.codecool.dungeoncrawl.logic.doors.OpenDoor;
import com.codecool.dungeoncrawl.logic.items.Food;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Weapon;
import com.codecool.dungeoncrawl.logic.util.SoundUtils;
import java.util.ArrayList;
import java.util.Set;
import java.util.StringJoiner;


public class Player extends Actor {
    private ArrayList<Item> inventory;
    public static final int HEALTH = 35;
    public static final int ATTACK_STRENGTH = 5;

    public Player(Cell cell) {
        super(cell);
        cell.setPlayer(this);
        this.setHealth(HEALTH);
        this.setStrength(ATTACK_STRENGTH);
        this.inventory = new ArrayList<>();
    }


    @Override
    public void move(int dx, int dy) {
        Set<String> developerNames = Set.of("natalia", "duc", "ola", "dawid");
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell == null) return;
        String smallName = name.toLowerCase();
        if(nextCell.getType() == CellType.STAIRS) {
            Main.setMap();
        }
        if (nextCell.getNormalDoor() != null) {
            NormalDoor door = nextCell.getNormalDoor();
            if(door.getIsOpen()) {
                door.setCell(new OpenDoor(door.getCell()).getCell());
                moveActor(nextCell);
                return;
            }
            if (developerNames.contains(smallName)) {
                moveActor(nextCell);
                return;
            }
        }
        if (isNotWalkable(nextCell) && !developerNames.contains(smallName)) {
            return;
        }
        if (nextCell.getActor() == null) {
            moveActor(nextCell);
            return;
        }
        if (isEnemy(nextCell)) {
            cell.getActor().fightWithMonster(nextCell.getActor());
        }
    }


    private void moveActor(Cell nextCell) {
        SoundUtils.playSound(SoundUtils.STEP, 0.7f);
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    public String getTileName() {
        return "player";
    }


    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }


    public ArrayList getInventory() {
        return inventory;
    }


    public void pickUpItem() {
        inventory.add(this.getCell().getItem());
        if (this.getCell().getItem() == null) {
            return;
        } else if (this.getCell().getItem() instanceof Weapon) {
            this.setStrength(getStrength() + 2);
            this.setHasWeapon(true);
        } else if (this.getCell().getItem() instanceof Food) {
            SoundUtils.playSound(SoundUtils.EAT, 0.5f);
            this.setHealth(getHealth() + 3);
        } else if (this.getCell().getItem() instanceof Key) {
            this.setHasKey(true);
        }
        this.getCell().setItem(null);
    }

    public String inventoryToString() {
        StringJoiner s = new StringJoiner("\n");
        for (Item item : inventory) {
            if (item != null) s.add(item.getTileName());
        }
        return s.toString();
    }

    public void openClosedDoor(NormalDoor door) {
        ArrayList<Item> inventory = this.getInventory();
        for (Item item : inventory) {
            if (item instanceof Key) {
                if(!door.getIsOpen())
                    door.setIsOpen();
            }
        }
    }
}
