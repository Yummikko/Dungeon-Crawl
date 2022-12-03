package com.codecool.dungeoncrawl.logic.json;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.doors.NormalDoor;
import com.codecool.dungeoncrawl.logic.doors.OpenDoor;
import com.codecool.dungeoncrawl.logic.enviroment.*;
import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.model.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MapLoaderJSON {
    public static List<GameMap> maps = new ArrayList<>();
    public MapLoaderJSON() {}
    //load the map like before but take account of new data and set them with correct position
    public static GameMap loadMapJSON(String mapName) {
        InputStream input = MapLoaderJSON.class.getResourceAsStream("/" + mapName);
        Scanner scannerJSON = new Scanner(input);
        int width = scannerJSON.nextInt();
        int height = scannerJSON.nextInt();

        scannerJSON.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        List<Item> items = new ArrayList<>();
        map.setItems(items);

        for (int y = 0; y < height; y++) {
            String line = scannerJSON.nextLine();
            for (int x = 0; x < line.length(); x++) {
                Cell cell = map.getCell(x, y);
                switch (line.charAt(x)) {
                    case ' ':
                        cell.setType(CellType.EMPTY);
                        break;
                    case '#':
                        cell.setType(CellType.WALL);
                        break;
                    case '.':
                        cell.setType(CellType.FLOOR);
                        break;
                    case 's':
                        for (int i = 0; i < GameJSONToMap.enemiesList.size(); i++) {
                            EnemyModel enemy = GameJSONToMap.enemiesList.get(i);
                            if (enemy.getEnemyName().toLowerCase().contains("skeleton")) {
                                cell = new Cell(map, enemy.getX(), enemy.getY(), CellType.FLOOR);
                                Skeleton skeleton = new Skeleton(cell);
                                skeleton.setHealth(enemy.getHp());
                                map.addActor(skeleton);
                                GameJSONToMap.enemiesList.remove(enemy);
                            }
                        }
                        cell.setType(CellType.FLOOR);
                        break;
                    case 'd':
                        cell.setType(CellType.WALL);
                        if (GameJSONToMap.openedDoors.size() > 0)
                            for (int i = 0; i < GameJSONToMap.openedDoors.size(); i++) {
                                OpenDoorModel openDoor = GameJSONToMap.openedDoors.get(i);
                                if(y == openDoor.getY() && x == openDoor.getX())
                                    new OpenDoor(cell);
                            }
                        else
                            new NormalDoor(cell);
                        break;
                    case 'k':
                        for (int i = 0; i < GameJSONToMap.itemsOnMap.size(); i++) {
                            ItemModel item = GameJSONToMap.itemsOnMap.get(i);
                            if(item.getItemName().toLowerCase().contains("key")) {
                                if (cell.getX() == item.getX() && cell.getY() == item.getY()) {
                                    cell.setType(CellType.FLOOR);
                                    items.add(new Key(cell));
                                }
                            }
                        }
                        cell.setType(CellType.FLOOR);
                        break;
                    case 'p':
                        for (int i = 0; i < GameJSONToMap.itemsOnMap.size(); i++) {
                            ItemModel item = GameJSONToMap.itemsOnMap.get(i);
                            if(item.getItemName().toLowerCase().contains("poison")) {
                                if (cell.getX() == item.getX() && cell.getY() == item.getY()) {
                                    cell.setType(CellType.FLOOR);
                                    items.add(new Poison(cell));
                                }
                                
                            }
                        }
                        cell.setType(CellType.FLOOR);
                        break;
                    case 'z':
                        for (int i = 0; i < GameJSONToMap.itemsOnMap.size(); i++) {
                            ItemModel item = GameJSONToMap.itemsOnMap.get(i);
                            if(item.getItemName().toLowerCase().contains("shield")) {
                                if (cell.getX() == item.getX() && cell.getY() == item.getY()) {
                                    cell.setType(CellType.FLOOR);
                                    items.add(new Shield(cell));
                                }
                            }
                        }
                        cell.setType(CellType.FLOOR);
                        break;
                    case 'o':
                        for (int i = 0; i < GameJSONToMap.enemiesList.size(); i++) {
                            EnemyModel enemy = GameJSONToMap.enemiesList.get(i);
                            if (cell.getX() == enemy.getX() && cell.getY() == enemy.getY()) {
                                if (enemy.getEnemyName().toLowerCase().contains("octopus")) {
                                    cell.setType(CellType.FLOOR);
                                    Octopus octopus = new Octopus(cell);
                                    octopus.setHealth(enemy.getHp());
                                    map.addActor(octopus);
                                }
                            }
                        }
                        cell.setType(CellType.FLOOR);
                        break;
                    case 'a':
                        for (int i = 0; i < GameJSONToMap.enemiesList.size(); i++) {
                            EnemyModel enemy = GameJSONToMap.enemiesList.get(i);
                            if (enemy.getEnemyName().toLowerCase().contains("spider")) {
                                if (cell.getX() == enemy.getX() && cell.getY() == enemy.getY()) {
                                    cell.setType(CellType.FLOOR);
                                    Spider spider = new Spider(cell);
                                    spider.setHealth(enemy.getHp());
                                    map.addActor(spider);
                                }
                            }
                        }
                        cell.setType(CellType.FLOOR);
                        break;
                    case '@':
                        for (int i = 0; i < GameJSONToMap.playerData.size(); i++) {
                            PlayerModel player = GameJSONToMap.playerData.get(i);
                            Cell newCell = new Cell(map, player.getX(), player.getY(), CellType.FLOOR);
                            Player lastPlayer = new Player(newCell);
                            map.setPlayer(lastPlayer);
                            lastPlayer.setName(player.getPlayerName());
                            lastPlayer.setHealth(player.getHp());
                            lastPlayer.setStrength(player.getStrength());
                            lastPlayer.getInventory().clear();
                            for (int j = 0; j < GameJSONToMap.inventoryPlayer.size(); j++) {
                                InventoryModel item = GameJSONToMap.inventoryPlayer.get(j);
                                if (item.getItem().toLowerCase().contains("axe")) {
                                    lastPlayer.getInventory().add(new Axe(newCell));
                                }
                                else if (item.getItem().toLowerCase().contains("shield")) {
                                    lastPlayer.getInventory().add(new Shield(newCell));
                                }
                                else if (item.getItem().toLowerCase().contains("weapon")) {
                                    lastPlayer.getInventory().add(new Weapon(newCell));
                                }
                                else if (item.getItem().toLowerCase().contains("food")) {
                                    lastPlayer.getInventory().add(new Food(newCell));
                                }
                                else if (item.getItem().toLowerCase().contains("poison")) {
                                    lastPlayer.getInventory().add(new Poison(newCell));
                                }
                                else if (item.getItem().toLowerCase().contains("key")) {
                                    lastPlayer.getInventory().add(new Key(newCell));
                                }
                                else if (item.getItem().toLowerCase().contains("crown")) {
                                    lastPlayer.getInventory().add(new Crown(newCell));
                                }
                            }
                        }
                        cell.setType(CellType.FLOOR);
                        break;
                    case 'f':
                        for (int i = 0; i < GameJSONToMap.itemsOnMap.size(); i++) {
                            ItemModel item = GameJSONToMap.itemsOnMap.get(i);
                            if(item.getItemName().toLowerCase().contains("food")) {
                                if (cell.getX() == item.getX() && cell.getY() == item.getY()) {
                                    cell.setType(CellType.FLOOR);
                                    items.add(new Food(cell));
                                }
                            }
                        }
                        cell.setType(CellType.FLOOR);
                        break;
                    case 'w':
                        for (int i = 0; i < GameJSONToMap.itemsOnMap.size(); i++) {
                            ItemModel item = GameJSONToMap.itemsOnMap.get(i);
                            if(item.getItemName().toLowerCase().contains("weapon")) {
                                if (cell.getX() == item.getX() && cell.getY() == item.getY()) {
                                    cell.setType(CellType.FLOOR);
                                    items.add(new Weapon(cell));
                                }
                            }
                        }
                        cell.setType(CellType.FLOOR);
                        break;
                    case 'v':
                        for (int i = 0; i < GameJSONToMap.itemsOnMap.size(); i++) {
                            ItemModel item = GameJSONToMap.itemsOnMap.get(i);
                            if(item.getItemName().toLowerCase().contains("axe")) {
                                if (cell.getX() == item.getX() && cell.getY() == item.getY()) {
                                    cell.setType(CellType.FLOOR);
                                    items.add(new Axe(cell));
                                }
                            }
                        }
                        cell.setType(CellType.FLOOR);
                        break;
                    case 'n':
                        cell.setType(CellType.PORTAL);
                        new Portal(cell);
                        break;
                    case 'r':
                        cell.setType(CellType.WALL);
                        new Water(cell);
                        break;
                    case 't':
                        cell.setType(CellType.STAIRS);
                        new Stairs(cell);
                        break;
                    case 'y':
                        cell.setType(CellType.LADDER);
                        new Ladder(cell);
                        break;
                    case 'u':
                        cell.setType(CellType.SKULL);
                        new Skull(cell);
                        break;
                    case 'c':
                        for (int i = 0; i < GameJSONToMap.itemsOnMap.size(); i++) {
                            ItemModel item = GameJSONToMap.itemsOnMap.get(i);
                            if(item.getItemName().toLowerCase().contains("crown")) {
                                if (cell.getX() == item.getX() && cell.getY() == item.getY()) {
                                    cell.setType(CellType.FLOOR);
                                    items.add(new Crown(cell));
                                }
                            }
                        }
                        cell.setType(CellType.FLOOR);
                        break;
                    case 'b':
                        cell.setType(CellType.FLOOR);
                        new Bridge(cell);
                        break;
                    case 'l':
                        for (int i = 0; i < GameJSONToMap.enemiesList.size(); i++) {
                            EnemyModel enemy = GameJSONToMap.enemiesList.get(i);
                            if (enemy.getEnemyName().toLowerCase().contains("lich")) {
                                Cell newCell = new Cell(map, enemy.getX(), enemy.getY(), CellType.FLOOR);
                                Lich lich = new Lich(newCell);
                                lich.setHealth(enemy.getHp());
                                map.addActor(lich);
                                GameJSONToMap.enemiesList.remove(enemy);
                            }
                        }
                        cell.setType(CellType.FLOOR);
                        break;
                    case 'x':
                        for (int i = 0; i < GameJSONToMap.enemiesList.size(); i++) {
                            EnemyModel enemy = GameJSONToMap.enemiesList.get(i);
                            if (enemy.getEnemyName().toLowerCase().contains("darklord")) {
                                Cell newCell = new Cell(map, enemy.getX(), enemy.getY(), CellType.FLOOR);
                                DarkLord darkLord = new DarkLord(newCell);
                                darkLord.setHealth(enemy.getHp());
                                map.addActor(darkLord);
                                GameJSONToMap.enemiesList.remove(enemy);
                            }
                        }
                        cell.setType(CellType.FLOOR);
                        break;
                    case 'g':
                        for (int i = 0; i < GameJSONToMap.enemiesList.size(); i++) {
                            EnemyModel enemy = GameJSONToMap.enemiesList.get(i);
                            if (enemy.getEnemyName().toLowerCase().contains("phantom")) {
                                Cell newCell = new Cell(map, enemy.getX(), enemy.getY(), CellType.FLOOR);
                                Phantom phantom = new Phantom(newCell);
                                phantom.setHealth(enemy.getHp());
                                map.addActor(phantom);
                                GameJSONToMap.enemiesList.remove(enemy);
                            }
                        }
                        cell.setType(CellType.FLOOR);
                        break;
                    case '%':
                        cell.setType(CellType.FLOOR);
                        break;
                    default:
                        throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                }
            }
        }
        return map;
    }
}
