package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.doors.OpenDoor;

import java.util.ArrayList;
import java.util.List;

public class OpenDoorModel extends BaseModel{
    private OpenDoor openDoor;
    private int x;
    private int y;
    private List<OpenDoor> openedDoors = new ArrayList<>();

    public OpenDoorModel(int x, int y, OpenDoor openDoor, List<OpenDoor> openedDoors) {
        this.x = x;
        this.y = y;
        this.openDoor = openDoor;
        this.openedDoors = openedDoors;
    }

    public OpenDoor getOpenDoor() {
        return openDoor;
    }

    public void setOpenDoor(OpenDoor openDoor) {
        this.openDoor = openDoor;
    }

    public List<OpenDoor> getOpenedDoors() {
        return openedDoors;
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
