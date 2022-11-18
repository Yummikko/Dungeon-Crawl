package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.List;
import java.util.Random;

public class DarkLord extends Skeleton {

    public DarkLord(Cell cell) {
        super(cell);
    }

    public static void bossMoves(List<DarkLord> darkLords, GameMap map, Player player) {
        Random rand = new Random();
        int min = 0;
        int max = 5;
        for (int i = 0; i < darkLords.size(); i++) {
            int randomPos = rand.nextInt(max - min) + min;
            DarkLord a = darkLords.get(i);
            map.setDarkLord(a);
            if (randomPos == 1) {
                map.getDarkLord().randomMove(0, 1);
            } else if (randomPos == 2) {
                map.getDarkLord().randomMove(0, -1);
            } else if (randomPos == 3) {
                map.getDarkLord().randomMove(1, 0);
            } else if (randomPos == 4) {
                map.getDarkLord().randomMove(-1, 0);
            } else if (randomPos == 5)
                summonPhantoms(player, map, map.getDarkLord());
        }
    }

    public static void summonPhantoms(Player player, GameMap map, DarkLord darkLord) {
        System.out.println("summoning");
    }

    @Override
    public String getTileName() {
        return "darkLord";
    }
}
