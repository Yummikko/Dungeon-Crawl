package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    STAIRS("stairs"),
    WATER("water"),
    SKULL("skull"),
    CROWN("crown"),
    KEY("key"),
    FOOD("food"),
    POISON("poison"),
    WALL("wall"),
    SHIELD("shield");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
