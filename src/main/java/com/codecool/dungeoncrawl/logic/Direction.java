package com.codecool.dungeoncrawl.logic;

import java.util.Random;

public enum Direction {

    NORTH(-1, 0),
    SOUTH(1, 0),
    WEST(0, -1),
    EAST(0, 1);

    private static final Random random = new Random();
    private final int y;
    private final int x;

    Direction(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public static Direction getRandomDirection() {
        return values()[random.nextInt(values().length)];
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

}
