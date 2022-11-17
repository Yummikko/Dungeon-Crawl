package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.graphics.GameCamera;
import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.doors.NormalDoor;
import com.codecool.dungeoncrawl.logic.doors.OpenDoor;
import com.codecool.dungeoncrawl.logic.enviroment.Bridge;
import com.codecool.dungeoncrawl.logic.enviroment.Skull;
import com.codecool.dungeoncrawl.logic.enviroment.Water;
import com.codecool.dungeoncrawl.logic.items.Food;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Weapon;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap() {
        InputStream is = MapLoader.class.getResourceAsStream("/map.txt");
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
                            map.setPlayer(new Player(cell));
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
                            cell.setType(CellType.FLOOR);
                            new Water(cell);
                            break;
                        case 'u':
                            cell.setType(CellType.FLOOR);
                            new Skull(cell);
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
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
