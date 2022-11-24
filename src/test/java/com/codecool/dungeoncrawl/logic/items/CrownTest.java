package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.items.Crown;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CrownTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void constructorTestPositive(){
        Crown crownUnderTest = new Crown(new Cell(map, 1, 1, CellType.CROWN));
        CellType expectedCellType = CellType.CROWN;
        assertEquals(expectedCellType, crownUnderTest.getCell().getType());
    }

    @Test
    void constructorTestNegative(){
        Crown crownUnderTest = new Crown(new Cell(map, 1, 1, CellType.WALL));
        CellType expectedCellType = CellType.CROWN;
        assertNotEquals(expectedCellType, crownUnderTest.getCell().getType());
    }

    @Test
    void testGetTileNamePositive(){
        Crown crownUnderTest = new Crown(new Cell(map, 1, 1, CellType.CROWN));
        assertEquals("crown", crownUnderTest.getTileName());
    }

    @Test
    void testGetTileNameNegative(){
        Crown crownUnderTest = new Crown(new Cell(map, 1, 1, CellType.CROWN));
        assertNotEquals("sword", crownUnderTest.getTileName());
    }
}
