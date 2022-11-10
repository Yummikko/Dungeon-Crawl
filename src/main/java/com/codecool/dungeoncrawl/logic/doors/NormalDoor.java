package com.codecool.dungeoncrawl.logic.doors;

import com.codecool.dungeoncrawl.logic.Cell;

public class NormalDoor extends Door{
    public NormalDoor(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "door";
    }
}
