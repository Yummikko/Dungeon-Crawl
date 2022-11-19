package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DarkLord extends Skeleton {

    public DarkLord(Cell cell) {
        super(cell);
        cell.setDarkLord(this);
        this.setHealth(25);
        this.setStrength(7);
    }

    public static void bossMoves(List<DarkLord> darkLords, List<Phantom> phantoms, GameMap map, Player player) {
        Random rand = new Random();
        int min = 0;
        int max = 6;
        for (int i = 0; i < darkLords.size(); i++) {
            int randomPos = rand.nextInt(max - min) + min;
            System.out.println(randomPos);
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
            } else if (randomPos == 5) {
                summonPhantoms(player, map, map.getDarkLord(), rand, phantoms);
            }
        }
    }

    public static void summonPhantoms(Player player, GameMap map, DarkLord darkLord, Random rand, List<Phantom> phantoms) {
        int min = -2;
        int max = 2;
        int randomPos = rand.nextInt(max - min) + min;
        int randomPosNext = rand.nextInt(max - min) + min;
        int posX = player.getX();
        int posY = player.getY();
        Cell bossCell = darkLord.getCell();
        int bossX = bossCell.getX();
        int bossY = bossCell.getY();
        Cell cell = map.getCell(posX+randomPosNext, posY+randomPos);
        Cell nextCell = map.getCell(posX+randomPos, posY+randomPosNext);
        if (map.getPhantoms().size() < 3) {
            if (nextCell == null || nextCell.getType().equals(CellType.WALL)
                    || nextCell.getType().equals(CellType.WATER)
                    || nextCell.getType().equals(CellType.CROWN)
                    || nextCell.getType().equals(CellType.SKULL)
                    || nextCell.getActor() != null)
                return;
            else if (cell == null || cell.getType().equals(CellType.WALL)
                    || cell.getType().equals(CellType.WATER)
                    || cell.getType().equals(CellType.CROWN)
                    || cell.getType().equals(CellType.SKULL)
                    || cell.getActor() != null)
                return;
            else if ((bossX - posX >= -5) && (bossX - posX < -1) || (bossY - posY < -1) || (bossY - posY >= -5)) {
                Phantom phantom = new Phantom(cell);
                cell.setActor(phantom);
                map.getPhantoms().add(phantom);
                phantoms.add(phantom);
            }
        }
    }

    public void removePhantoms(List<Phantom> phantoms, GameMap map) {
        if (this.getHealth() <= 0) {
            for (int i = 0; i < phantoms.size(); i++) {
                Phantom phantom = phantoms.get(i);
                System.out.println("Change phantoms to null");
                phantom.dissapear();
            }
            phantoms.removeAll(phantoms);
            map.getPhantoms().removeAll(map.getPhantoms());
        }
    }

    @Override
    public String getTileName() {
        return "darkLord";
    }
}
