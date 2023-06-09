package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.doors.NormalDoor;
import com.codecool.dungeoncrawl.logic.doors.OpenDoor;
import com.codecool.dungeoncrawl.logic.enviroment.*;
import com.codecool.dungeoncrawl.logic.items.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MapLoader {
    public static List<GameMap> maps = new ArrayList<>();

    private MapLoader() {}

    public static GameMap loadMap(String mapname, boolean goingBack) {
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
                        cell.setType(CellType.FLOOR);
                        map.addActor(new Skeleton(cell));
                        break;
                    case 'd':
                        cell.setType(CellType.WALL);
                        new NormalDoor(cell);
                        break;
                    case 'q':
                        cell.setType(CellType.FLOOR);
                        new OpenDoor(cell);
                        break;
                    case 'k':
                        cell.setType(CellType.FLOOR);
                        items.add(new Key(cell));
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
                        if (goingBack) {
                            break;
                        } else {
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
                        }
                    case '%':
                        if (goingBack) {
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
                        } else {
                            break;
                        }
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