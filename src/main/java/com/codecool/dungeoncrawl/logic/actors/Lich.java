package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.util.SoundUtils;

import java.util.concurrent.ThreadLocalRandom;

public class Lich extends Actor implements Enemy{

    static final ThreadLocalRandom rand = ThreadLocalRandom.current();

    public Lich(Cell cell) {
        super(cell);
        this.setStrength(4);
        this.setHealth(20);
    }

    @Override
    public void move(GameMap map) {
        int randomNum = rand.nextInt(100);
        if (randomNum < 85) {
            moveRandomly();
        } else if (map.getPlayer().getX() + 1 < map.getWidth()) {
            teleportMonster(map.getPlayer(), map);
        }
    }

    public void teleportMonster(Player player, GameMap map) {
        int min = -1;
        int max = 1;
        int randomY = rand.nextInt(min, max);
        int randomX = rand.nextInt(min, max);
        int x = player.getX();
        int y = player.getY();
        Cell nextCell = map.getCell(x + randomX, y + randomX);
        if (x + randomX < map.getWidth() || y + randomX < map.getHeight()) {
            try {
                if (isNotWalkable(nextCell) || randomX == 0 || randomY == 0) {
                    return;
                } else if (nextCell.getNeighbour(randomY, randomX).getActor() != null) {
                    return;
                }
                else {
                    moveActor(nextCell);
                    SoundUtils.playSound(SoundUtils.TELEPORT, 0.8f);
                }
                if (isEnemy(nextCell.getNeighbour(randomY, randomX))
                        && nextCell.getActor() instanceof Player) {
                    nextCell.getActor().fightWithMonster(this);
                }
            } catch (NullPointerException e) {
                System.out.println("getPlayer() is null, cannot move there");
            }
        }
    }

    @Override
    public String getTileName() {
        return "lich";
    }

    public void moveRandomly() {
        Direction direction = Direction.getRandomDirection();
        super.move(direction);
    }
}
