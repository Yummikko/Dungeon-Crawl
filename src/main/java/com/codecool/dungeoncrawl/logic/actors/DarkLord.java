package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.util.SoundUtils;
import java.util.Random;

public class DarkLord extends Actor implements Enemy {

    private final Random rand = new Random();

    public DarkLord(Cell cell) {
        super(cell);
        this.setHealth(75);
        this.setStrength(10);
    }

    @Override
    public void move(GameMap map) {
        if (rand.nextInt(6) == 5) {
            summonPhantoms(map.getPlayer(), map);
        } else {
            super.move(map);
        }
    }

    public void summonPhantoms(Player player, GameMap map) {
        int min = -2;
        int max = 2;
        int randomPos = rand.nextInt(max - min) + min;
        int randomPosNext = rand.nextInt(max - min) + min;
        int posX = player.getX();
        int posY = player.getY();
        int bossX = cell.getX();
        int bossY = cell.getY();
        Cell cell = map.getCell(posX+randomPosNext, posY+randomPos);
        Cell nextCell = map.getCell(posX+randomPos, posY+randomPosNext);
        if (map.getPhantoms().size() < 5) {
            if (nextCell == null || nextCell.getType().equals(CellType.WALL)
                    || nextCell.getType().equals(CellType.WATER)
                    || nextCell.getType().equals(CellType.CROWN)
                    || nextCell.getType().equals(CellType.SKULL)
                    || nextCell.getActor() != null)
                return;
            if (cell == null || cell.getType().equals(CellType.WALL)
                    || cell.getType().equals(CellType.WATER)
                    || cell.getType().equals(CellType.CROWN)
                    || cell.getType().equals(CellType.SKULL)
                    || cell.getActor() != null)
                return;
            if ((bossX - posX <= 5) || (posY - bossY <= 5)) {
                SoundUtils.playSound(SoundUtils.SUMMON, 0.7f);
                Phantom phantom = new Phantom(cell);
                cell.setActor(phantom);
                map.addActor(phantom);
            }
        }
    }

    public void removePhantoms(GameMap map) {
        if (this.isDead()) {
            map.getPhantoms().forEach(p -> {
                p.getCell().setActor(null);
                map.getEnemies().remove(p);
            });
        }
    }

    @Override
    public String getTileName() {
        return "darkLord";
    }

}
