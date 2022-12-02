package com.codecool.dungeoncrawl.logic.json;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
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
    private static GameJSONToMap gameLoader = new GameJSONToMap();
    public MapLoaderJSON() {

    }
    //load the map like before but take account of new data and set them with correct position
    public static GameMap loadMapJSON(String mapname) {
        InputStream is = MapLoader.class.getResourceAsStream(mapname);

        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        List<Item> items = new ArrayList<>();
        map.setItems(items);

        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
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
                        for (int i = 0; i < gameLoader.enemiesList.size(); i++) {
                            EnemyModel enemy = gameLoader.enemiesList.get(i);
                            if (enemy.getEnemyName().toLowerCase() == "skeleton") {
                                cell = new Cell(map, enemy.getX(), enemy.getY(), CellType.FLOOR);
                                Skeleton skeleton = new Skeleton(cell);
                                skeleton.setHealth(enemy.getHp());
                                map.addActor(skeleton);
                            }
                        }
                        break;
                    case 'd':
                        cell.setType(CellType.WALL);
                        for (int i = 0; i < gameLoader.openedDoors.size(); i++) {
                            OpenDoorModel openDoor = gameLoader.openedDoors.get(i);
                            if(y == openDoor.getY() && x == openDoor.getX())
                                new OpenDoor(cell);
                            else
                                new NormalDoor(cell);
                        }
                        break;
                    case 'k':
                        for (int i = 0; i < gameLoader.itemsOnMap.size(); i++) {
                            ItemModel item = gameLoader.itemsOnMap.get(i);
                            if(item.getItemName().toLowerCase() == "key") {
                                cell.setType(CellType.FLOOR);
                                items.add(new Key(cell));
                            }
                        }
                        break;
                    case 'p':
                        for (int i = 0; i < gameLoader.itemsOnMap.size(); i++) {
                            ItemModel item = gameLoader.itemsOnMap.get(i);
                            if(item.getItemName().toLowerCase() == "poison") {
                                cell.setType(CellType.FLOOR);
                                items.add(new Poison(cell));
                            }
                        }
                        break;
                    case 'z':
                        for (int i = 0; i < gameLoader.itemsOnMap.size(); i++) {
                            ItemModel item = gameLoader.itemsOnMap.get(i);
                            if(item.getItemName().toLowerCase() == "shield") {
                                cell.setType(CellType.FLOOR);
                                items.add(new Shield(cell));
                            }
                        }
                        break;
                    case 'o':
                        for (int i = 0; i < gameLoader.enemiesList.size(); i++) {
                            EnemyModel enemy = gameLoader.enemiesList.get(i);
                            if (enemy.getEnemyName().toLowerCase() == "octopus") {
                                cell.setType(CellType.FLOOR);
                                Octopus octopus = new Octopus(cell);
                                octopus.setHealth(enemy.getHp());
                                map.addActor(octopus);
                            }
                        }
                        break;
                    case 'a':
                        for (int i = 0; i < gameLoader.enemiesList.size(); i++) {
                            EnemyModel enemy = gameLoader.enemiesList.get(i);
                            if (enemy.getEnemyName().toLowerCase() == "spider") {
                                cell.setType(CellType.FLOOR);
                                Spider spider = new Spider(cell);
                                spider.setHealth(enemy.getHp());
                                map.addActor(spider);
                            }
                        }
                        break;
                    case '@':
                        for (int i = 0; i < gameLoader.playerData.size(); i++) {
                            PlayerModel player = gameLoader.playerData.get(i);
                            Cell newCell = new Cell(map, player.getX(), player.getY(), CellType.FLOOR);
                            Player nextPlayer = new Player(newCell);
                            map.setPlayer(nextPlayer);
                            nextPlayer.setName(player.getPlayerName());
                            nextPlayer.setHealth(player.getHp());
                            nextPlayer.setStrength(player.getStrength());
                            for (int j = 0; j < gameLoader.inventoryPlayer.size(); j++) {
                                InventoryModel item = gameLoader.inventoryPlayer.get(j);
                                if (item.getItem().toLowerCase().contains("axe"))
                                    nextPlayer.getInventory().add(new Axe(null));
                                else if (item.getItem().toLowerCase().contains("shield"))
                                    nextPlayer.getInventory().add(new Shield(null));
                                else if (item.getItem().toLowerCase().contains("weapon"))
                                    nextPlayer.getInventory().add(new Weapon(null));
                                else if (item.getItem().toLowerCase().contains("food"))
                                    nextPlayer.getInventory().add(new Food(null));
                                else if (item.getItem().toLowerCase().contains("poison"))
                                    nextPlayer.getInventory().add(new Poison(null));
                                else if (item.getItem().toLowerCase().contains("key"))
                                    nextPlayer.getInventory().add(new Key(null));
                                else if (item.getItem().toLowerCase().contains("crown"))
                                    nextPlayer.getInventory().add(new Crown(null));
                            }
                        }
                        break;
                    case 'f':
                        for (int i = 0; i < gameLoader.itemsOnMap.size(); i++) {
                            ItemModel item = gameLoader.itemsOnMap.get(i);
                            if(item.getItemName().toLowerCase() == "food") {
                                cell.setType(CellType.FLOOR);
                                items.add(new Food(cell));
                            }
                        }
                        break;
                    case 'w':
                        for (int i = 0; i < gameLoader.itemsOnMap.size(); i++) {
                            ItemModel item = gameLoader.itemsOnMap.get(i);
                            if(item.getItemName().toLowerCase() == "weapon") {
                                cell.setType(CellType.FLOOR);
                                items.add(new Weapon(cell));
                            }
                        }
                        break;
                    case 'v':
                        for (int i = 0; i < gameLoader.itemsOnMap.size(); i++) {
                            ItemModel item = gameLoader.itemsOnMap.get(i);
                            if(item.getItemName().toLowerCase() == "axe") {
                                cell.setType(CellType.FLOOR);
                                items.add(new Axe(cell));
                            }
                        }
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
                        for (int i = 0; i < gameLoader.itemsOnMap.size(); i++) {
                            ItemModel item = gameLoader.itemsOnMap.get(i);
                            if(item.getItemName().toLowerCase() == "crown") {
                                cell.setType(CellType.FLOOR);
                                items.add(new Crown(cell));
                            }
                        }
                        break;
                    case 'b':
                        cell.setType(CellType.FLOOR);
                        new Bridge(cell);
                        break;
                    case 'l':
                        for (int i = 0; i < gameLoader.enemiesList.size(); i++) {
                            EnemyModel enemy = gameLoader.enemiesList.get(i);
                            if (enemy.getEnemyName().toLowerCase() == "lich") {
                                Cell newCell = new Cell(map, enemy.getX(), enemy.getY(), CellType.FLOOR);
                                Lich lich = new Lich(newCell);
                                lich.setHealth(enemy.getHp());
                                map.addActor(lich);
                            }
                        }
                        break;
                    case 'x':
                        for (int i = 0; i < gameLoader.enemiesList.size(); i++) {
                            EnemyModel enemy = gameLoader.enemiesList.get(i);
                            if (enemy.getEnemyName().toLowerCase() == "darklord") {
                                Cell newCell = new Cell(map, enemy.getX(), enemy.getY(), CellType.FLOOR);
                                DarkLord darkLord = new DarkLord(newCell);
                                darkLord.setHealth(enemy.getHp());
                                map.addActor(darkLord);
                            }
                        }
                        break;
                    case 'g':
                        for (int i = 0; i < gameLoader.enemiesList.size(); i++) {
                            EnemyModel enemy = gameLoader.enemiesList.get(i);
                            if (enemy.getEnemyName().toLowerCase() == "phantom") {
                                Cell newCell = new Cell(map, enemy.getX(), enemy.getY(), CellType.FLOOR);
                                Phantom phantom = new Phantom(newCell);
                                phantom.setHealth(enemy.getHp());
                                map.addActor(phantom);
                            }
                        }
                        break;
                    default:
                        throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                }
            }
        }
        return map;
    }
}
