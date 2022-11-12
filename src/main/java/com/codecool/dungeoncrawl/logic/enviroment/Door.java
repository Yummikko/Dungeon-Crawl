package com.codecool.dungeoncrawl.logic.enviroment;

import com.codecool.dungeoncrawl.logic.Cell;

public class Door extends Enviroment{
    public Door(Cell cell){super(cell);}

    @Override
    public String getTileName() {
        return "door";
    }
}
