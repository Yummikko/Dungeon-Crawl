package com.codecool.dungeoncrawl.logic.enviroment;

import com.codecool.dungeoncrawl.logic.Cell;

public class Bridge extends Enviroment{
    public Bridge(Cell cell){super(cell);}

    @Override
    public String getTileName() {
        return "bridge";
    }
}
