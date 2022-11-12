package com.codecool.dungeoncrawl.logic.doors;

import com.codecool.dungeoncrawl.logic.Cell;

public class NormalDoor extends Door{
    public NormalDoor(Cell cell) {
        super(cell);
        isOpen = false;
        cell.setNormalDoor(this);
    }

    public void setIsOpen() {
        isOpen = true;
    }

    public boolean getIsOpen() {
        return isOpen;
    }
    @Override
    public String getTileName() {
        return "door";
    }
}
