package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.List;
import java.util.Random;

public class Phantom extends Skeleton{
    public Phantom(Cell cell) {
        super(cell);
        cell.setPhantom(this);
        this.setHealth(8);
        this.setStrength(3);
    }

    public static void movements(List<Phantom> phantoms, GameMap map) {
        Random rand = new Random();
        int min = 0;
        int max = 4;
        for (int i = 0; i < phantoms.size(); i++) {
            int randomPos = rand.nextInt(max - min) + min;
            Phantom a = phantoms.get(i);
            map.setPhantom(a);
            if (randomPos == 1) {
                map.getPhantom().randomMove(0, 1);
            } else if (randomPos == 2) {
                map.getPhantom().randomMove(0, -1);
            } else if (randomPos == 3) {
                map.getPhantom().randomMove(1, 0);
            } else {
                map.getPhantom().randomMove(-1, 0);
            }
        }
    }

    public void dissapear() {
        cell.setActor(null);
    }

    @Override
    public String getTileName() {
        return "phantom";
    }
}
