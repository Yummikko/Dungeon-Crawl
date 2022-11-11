package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Key extends Item{
    @Override
    public String getTileName() {
        return "key";
    }

    public Key(Cell cell) {
        super(cell);
    }

}
