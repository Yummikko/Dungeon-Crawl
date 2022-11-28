package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.items.Poison;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PoisonTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void constructorTestPositive(){
        Poison poisonUnderTest = new Poison(new Cell(map, 1, 1, CellType.POISON));
        CellType expectedCellType = CellType.POISON;
        assertEquals(expectedCellType, poisonUnderTest.getCell().getType());
    }

    @Test
    void constructorTestNegative(){
        Poison poisonUnderTest = new Poison(new Cell(map, 1, 1, CellType.WALL));
        CellType expectedCellType = CellType.POISON;
        assertNotEquals(expectedCellType, poisonUnderTest.getCell().getType());
    }

    @Test
    void testGetTileNamePositive(){
        Poison poisonUnderTest = new Poison(new Cell(map, 1, 1, CellType.POISON));
        assertEquals("poison", poisonUnderTest.getTileName());
    }

    @Test
    void testGetTileNameNegative(){
        Poison poisonUnderTest = new Poison(new Cell(map, 1, 1, CellType.POISON));
        assertNotEquals("sword", poisonUnderTest.getTileName());
    }
}
