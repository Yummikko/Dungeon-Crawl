package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Lich;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.doors.Door;
import com.codecool.dungeoncrawl.logic.doors.NormalDoor;
import com.codecool.dungeoncrawl.logic.doors.OpenDoor;
import com.codecool.dungeoncrawl.logic.enviroment.Enviroment;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cell<T> implements Drawable {
    private CellType type;
    private Actor actor;
    private Door door;
    private NormalDoor normalDoor;

    private OpenDoor openDoor;

    private Skeleton skeleton;
    private Lich lich;

    private T t;

    private Player player;
    private Item item;
    private GameMap gameMap;
    private Enviroment enviroment;
    private int x, y;

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

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Actor getActor() {
        return actor;
    }

    public Door getDoor() {
        return door;
    }

    public Lich getLich() {
        return lich;
    }

    public void setLich(Lich lich) {
        this.lich = lich;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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

    public Skeleton getSkeleton() {
        return skeleton;
    }

    public void setSkeleton(Skeleton skeleton) {
        this.skeleton = skeleton;
    }

    public OpenDoor getOpenDoor() {
        return openDoor;
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

    public Cell getNeighbor(int dx, int dy) {
        return gameMap.getCell(x + dx, y + dy);
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

    public T switchItem(T original, T a, T b) {
        if (original == a)
            return b;
        else
            return a;
    }

    public Enviroment getEnviroment() {
        return enviroment;
    }

    public void setEnviroment(Enviroment enviroment) {
        this.enviroment = enviroment;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        T t = (T) o;
        return this == actor.getCell() && Objects.equals(this, actor.getCell());
    }
}
