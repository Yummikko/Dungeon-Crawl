package com.codecool.dungeoncrawl.model;

public class ItemModel {
    private String itemName;
    private int x;
    private int y;

    public ItemModel(String itemName, int x, int y) {
        this.itemName = itemName;
        this.x = x;
        this.y = y;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
