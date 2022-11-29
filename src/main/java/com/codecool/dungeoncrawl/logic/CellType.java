package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.doors.NormalDoor;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    STAIRS("stairs"),
    WALL("wall"),
    WATER("water"),
    SKULL("skull"),
    CROWN("crown"),
    KEY("key"),
    FOOD("food"),
    SHIELD("shield"),
    POISON("poison"),
    WEAPON("weapon"),
    LADDER("ladder"),
    NORMAL_DOOR("door"),
    OPEN_DOOR("openDoor");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
