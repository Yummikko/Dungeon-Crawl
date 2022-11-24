package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.items.Food;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class FoodTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void constructorTestPositive(){
        Food foodUnderTest = new Food(new Cell(map, 1, 1, CellType.FOOD));
        CellType expectedCellType = CellType.FOOD;
        assertEquals(expectedCellType, foodUnderTest.getCell().getType());
    }

    @Test
    void constructorTestNegative(){
        Food foodUnderTest = new Food(new Cell(map, 1, 1, CellType.WALL));
        CellType expectedCellType = CellType.FOOD;
        assertNotEquals(expectedCellType, foodUnderTest.getCell().getType());
    }

    @Test
    void testGetTileNamePositive(){
        Food foodUnderTest = new Food(new Cell(map, 1, 1, CellType.FOOD));
        assertEquals("food", foodUnderTest.getTileName());
    }

    @Test
    void testGetTileNameNegative(){
        Food foodUnderTest = new Food(new Cell(map, 1, 1, CellType.FOOD));
        assertNotEquals("sword", foodUnderTest.getTileName());
    }
}
