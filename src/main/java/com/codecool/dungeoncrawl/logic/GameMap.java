package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Phantom;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.doors.Door;
import com.codecool.dungeoncrawl.logic.doors.OpenDoor;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private final int width;
    private final int height;
    private final Cell[][] cells;
    private final List<Actor> enemies;
    private Player player;
    private List<Item> items;
    private List<OpenDoor> openDoors;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
        this.enemies = new ArrayList<>();
        this.openDoors = new ArrayList<>();
    }

    public Cell getCell(int x, int y) {
        try {
            return cells[x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addActor(Actor actor) {
        enemies.add(actor);
    }

    public List<Actor> getEnemies() {
        return enemies;
    }

    public List<Actor> getPhantoms() {
        return enemies.stream()
                .filter(Phantom.class::isInstance)
                .toList();
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addOpenDoor(OpenDoor openDoor) {
        if (!openDoors.contains(openDoor))
            openDoors.add(openDoor);
    }
    public List<OpenDoor> getOpenDoors() {
        return openDoors;
    }
}
