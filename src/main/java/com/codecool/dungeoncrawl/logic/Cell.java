package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.doors.Door;
import com.codecool.dungeoncrawl.logic.doors.NormalDoor;
import com.codecool.dungeoncrawl.logic.doors.OpenDoor;
import com.codecool.dungeoncrawl.logic.enviroment.Enviroment;
import com.codecool.dungeoncrawl.logic.items.Item;

public class Cell implements Drawable {
    private final GameMap gameMap;
    private final int y;
    private final int x;
    private CellType type;
    private Actor actor;
    private Door door;
    private NormalDoor normalDoor;
    private OpenDoor openDoor;
    private Item item;
    private Enviroment enviroment;

    Cell(GameMap gameMap, int x, int y, CellType type) {
        this.gameMap = gameMap;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Door getDoor() {
        return door;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    public NormalDoor getNormalDoor() {
        return normalDoor;
    }

    public void setNormalDoor(NormalDoor normalDoor) {
        this.normalDoor = normalDoor;
    }

    public void setOpenDoor(OpenDoor openDoor) {
        this.openDoor = openDoor;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Cell getNeighbour(int dx, int dy) {
        return gameMap.getCell(x + dx, y + dy);
    }

    public Cell getNeighbour(Direction d) {
        return gameMap.getCell(x + d.getX(), y + d.getY());
    }

    @Override
    public String getTileName() {
        return type.getTileName();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Enviroment getEnviroment() {
        return enviroment;
    }

    public void setEnviroment(Enviroment enviroment) {
        this.enviroment = enviroment;
    }

}
