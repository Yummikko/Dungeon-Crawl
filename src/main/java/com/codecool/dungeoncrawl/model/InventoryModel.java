package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.Game;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;
import java.util.List;

public class InventoryModel extends BaseModel {
    private String item;

    public InventoryModel(String item) {
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

}
