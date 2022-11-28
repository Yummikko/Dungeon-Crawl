package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.items.Key;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class KeyTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void constructorTestPositive(){
        Key keyUnderTest = new Key(new Cell(map, 1, 1, CellType.KEY));
        CellType expectedCellType = CellType.KEY;
        assertEquals(expectedCellType, keyUnderTest.getCell().getType());
    }

    @Test
    void constructorTestNegative(){
        Key keyUnderTest = new Key(new Cell(map, 1, 1, CellType.WALL));
        CellType expectedCellType = CellType.KEY;
        assertNotEquals(expectedCellType, keyUnderTest.getCell().getType());
    }

    @Test
    void testGetTileNamePositive(){
        Key keyUnderTest = new Key(new Cell(map, 1, 1, CellType.KEY));
        assertEquals("key", keyUnderTest.getTileName());
    }

    @Test
    void testGetTileNameNegative(){
        Key keyUnderTest = new Key(new Cell(map, 1, 1, CellType.KEY));
        assertNotEquals("sword", keyUnderTest.getTileName());
    }
}
