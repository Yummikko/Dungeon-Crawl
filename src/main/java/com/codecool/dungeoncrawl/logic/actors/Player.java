package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Player extends Actor {
    private ArrayList<Item> inventory;

    public static final int HEALTH = 10;
    public static final int ATTACK_STRENGTH = 5;

    public String getTileName() {
        return "player";
    }

    public Player(Cell cell) {
        super(cell);
        this.setHealth(HEALTH);
        this.setStrength(ATTACK_STRENGTH);
        this.inventory = new ArrayList<>();
    }

    public void addToInventory(Item item) {
        inventory.add(item);
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public void removeFromInventory(Item item) {
        inventory.remove(item);
    }

    public ArrayList getInventory() {
        return inventory;
    }

    public void pickUpItem() {
        if (this.getCell().getItem() != null) {
            addToInventory(this.getCell().getItem());
            if (this.getCell().getItem() instanceof Weapon) {
                this.setStrength(getStrength()+2);
            } else if (this.getCell().getItem() instanceof Food) {
                this.setHealth(getHealth()+3);
            } else if (this.getCell().getItem() instanceof Key) {
                addToInventory(this.getCell().getItem());
            }
            //System.out.println(inventory);
            this.getCell().setItem(null);
        }
    }

    public String displayInventory() {
        StringBuilder display = new StringBuilder();
        int keyCount = 0;
        int swordCount = 0;
        int shieldCount = 0;

        HashMap<String, Integer> inventory_dict = new HashMap<String, Integer>();
        for(Item item : inventory){
            if(item instanceof Key){
                keyCount+=1;
                if(keyCount <= 1){
                    inventory_dict.put(item.getTileName(), keyCount);
                }else{
                    inventory_dict.put("Key", keyCount);
                }

            } else if (item instanceof Sword){
                swordCount += 1;
                if(keyCount <= 1){
                    inventory_dict.put(item.getTileName(), swordCount);
                }else{
                    inventory_dict.put("Sword", swordCount);
                }

            } else if (item instanceof Shield){
                shieldCount += 1;
                if(keyCount <= 1){
                    inventory_dict.put(item.getTileName(), shieldCount);
                }else{
                    inventory_dict.put("Sword", shieldCount);
                }

            }
        }
        for(HashMap.Entry<String, Integer> element: inventory_dict.entrySet()){
            display.append(element.getKey()+": "+ element.getValue());
            display.append("\n");
        }


        return display.toString();
    }
}
