package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.List;
import java.util.Random;

public class Lich extends Actor {
    public Lich(Cell cell) {
        super(cell);
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
            int randomX = rand.nextInt(map.getWidth());
            int randomY = rand.nextInt(map.getHeight());
            Lich a = lichs.get(i);
            cell.setLich(a);
            if (randomPos == 1) {
                if (randomNum < 50)
                    cell.getLich().move(0, 1);
                else {
                    teleportMonster(player.getX()+1, player.getY(), map, cell.getLich(), randomX, randomY);
                }
            } else if (randomPos == 2) {
                if (randomNum < 50)
                    cell.getLich().move(0, -1);
                else {
                    teleportMonster(player.getX()-1, player.getY(), map, cell.getLich(), randomX, randomY);
                }
            } else if (randomPos == 3) {
                if (randomNum < 50)
                    cell.getLich().move(1, 0);
                else {
                    teleportMonster(player.getX(), player.getY()+1, map, cell.getLich(), randomX, randomY);
                }
            } else {
                if (randomNum < 50)
                    cell.getLich().move(-1, 0);
                else {
                    teleportMonster(player.getX(), player.getY()-1, map, cell.getLich(), randomX, randomY);
                }
            }
        }
    }
    public static void teleportMonster(int x, int y, GameMap map, Lich lich, int randomX, int randomY) {
        Cell nextCell = map.getCell(x+1, y+1);
        if (nextCell != null || !nextCell.equals(CellType.WALL)) {
            lich.move(x+1, y+1);
        } else if (nextCell.getNeighbor(x, y) != null || !nextCell.getNeighbor(x, y).equals(CellType.WALL)) {
            lich.move(x+2, y+2);
        } else
            lich.move(randomX, randomY);
    }

}
