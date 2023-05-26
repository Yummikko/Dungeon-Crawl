package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Poison extends Item {

    public Poison(Cell cell) { super(cell); }

    @Override
    public String getTileName() {
        return "poison";
    }
}