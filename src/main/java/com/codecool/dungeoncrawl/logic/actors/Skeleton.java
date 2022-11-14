package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import java.util.List;
import java.util.Random;


public class Skeleton extends Actor {

    public Skeleton(Cell cell) {
        super(cell);
        this.setStrength(3);
        this.setHealth(5);
    }

    public static void monsterMove(List<Skeleton> skeletons, GameMap map) {
        Random rand = new Random();
        int min = 0;
        int max = 4;
        for (int i = 0; i < skeletons.size(); i++) {
            int randomPos = rand.nextInt(max - min) + min;
            System.out.println(skeletons.size());
            Skeleton a = skeletons.get(i);
            cell.setSkeleton(a);
            if (randomPos == 1) {
                cell.getSkeleton().move(0, 1);
            } else if (randomPos == 2) {
                cell.getSkeleton().move(0, -1);
            } else if (randomPos == 3) {
                cell.getSkeleton().move(1, 0);
            } else {
                cell.getSkeleton().move(-1, 0);
            }
        }
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
