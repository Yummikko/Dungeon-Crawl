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
            if (randomPos == 1) {
                if (randomNum < 80)
                    map.getLich().move(0, 1);
                else {
                    if (player.getX()+1 < map.getWidth())
                        teleportMonster(player.getX()+1, player.getY(), map, map.getLich(), randomPos);
                    else
                        map.getLich().move(0, 1);
                }
            } else if (randomPos == 2) {
                if (randomNum < 80)
                    map.getLich().move(0, -1);
                else {
                    if (player.getX()-1 < map.getWidth())
                        teleportMonster(player.getX()-1, player.getY(), map, map.getLich(), randomPos);
                    else
                        map.getLich().move(0, -1);
                }
            } else if (randomPos == 3) {
                if (randomNum < 80)
                    map.getLich().move(1, 0);
                else {
                    if (player.getY()+1 < map.getHeight())
                        teleportMonster(player.getX(), player.getY()+1, map, map.getLich(), randomPos);
                    else
                        map.getLich().move(1, 0);
                }
            } else {
                if (randomNum < 80)
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
        Cell nextCell = map.getCell(x+1, y+1);
        Cell evenNextCell = map.getCell(x+2, y+2);
        if (nextCell != null || nextCell.getActor() != null) {
            lich.newMove(nextCell, lich);
        } else if (nextCell.getNeighbor(x, y) != null || nextCell.getNeighbor(x, y).getActor() != null) {
            lich.newMove(evenNextCell, lich);
        } else if (nextCell.getActor() != null) {
            if (nextCell.getActor() instanceof Player) {
                System.out.println("should hit player");
                nextCell.getPlayer().fightWithMonster(lich);
            }
        } else if (nextCell.getNeighbor(x, y).getType().equals(CellType.WALL) || nextCell.getType().equals(CellType.WALL)) {
            return;
        }
        else {
            System.out.println("should move randomly");
            lich.moveRandomly(randomPos, lich);
        }
    }

    public void newMove(Cell nextCell, Lich lich) {
//        System.out.println("moved");
        cell.setActor(null);
        nextCell.setActor(lich);
        cell = nextCell;
    }

    public static void moveRandomly(int randomPos, Lich lich) {
        int x = lich.getCell().getX();
        int y = lich.getCell().getY();
        Cell lichNextPos = lich.getCell().getNeighbor(x, y);
        if (randomPos == 1)
            if (lichNextPos.getType().equals(CellType.WALL) || lichNextPos != null || lichNextPos.getNeighbor(x, y).getActor() != null) {
                System.out.println("shouldn't move");
                return;
            }
            else
                lich.move(0, 1);
        else if (randomPos == 2)
            if (lichNextPos.getType().equals(CellType.WALL) || lichNextPos != null || lichNextPos.getNeighbor(x, y).getActor() != null) {
                System.out.println("shouldn't move");
                return;
            }
            else
                lich.move(0, -1);
        else if (randomPos == 3)
            if (lichNextPos.getType().equals(CellType.WALL) || lichNextPos != null || lichNextPos.getNeighbor(x, y).getActor() != null) {
                System.out.println("shouldn't move");
                return;
            }
            else
                lich.move(1, 0);
        else
            if (lichNextPos.getType().equals(CellType.WALL) || lichNextPos != null || lichNextPos.getNeighbor(x, y).getActor() != null) {
                System.out.println("shouldn't move");
                return;
            }
            else
                lich.move(-1, 0);
    }
}
