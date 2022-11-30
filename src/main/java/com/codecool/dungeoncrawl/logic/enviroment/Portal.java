package com.codecool.dungeoncrawl.logic.enviroment;

import com.codecool.dungeoncrawl.logic.Cell;

public class Portal extends Enviroment {
    public Portal(Cell cell){super(cell);}

    @Override
    public String getTileName(){
        return "portal";
    }
}
