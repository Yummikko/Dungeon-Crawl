package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.List;
import java.util.Random;

public class Lich extends Actor {

    private static final Random rand = new Random();

    public Lich(Cell cell) {
        super(cell);
        cell.setLich(this);
        this.setStrength(6);
        this.setHealth(22);
    }

    public static void magicMovement(List<Lich> lichs, GameMap map, Player player) {
        int max = 4;
        int randomMove = rand.nextInt(max);
        for (Lich lich : lichs) {
            int randomNum = rand.nextInt(100);
            map.setLich(lich);
            if (randomNum < 75) {
                map.getLich().moveRandomly(randomMove, map.getLich(), rand);
            } else {
                if (player.getX() + 1 < map.getWidth())
                    teleportMonster(player, map, map.getLich(), rand);
            }
        }
    }

    public static void teleportMonster(Player player, GameMap map, Lich lich, Random rand) {
        int min = -1;
        int max = 1;
        int randomPos = rand.nextInt(max - min) + min;
        int randomPosNext = rand.nextInt(max - min) + min;
        int x = player.getX();
        int y = player.getY();
        Cell currentCell = map.getCell(x, y);
        Cell nextCell = map.getCell(x + randomPosNext, y + randomPosNext);
        if (x + randomPosNext < map.getWidth() || y + randomPosNext < map.getHeight()) {
            try {
                if (currentCell.getNeighbor(randomPos, randomPosNext) == null
                        || currentCell.getNeighbor(randomPos, randomPosNext).getSkeleton() != null
                        || currentCell.getNeighbor(randomPos, randomPosNext).getPlayer() != null
                        || nextCell.getNeighbor(randomPos, randomPosNext).getPlayer() != null) {
                    return;
                } else if (nextCell.getNeighbor(randomPos, randomPosNext) == null
                        || nextCell.getNeighbor(randomPos, randomPosNext).getType().equals(CellType.WALL)
                        || nextCell.getType().equals(CellType.WALL)) {
                    return;
                } else if (randomPosNext == 0 || randomPos == 0) {
                    return;
                } else
                    lich.newMove(nextCell, lich);
                if (isEnemy(nextCell.getNeighbor(randomPos, randomPosNext))
                        && nextCell.getActor() instanceof Player) {
                    nextCell.getActor().fightWithMonster(lich);

                }
            } catch (NullPointerException e) {
                System.out.println("getPlayer() is null, cannot move there");
            }
        }
    }

    public static void moveRandomly(int randomPos, Lich lich, Random rand) {
        int min = -1;
        int max = 1;
        int randomX = rand.nextInt(max - min) + min;
        int randomY = rand.nextInt(max - min) + min;
        Cell lichNextPos = lich.getCell().getNeighbor(randomX, randomY);
        if (lichNextPos.getType().equals(CellType.WALL) || lichNextPos.getActor() != null) {
            return;
        }
        if (randomPos == 1) {
            lich.move(0, 1);
        } else if (randomPos == 2) {
            lich.move(0, -1);
        } else if (randomPos == 3) {
            lich.move(1, 0);
        } else {
            lich.move(-1, 0);
        }
    }

    @Override
    public String getTileName() {
        return "lich";
    }

    public void newMove(Cell nextCell, Lich lich) {
        cell.setActor(null);
        nextCell.setActor(lich);
        cell = nextCell;
    }
}
