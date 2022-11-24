package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import org.junit.jupiter.api.Test;

import static com.codecool.dungeoncrawl.Main.map;
import static org.junit.jupiter.api.Assertions.*;

class ShieldTest {

    @Test
    void testGetTileName() {
        var shieldUnderTest = new Shield(new Cell(map, 1, 1, CellType.SHIELD));
        assertEquals("shield", shieldUnderTest.getTileName());
    }
}