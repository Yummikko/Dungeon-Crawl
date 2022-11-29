package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Game;
import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.RightUiPanel;
import com.codecool.dungeoncrawl.graphics.GameInventory;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.doors.Door;
import com.codecool.dungeoncrawl.logic.doors.NormalDoor;
import com.codecool.dungeoncrawl.logic.doors.OpenDoor;
import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.logic.util.SoundUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;


public class Player extends Actor {

    public static final int INITIAL_HEALTH = 35;
    public static final int ATTACK_STRENGTH = 5;
    private List<Item> inventory;
    private RightUiPanel rightUIInventory;

    public Player(Cell cell) {
        super(cell);
        this.setHealth(INITIAL_HEALTH);
        this.setStrength(ATTACK_STRENGTH);
        this.inventory = new ArrayList<>();
    }

    @Override
    public void move(Direction direction) {
        Set<String> developerNames = Set.of("natalia", "duc", "ola", "dawid");
        Cell nextCell = cell.getNeighbour(direction);
        if (nextCell == null) return;
        String smallName = name.toLowerCase();
        if (nextCell.getType() == CellType.STAIRS) {
            Game.setMap();
        }
        if (nextCell.getNormalDoor() != null) {
            NormalDoor door = nextCell.getNormalDoor();
            if (door.getIsOpen()) {
                for (Item item : this.getInventory()) {
                    if (item instanceof Key) {
                        door.setCell(new OpenDoor(door.getCell()).getCell());
                        removeKey();
                        moveActor(nextCell);
                        SoundUtils.playSound(SoundUtils.OPEN_DOOR, 0.7f);
                        return;
                    }
                }
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

    @Override
    void moveActor(Cell nextCell) {
        SoundUtils.playSound(SoundUtils.STEP, 0.7f);
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    public String getTileName() {
        return "player";
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    public void setRightUiPanel(RightUiPanel rightUiPanel) {
        this.rightUIInventory = rightUiPanel;
    }

    public void pickUpItem() {
        Item item = cell.getItem();
        inventory.add(item);
        if (item == null) {
            return;
        } else if (item instanceof Weapon) {
            this.setStrength(getStrength() + 2);
            this.setHasWeapon(true);
        } else if (item instanceof Shield) {
            this.setStrength(getStrength() + 10);
        } else if (item instanceof Food) {
            SoundUtils.playSound(SoundUtils.EAT, 0.5f);
            this.setHealth(getHealth() + 3);
        } else if (item instanceof Poison) {
            SoundUtils.playSound(SoundUtils.EAT, 0.5f);
            this.setHealth(getHealth() - 3);
        } else if (item instanceof Key) {
            this.setHasKey(true);
        }
        cell.setItem(null);
    }

    public String inventoryToString() {
        StringJoiner s = new StringJoiner("\n");
        for (Item item : inventory) {
            if (item != null) s.add(item.getTileName());
        }
        return s.toString();
    }

    public void openClosedDoor(NormalDoor door) {
        for (Item item : inventory) {
            if (item instanceof Key) {
                door.setIsOpen();
            }
        }
    }

    public void removeKey() {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) instanceof Key) {
                rightUIInventory.getInventory().removeItemFromLoot(inventory.get(i));
                this.inventory.remove(i);
                break;
            }
        }
    }

}
