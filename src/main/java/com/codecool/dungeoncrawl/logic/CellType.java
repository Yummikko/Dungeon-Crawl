package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    STAIRS("stairs"),

    DOWN_STAIRS("stairs"),
    WATER("water"),
    SKULL("skull"),
    CROWN("crown"),
    KEY("key"),
    FOOD("food"),
    WALL("wall");


    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
