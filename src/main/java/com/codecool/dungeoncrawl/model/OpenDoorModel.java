package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.doors.OpenDoor;

import java.util.ArrayList;
import java.util.List;

public class OpenDoorModel extends BaseModel{
    private int x;
    private int y;

    public OpenDoorModel(int x, int y) {
        this.x = x;
        this.y = y;
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
