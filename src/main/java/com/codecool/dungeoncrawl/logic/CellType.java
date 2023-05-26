package com.codecool.dungeoncrawl.logic;

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
    PORTAL("portal");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
