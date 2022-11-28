package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

import org.junit.jupiter.api.Test;

import static com.codecool.dungeoncrawl.graphics.GameMenu.map;
import static org.junit.jupiter.api.Assertions.*;

class PoisonTest {

    @Test
    void testGetTileName(){
        var poisonUnderTest = new Poison(new Cell(map, 1, 1, CellType.FLOOR));
        assertEquals("poison", poisonUnderTest.getTileName());
    }
}