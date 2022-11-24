package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.items.Crown;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CrownTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void constructorTestPositive(){
        Crown keyUnderTest = new Crown(new Cell(map, 1, 1, CellType.KEY));
        CellType expectedCellType = CellType.KEY;
        assertEquals(expectedCellType, keyUnderTest.getCell().getType());
    }

    @Test
    void constructorTestNegative(){
        Crown keyUnderTest = new Crown(new Cell(map, 1, 1, CellType.WALL));
        CellType expectedCellType = CellType.KEY;
        assertNotEquals(expectedCellType, keyUnderTest.getCell().getType());
    }

    @Test
    void testGetTileNamePositive(){
        Crown keyUnderTest = new Crown(new Cell(map, 1, 1, CellType.KEY));
        assertEquals("crown", keyUnderTest.getTileName());
    }

    @Test
    void testGetTileNameNegative(){
        Crown keyUnderTest = new Crown(new Cell(map, 1, 1, CellType.KEY));
        assertNotEquals("sword", keyUnderTest.getTileName());
    }
}
