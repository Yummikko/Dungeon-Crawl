package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {
    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void moveUpdatesCells() {
        Player player = new Player(gameMap.getCell(1, 1));
        player.setName("Robert");
        player.move(Direction.SOUTH);

        assertEquals(1, player.getX());
        assertEquals(2, player.getY());
        assertNull(gameMap.getCell(1, 1).getActor());
        assertEquals(player, gameMap.getCell(1, 2).getActor());
    }

    @Test
    void cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setType(CellType.WALL);
        Player player = new Player(gameMap.getCell(1, 1));
        player.setName("Robert");
        player.move(Direction.EAST);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    // test niepotrzebny(zwykly gracz nie moze przejsc przez sciane za ktora jest out of map)
//    @Test
//    void cannotMoveOutOfMap() {
//        gameMap.getCell(2, 1).setType(CellType.EMPTY);
//        Player player = new Player(gameMap.getCell(1, 1));
//        player.setName("Robert");
//        player.move(Direction.EAST);
//
//
//        assertEquals(1, player.getX());
//        assertEquals(1, player.getY());
//    }

    @Test
    void cannotMoveIntoAnotherActor() {
        Player player = new Player(gameMap.getCell(1, 1));
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        player.setName("Robert");
        player.move(Direction.EAST);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
        assertEquals(2, skeleton.getX());
        assertEquals(1, skeleton.getY());
    }
}