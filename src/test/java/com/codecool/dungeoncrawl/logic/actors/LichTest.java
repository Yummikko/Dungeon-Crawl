package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import org.junit.jupiter.api.Test;

import static com.codecool.dungeoncrawl.logic.actors.Lich.rand;
import static org.junit.jupiter.api.Assertions.*;

public class LichTest {
    GameMap gameMap = new GameMap(5, 5, CellType.FLOOR);
    int randomNum = rand.nextInt(100);

    public void move(GameMap map, Lich lich, Player player) {
        if (randomNum < 85) {
            lich.moveRandomly();
        } else if (player.getX() + 1 < map.getWidth()) {
            lich.teleportMonster(player, map);
        }
    }

    @Test
    void willItMoveRandomly() {
        Lich lich = new Lich(gameMap.getCell(1, 1));
        Player player = new Player(gameMap.getCell(2, 2));
        move(gameMap, lich, player);
        for (int x = 0; x < gameMap.getWidth(); x++) {
            for (int y = 0; y < gameMap.getHeight(); y++) {
                Cell nextCell = new Cell(gameMap, x, y, CellType.FLOOR);
                if(nextCell.getActor() != null) {
                    assertTrue(gameMap.getCell(x, y).getActor().equals(nextCell.getActor()));
                }
            }
        }
    }
}
