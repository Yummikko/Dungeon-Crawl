package com.codecool.dungeoncrawl.logic.doors;

import com.codecool.dungeoncrawl.logic.Cell;

public class OpenDoor extends Door{

    public OpenDoor(Cell cell) {
        super(cell);
        this.isOpen = true;
        cell.setOpenDoor(this);
    }

    @Override
    public String getTileName() {
        return "openDoor";
    }


}
