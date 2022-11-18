package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.DarkLord;
import com.codecool.dungeoncrawl.logic.actors.Lich;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import java.util.ArrayList;
import java.util.List;


public class GameMap {
    private final int width;
    private final int height;
    private final Cell[][] cells;
    private final List<Skeleton> skeletons = new ArrayList<>();
    private final List<Lich> lichs = new ArrayList<>();
    private final List<DarkLord> bosses = new ArrayList<>();
    private Player player;
    private Skeleton skeleton;
    private Lich lich;
    private DarkLord darkLord;

    public DarkLord getDarkLord() {
        return darkLord;
    }

    public void setDarkLord(DarkLord darkLord) {
        this.darkLord = darkLord;
    }

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        try {
            return cells[x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public Lich getLich() {
        return lich;
    }

    public void setLich(Lich lich) {
        this.lich = lich;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setSkeleton(Skeleton skeleton) {
        this.skeleton = skeleton;
    }

    public Skeleton getSkeleton() {
        return skeleton;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Skeleton> getSkeletons() {
        return skeletons;
    }

    public List<Lich> getLichs() {
        return lichs;
    }

    public List<DarkLord> getDarkLords() {
        return bosses;
    }
}
