package com.codecool.dungeoncrawl.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void getNeighbor() {
        Cell cell = map.getCell(1,0);
        Cell neighbor = cell.getNeighbour(-1, 0);
        assertEquals(0, neighbor.getX());
        assertEquals(1, neighbor.getY());
    }

    @Test
    void cellOnEdgeHasNoNeighbor() {
        Cell cell = map.getCell(1, 0);
        assertEquals(null, cell.getNeighbour(0, -1));

        cell = map.getCell(1, 2);
        assertEquals(null, cell.getNeighbour(0, 1));
    }

    @Test
    void cellHasNoItem() {
        Cell cell = map.getCell(0, 1);
        assertNull(cell.getItem());
    }
}