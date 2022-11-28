package com.codecool.dungeoncrawl.logic.enviroment;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import org.junit.jupiter.api.Test;

import static com.codecool.dungeoncrawl.graphics.GameMenu.map;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StairsTest {

    @Test
    void testGetTileName() {
        var stairsUnderTest = new Stairs(new Cell(map, 1, 1, CellType.FLOOR));
        assertEquals("stairs", stairsUnderTest.getTileName());
    }
}
