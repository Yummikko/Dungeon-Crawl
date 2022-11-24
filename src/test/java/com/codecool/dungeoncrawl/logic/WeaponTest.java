package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.items.Weapon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class WeaponTest {
    GameMap map = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void constructorTestPositive(){
        Weapon weaponUnderTest = new Weapon(new Cell(map, 1, 1, CellType.WEAPON));
        CellType expectedCellType = CellType.WEAPON;
        assertEquals(expectedCellType, weaponUnderTest.getCell().getType());
    }

    @Test
    void constructorTestNegative(){
        Weapon weaponUnderTest = new Weapon(new Cell(map, 1, 1, CellType.WALL));
        CellType expectedCellType = CellType.WEAPON;
        assertNotEquals(expectedCellType, weaponUnderTest.getCell().getType());
    }

    @Test
    void testGetTileNamePositive(){
        Weapon weaponUnderTest = new Weapon(new Cell(map, 1, 1, CellType.WEAPON));
        assertEquals("weapon", weaponUnderTest.getTileName());
    }

    @Test
    void testGetTileNameNegative(){
        Weapon weaponUnderTest = new Weapon(new Cell(map, 1, 1, CellType.WEAPON));
        assertNotEquals("sword", weaponUnderTest.getTileName());
    }
}