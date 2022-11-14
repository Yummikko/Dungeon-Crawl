package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Food;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Weapon;
import com.codecool.dungeoncrawl.logic.util.SoundUtils;

import java.util.ArrayList;
import java.util.Set;
import java.util.StringJoiner;

public class Player extends Actor {

    public static final int HEALTH = 10;
    public static final int ATTACK_STRENGTH = 5;
    private ArrayList<Item> inventory;

    public Player(Cell cell) {
        super(cell);
        this.setHealth(HEALTH);
        this.setStrength(ATTACK_STRENGTH);
        this.inventory = new ArrayList<>();
    }

    @Override
    public void move(int dx, int dy) {
        Set<String> developerNames = Set.of("Natalia", "Duc", "Ola", "Dawid");
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell == null) return;
        if (isWall(nextCell) && !developerNames.contains(name)) {
            return;
        }
        if (nextCell.getActor() == null) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
            return;
        }
        if (isEnemy(nextCell)) {
            this.fightWithMonster(nextCell.getActor());
        }
    }

    public String getTileName() {
        return "player";
    }

    public void addToInventory(Item item) {
        inventory.add(item);
    }

    public void removeFromInventory(Item item) {
        inventory.remove(item);
    }

    public ArrayList getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
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
}
