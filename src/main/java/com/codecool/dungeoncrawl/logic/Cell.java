package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.doors.Door;
import com.codecool.dungeoncrawl.logic.doors.NormalDoor;
import com.codecool.dungeoncrawl.logic.doors.OpenDoor;
import com.codecool.dungeoncrawl.logic.enviroment.Enviroment;
import com.codecool.dungeoncrawl.logic.items.Item;
import java.util.Objects;

public class Cell implements Drawable {
    private final GameMap gameMap;
    private final int x, y;
    private CellType type;
    private Actor actor;
    private Door door;
    private NormalDoor normalDoor;
    private OpenDoor openDoor;
    private Skeleton skeleton;
    private Lich lich;
    private Player player;
    private Item item;
    private Enviroment enviroment;
    private DarkLord darkLord;
    private Phantom phantom;

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

    public DarkLord getDarkLord() {
        return darkLord;
    }

    public void setDarkLord(DarkLord darkLord) {
        this.darkLord = darkLord;
    }

    public Phantom getPhantom() {
        return phantom;
    }

    public void setPhantom(Phantom phantom) {
        this.phantom = phantom;
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



    public Enviroment getEnviroment() {
        return enviroment;
    }

    public void setEnviroment(Enviroment enviroment) {
        this.enviroment = enviroment;
    }

}
