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

    /* think about refactoring this as it's not working perfectly */
    public static void magicMovement(List<Lich> lichs, GameMap map, Player player) {
        Random rand = new Random();
        int max = 4;
        int randomMove = rand.nextInt(max);
        for (int i = 0; i < lichs.size(); i++) {
            int randomNum = rand.nextInt(100);
            Lich a = lichs.get(i);
            map.setLich(a);
            if (randomNum < 75)
                map.getLich().moveRandomly(randomMove, map.getLich(), rand);
            else {
                if (player.getX()+1 < map.getWidth())
                    teleportMonster(player, map, map.getLich(), rand);
            }
        }
    }
    public static void teleportMonster(Player player, GameMap map, Lich lich, Random rand) {
        int min = -1;
        int max = 1;
        int randomPos = rand.nextInt(max - min) + min;
        int randomPosNext = rand.nextInt(max - min) + min;
        System.out.println(randomPos);
        int x = player.getX();
        int y = player.getY();
        Cell currentCell = map.getCell(x, y);
        Cell nextCell = map.getCell(x+randomPosNext, y+randomPosNext);
        if (x + randomPosNext < map.getWidth() || y + randomPosNext < map.getHeight()) {
            if (currentCell.getNeighbor(randomPos, randomPosNext) == null || currentCell.getNeighbor(randomPos, randomPosNext).getSkeleton() != null) {
                return;
            }
            if (nextCell == null || nextCell.getNeighbor(randomPos, randomPosNext) == null || nextCell.getNeighbor(randomPos, randomPosNext).getType().equals(CellType.WALL) || nextCell.getType().equals(CellType.WALL)) {
                return;
            }
            else if (nextCell.getActor() != null) {
                if (nextCell.getActor() instanceof Player) {
                    nextCell.getActor().fightWithMonster(lich);
                }
            } else {
                lich.newMove(nextCell, lich);
            }
        } else
            return;
    }

    public void newMove(Cell nextCell, Lich lich) {
//        System.out.println("moved");
        cell.setActor(null);
        nextCell.setActor(lich);
        cell = nextCell;
    }

    public static void moveRandomly(int randomPos, Lich lich, Random rand) {
        int x = lich.getCell().getX();
        int y = lich.getCell().getY();
        int min = -1;
        int max = 1;
        int randomX = rand.nextInt(max - min) + min;
        int randomY = rand.nextInt(max - min) + min;
        Cell lichNextPos = lich.getCell().getNeighbor(randomX, randomY);
        if (randomPos == 1)
            if (lichNextPos.getType().equals(CellType.WALL) || lichNextPos == null || lichNextPos.getActor() != null) {
                return;
            }
            else
                lich.move(0, 1);
        else if (randomPos == 2)
            if (lichNextPos.getType().equals(CellType.WALL) || lichNextPos == null || lichNextPos.getActor() != null) {
                return;
            }
            else
                lich.move(0, -1);
        else if (randomPos == 3)
            if (lichNextPos.getType().equals(CellType.WALL) || lichNextPos == null || lichNextPos.getActor() != null) {
                return;
            }
            else
                lich.move(1, 0);
        else
            if (lichNextPos.getType().equals(CellType.WALL) || lichNextPos == null || lichNextPos.getActor() != null) {
                return;
            }
            else
                lich.move(-1, 0);
    }
}
