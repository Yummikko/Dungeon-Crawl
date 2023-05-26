package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Weapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    Cell cell;
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @BeforeEach
    void setup() {
        map = new GameMap(3, 3, CellType.FLOOR);
        cell = map.getCell(1, 1);
    }

    @Test
    void getNeighbor() {
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
    void shouldReturnNullIfNoItemOnCell() {
        assertNull(cell.getItem());
    }

    @Test
    void shouldReturnItemIFItemSetOnCell() {
        Item item = new Weapon(cell);
        cell.setItem(item);
        assertEquals(item, cell.getItem());
    }
}