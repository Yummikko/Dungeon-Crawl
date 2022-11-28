package com.codecool.dungeoncrawl.logic.enviroment;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import org.junit.jupiter.api.Test;

import static com.codecool.dungeoncrawl.graphics.GameMenu.map;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BridgeTest {
    @Test
    void testGetTileName() {
        var bridgeUnderTest = new Bridge(new Cell(map, 1, 1, CellType.FLOOR));
        assertEquals("bridge", bridgeUnderTest.getTileName());
    }
}
