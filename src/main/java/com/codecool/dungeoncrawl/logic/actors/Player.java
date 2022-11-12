package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
<<<<<<< HEAD
import com.codecool.dungeoncrawl.logic.items.Food;
=======
import com.codecool.dungeoncrawl.logic.doors.NormalDoor;
import com.codecool.dungeoncrawl.logic.doors.OpenDoor;
>>>>>>> fc43c7d (Adding open door when there is the key functionality)
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Weapon;

import java.util.ArrayList;
import java.util.HashMap;
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
        if (this.getCell().getItem() == null) {
            return;
        }
        addToInventory(this.getCell().getItem());
        System.out.println(inventory);
        this.getCell().setItem(null);
        if (this.getCell().getItem() instanceof Weapon) {
            this.setStrength(getStrength() + 2);
            this.setHasWeapon(true);
        } else if (this.getCell().getItem() instanceof Food) {
            this.setHealth(getHealth() + 3);
        } else if (this.getCell().getItem() instanceof Key) {
            this.setHasKey(true);
        }
    }

    public String inventoryToString() {
        StringJoiner s = new StringJoiner("\n");
        for (Item item : inventory) {
            if (item != null) s.add(item.getTileName());
        }
        return s.toString();
    }

    public String displayInventory() {
        StringBuilder display = new StringBuilder();
        int keyCount = 0;
        int swordCount = 0;
        int shieldCount = 0;

        HashMap<String, Integer> inventory_dict = new HashMap<String, Integer>();
        for (Item item : inventory) {
            if (item instanceof Key) {
                keyCount += 1;
                if (keyCount <= 1) {
                    inventory_dict.put(item.getTileName(), keyCount);
                } else {
                    inventory_dict.put("Key", keyCount);
                }
            }
            for (HashMap.Entry<String, Integer> element : inventory_dict.entrySet()) {
                display.append(element.getKey() + ": " + element.getValue());
                display.append("\n");
            }

        }
        return display.toString();
    }

<<<<<<< HEAD
=======
    public void openClosedDoor(NormalDoor door) {
        ArrayList<Item> inventory = this.getInventory();
        for (Item item : inventory) {
            if (item instanceof Key) {
                System.out.println("The Key is inside inventory!");
                if(!door.getIsOpen())
                    door.setIsOpen();
            }
        }
        if (door.getIsOpen()) {
            System.out.println("Player can enter through the door.");
            door.setCell(new OpenDoor(door.getCell()).getCell());
        }
    }
>>>>>>> fc43c7d (Adding open door when there is the key functionality)
}
