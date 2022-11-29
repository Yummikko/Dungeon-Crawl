package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    GameMap gameMap = new GameMap(3, 3, CellType.FLOOR);

    @Test
    void testPickUpItem() {
        Player player = new Player(gameMap.getCell(1, 1));
        Item item = new Weapon(gameMap.getCell(1, 1));
        player.pickUpItem();
        assertTrue(player.getInventory().contains(item));
        assertNull(gameMap.getCell(1, 1).getItem());
    }

    @Test
    void testIfStrengthIncreaseByTwoAfterPickingUpWeapon() {
        Player player = new Player(gameMap.getCell(1, 1));
        Item item = new Weapon(gameMap.getCell(1, 1));
        int initialStrength = player.getStrength();
        player.pickUpItem();
        assertEquals(initialStrength + 2, player.getStrength());

    }

    @Test
    void testIfStrengthIncreaseByTenAfterPickingUpShield() {
        Player player = new Player(gameMap.getCell(1, 1));
        Item item = new Shield(gameMap.getCell(1, 1));
        int initialStrength = player.getStrength();
        player.pickUpItem();
        assertEquals(initialStrength + 10, player.getStrength());

    }

    @Test
    void testIfHealthIncreaseByThreeAfterPickingUpFood() {
        Player player = new Player(gameMap.getCell(1, 1));
        Item item = new Food(gameMap.getCell(1, 1));
        int initialHealth = player.getHealth();
        player.pickUpItem();
        assertEquals(initialHealth + 3, player.getHealth());

    }

    @Test
    void testIfHealthDecreaseByThreeAfterPickingUpPoison() {
        Player player = new Player(gameMap.getCell(1, 1));
        Item item = new Poison(gameMap.getCell(1, 1));
        int initialHealth = player.getHealth();
        player.pickUpItem();
        assertEquals(initialHealth - 3, player.getHealth());

    }

    @Test
    void testIfKeyIsRemoved() {
        Player player = new Player(gameMap.getCell(1, 1));
        Item item = new Key(gameMap.getCell(1, 1));
        player.pickUpItem();
        player.removeKey();
        assertFalse(player.getInventory().contains(item));
    }

}
