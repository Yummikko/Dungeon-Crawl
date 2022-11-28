package com.codecool.dungeoncrawl.logic;


import com.codecool.dungeoncrawl.logic.items.Shield;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ShieldTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void constructorTestPositive(){
        Shield shieldUnderTest = new Shield(new Cell(map, 1, 1, CellType.SHIELD));
        CellType expectedCellType = CellType.SHIELD;
        assertEquals(expectedCellType, shieldUnderTest.getCell().getType());
    }

    @Test
    void constructorTestNegative(){
        Shield shieldUnderTest = new Shield(new Cell(map, 1, 1, CellType.WALL));
        CellType expectedCellType = CellType.SHIELD;
        assertNotEquals(expectedCellType, shieldUnderTest.getCell().getType());
    }

    @Test
    void testGetTileNamePositive(){
        Shield shieldUnderTest = new Shield(new Cell(map, 1, 1, CellType.SHIELD));
        assertEquals("shield", shieldUnderTest.getTileName());
    }

    @Test
    void testGetTileNameNegative(){
        Shield shieldUnderTest = new Shield(new Cell(map, 1, 1, CellType.SHIELD));
        assertNotEquals("sword", shieldUnderTest.getTileName());
    }
}