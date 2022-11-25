package com.codecool.dungeoncrawl.logic.enviroment;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import org.junit.jupiter.api.Test;

import static com.codecool.dungeoncrawl.Main.map;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WaterTest {

    @Test
    void testGetTileName() {
        var waterUnderTest = new Water(new Cell(map, 1, 1, CellType.FLOOR));
        assertEquals("water", waterUnderTest.getTileName());
    }
}
