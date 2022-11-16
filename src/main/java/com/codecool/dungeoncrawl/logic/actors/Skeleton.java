package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.doors.NormalDoor;
import com.codecool.dungeoncrawl.logic.doors.OpenDoor;

import java.util.List;
import java.util.Random;


public class Skeleton extends Actor {

    public Skeleton(Cell cell) {
        super(cell);
        cell.setSkeleton(this);
        this.setStrength(2);
        this.setHealth(10);
    }

    public static void monsterMove(List<Skeleton> skeletons, GameMap map) {
        Random rand = new Random();
        int min = 0;
        int max = 4;
        for (int i = 0; i < skeletons.size(); i++) {
            int randomPos = rand.nextInt(max - min) + min;
            Skeleton a = skeletons.get(i);
            map.setSkeleton(a);
            if (randomPos == 1) {
                map.getSkeleton().randomMove(0, 1);
            } else if (randomPos == 2) {
                map.getSkeleton().randomMove(0, -1);
            } else if (randomPos == 3) {
                map.getSkeleton().randomMove(1, 0);
            } else {
                map.getSkeleton().randomMove(-1, 0);
            }
        }
    }

    public void randomMove(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getOpenDoor() != null || nextCell.getType().equals(CellType.WALL)) {
            return;
        }
        else if (nextCell.getActor() == null) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        } else if (isEnemy(nextCell)) {
            if (nextCell.getActor() instanceof Player) {
                nextCell.getActor().fightWithMonster(cell.getActor());
            }
        }
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
