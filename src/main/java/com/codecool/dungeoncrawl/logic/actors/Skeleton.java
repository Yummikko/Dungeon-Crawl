package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import java.util.List;
import java.util.Random;


public class Skeleton extends Actor {
    private boolean canMove;

    public Skeleton(Cell cell) {
        super(cell);
        this.canMove = true;
        cell.setSkeleton(this);
        this.setStrength(5);
        this.setHealth(20);
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
                map.getSkeleton().move(0, 1);
            } else if (randomPos == 2) {
                map.getSkeleton().move(0, -1);
            } else if (randomPos == 3) {
                map.getSkeleton().move(1, 0);
            } else {
                map.getSkeleton().move(-1, 0);
            }
        }
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
