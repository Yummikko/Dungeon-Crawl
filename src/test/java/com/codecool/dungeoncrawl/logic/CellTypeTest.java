package com.codecool.dungeoncrawl.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CellTypeTest {

    Cell cell;

    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @BeforeEach
    void setup() {
        map = new GameMap(3, 3, CellType.FLOOR);
        cell = map.getCell(1, 1);
    }

    @Test
    void shouldReturnFloor() {
        String expectedTileName = "floor";
        assertEquals(expectedTileName, cell.getTileName());
    }

    @Test
    void shouldReturnStairs() {
        cell.setType(CellType.STAIRS);
        String expectedTileName = "stairs";
        assertEquals(expectedTileName, cell.getTileName());
    }

    @Test
    void shouldReturnWater() {
        cell.setType(CellType.WATER);
        String expectedTileName = "water";
        assertEquals(expectedTileName, cell.getTileName());
    }

    @Test
    void shouldReturnSkull() {
        cell.setType(CellType.SKULL);
        String expectedTileName = "skull";
        assertEquals(expectedTileName, cell.getTileName());
    }

    @Test
    void shouldReturnCrown() {
        cell.setType(CellType.CROWN);
        String expectedTileName = "crown";
        assertEquals(expectedTileName, cell.getTileName());
    }

    @Test
    void shouldReturnWall() {
        cell.setType(CellType.WALL);
        String expectedTileName = "wall";
        assertEquals(expectedTileName, cell.getTileName());
    }
}
