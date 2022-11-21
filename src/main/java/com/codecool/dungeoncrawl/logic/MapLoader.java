package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.doors.*;
import com.codecool.dungeoncrawl.logic.enviroment.*;
import com.codecool.dungeoncrawl.logic.items.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MapLoader {
    public static List<GameMap> maps = new ArrayList<>();
    public static GameMap loadMap(String mapname) {
        InputStream is = MapLoader.class.getResourceAsStream(mapname);

        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
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
                            map.setSkeleton(new Skeleton(cell));
                            map.getSkeletons().add(map.getSkeleton());
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
                            new Key(cell);
                            break;
                        case 'p':
                            cell.setType(CellType.FLOOR);
                            new Poison(cell);
                            break;
                        case 'z':
                            cell.setType(CellType.FLOOR);
                            new Shield(cell);
                            break;
                        case 'o':
                            cell.setType(CellType.FLOOR);
                            new Octopus(cell);
                            break;
                        case 'a':
                            cell.setType(CellType.FLOOR);
                            new Spider(cell);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            Player nextPlayer = new Player(cell);
                            map.setPlayer(nextPlayer);
                            for (int i = 0; i < maps.size(); i++) {
                                for (int j = 1; j < maps.size()+j; j++) {
                                    if(maps.size() == j) {
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
                            new Food(cell);
                            break;
                        case 'w':
                            cell.setType(CellType.FLOOR);
                            new Weapon(cell);
                            break;
                        case 'r':
                            cell.setType(CellType.WALL);
                            new Water(cell);
                            break;
                        case 't':
                            cell.setType(CellType.STAIRS);
                            new Stairs(cell);
                            break;
                        case 'u':
                            cell.setType(CellType.SKULL);
                            new Skull(cell);
                            break;
                        case 'c':
                            cell.setType(CellType.FLOOR);
                            new Crown(cell);
                            break;
                        case 'b':
                            cell.setType(CellType.FLOOR);
                            new Bridge(cell);
                            break;
                        case 'l':
                            cell.setType(CellType.FLOOR);
                            map.setLich(new Lich(cell));
                            map.getLichs().add(map.getLich());
                            break;
                        case 'x':
                            cell.setType(CellType.FLOOR);
                            map.setDarkLord(new DarkLord(cell));
                            map.getDarkLords().add(map.getDarkLord());
                            break;
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            map.setPhantom(new Phantom(cell));
                            map.getPhantoms().add(map.getPhantom());
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }
}
