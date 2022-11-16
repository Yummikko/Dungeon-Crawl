package com.codecool.dungeoncrawl.logic.enviroment;

import com.codecool.dungeoncrawl.logic.Cell;

public class Skull extends Enviroment{
    public Skull(Cell cell){super(cell);}

    @Override
    public String getTileName() {
        return "skull";
    }
}
