package com.codecool.dungeoncrawl.logic.doors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Door implements Drawable {
    private Cell cell;
    protected boolean isOpen;

    public Door(Cell cell) {
        this.cell = cell;
        this.isOpen = false;
        this.cell.setDoor(this);
    }


    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

}
