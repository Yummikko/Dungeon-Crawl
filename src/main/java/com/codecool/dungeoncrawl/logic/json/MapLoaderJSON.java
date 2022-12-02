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
                            if (enemy.getEnemyName() == "skeleton") {
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
                            if(item.getItemName() == "key") {
                                cell = new Cell(map, item.getX(), item.getY(), CellType.FLOOR);
                                items.add(new Key(cell));
                            }
                        }
                        break;
                    case 'p':
                        cell.setType(CellType.FLOOR);
                        items.add(new Poison(cell));
                        break;
                    case 'z':
                        cell.setType(CellType.FLOOR);
                        items.add(new Shield(cell));
                        break;
                    case 'o':
                        cell.setType(CellType.FLOOR);
                        map.addActor(new Octopus(cell));
                        break;
                    case 'a':
                        cell.setType(CellType.FLOOR);
                        map.addActor(new Spider(cell));
                        break;
                    case '@':
                        cell.setType(CellType.FLOOR);
                        Player nextPlayer = new Player(cell);
                        map.setPlayer(nextPlayer);
                        for (int i = 0; i < maps.size(); i++) {
                            for (int j = 1; j < maps.size() + j; j++) {
                                if (maps.size() == j) {
                                    nextPlayer.setName(maps.get(i).getPlayer().getName());
                                    nextPlayer.setInventory(maps.get(i).getPlayer().getInventory());
                                    nextPlayer.setHealth(maps.get(i).getPlayer().getHealth());
                                    nextPlayer.setStrength(maps.get(i).getPlayer().getStrength());
                                    break;
                                }
                            }
                        }
                        break;
                    case 'f':
                        cell.setType(CellType.FLOOR);
                        items.add(new Food(cell));
                        break;
                    case 'w':
                        cell.setType(CellType.FLOOR);
                        items.add(new Weapon(cell));
                        break;
                    case 'v':
                        cell.setType(CellType.FLOOR);
                        new Axe(cell);
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
                        cell.setType(CellType.FLOOR);
                        items.add(new Crown(cell));
                        break;
                    case 'b':
                        cell.setType(CellType.FLOOR);
                        new Bridge(cell);
                        break;
                    case 'l':
                        cell.setType(CellType.FLOOR);
                        map.addActor(new Lich(cell));
                        break;
                    case 'x':
                        cell.setType(CellType.FLOOR);
                        map.addActor(new DarkLord(cell));
                        break;
                    case 'g':
                        cell.setType(CellType.FLOOR);
                        map.addActor(new Phantom(cell));
                        break;
                    default:
                        throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                }
            }
        }
        return map;
    }
}
