package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.List;
import java.util.Random;

public class Lich extends Actor {
    public Lich(Cell cell) {
        super(cell);
        cell.setLich(this);
        this.setStrength(6);
        this.setHealth(12);
    }
    @Override
    public String getTileName() {
        return "lich";
    }

    public static void magicMovement(List<Lich> lichs, GameMap map, Player player) {
        Random rand = new Random();
        int min = 0;
        int max = 4;
        for (int i = 0; i < lichs.size(); i++) {
            int randomPos = rand.nextInt(max - min) + min;
            int randomNum = rand.nextInt(100);
            Lich a = lichs.get(i);
            map.setLich(a);
            Cell currentPos = a.getCell();
            if (randomPos == 1) {
                if (randomNum < 50)
                    map.getLich().move(0, 1);
                else {
                    if (player.getX()+1 < map.getWidth())
                        teleportMonster(player.getX()+1, player.getY(), map, map.getLich(), randomPos);
                    else
                        map.getLich().move(0, 1);
                }
            } else if (randomPos == 2) {
                if (randomNum < 50)
                    map.getLich().move(0, -1);
                else {
                    if (player.getX()-1 < map.getWidth())
                        teleportMonster(player.getX()-1, player.getY(), map, map.getLich(), randomPos);
                    else
                        map.getLich().move(0, -1);
                }
            } else if (randomPos == 3) {
                if (randomNum < 50)
                    map.getLich().move(1, 0);
                else {
                    if (player.getY()+1 < map.getHeight())
                        teleportMonster(player.getX(), player.getY()+1, map, map.getLich(), randomPos);
                    else
                        map.getLich().move(1, 0);
                }
            } else {
                if (randomNum < 50)
                    map.getLich().move(-1, 0);
                else {
                    if (player.getY()-1 < map.getHeight())
                        teleportMonster(player.getX(), player.getY()-1, map, map.getLich(), randomPos);
                    else
                        map.getLich().move(-1, 0);
                }
            }
        }
    }
    public static void teleportMonster(int x, int y, GameMap map, Lich lich, int randomPos) {
        Cell current = lich.getCell();
        Cell nextCell = map.getCell(x+1, y+1);
        Cell evenNextCell = map.getCell(x+1, y+1);
        if (nextCell != null || !nextCell.equals(CellType.WALL)) {
            lich.newMove(current, nextCell);
        } else if (nextCell.getNeighbor(x, y) != null || !nextCell.getNeighbor(x, y).equals(CellType.WALL)) {
            lich.newMove(current, evenNextCell);
        } else
            if (randomPos == 1)
                map.getSkeleton().move(0, 1);
            else if (randomPos == 2)
                map.getSkeleton().move(0, -1);
            else if (randomPos == 3)
                map.getSkeleton().move(1, 0);
            else
                map.getSkeleton().move(-1, 0);

    }

    public void newMove(Cell cell, Cell nextCell) {
        System.out.println("moved");
        cell.setLich(null);
        nextCell.setLich(this);
    }
}
