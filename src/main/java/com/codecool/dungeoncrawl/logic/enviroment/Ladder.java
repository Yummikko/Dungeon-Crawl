package com.codecool.dungeoncrawl.logic.enviroment;

import com.codecool.dungeoncrawl.logic.Cell;

public class Ladder extends Enviroment {
    public Ladder(Cell cell){super(cell);}

    @Override
    public String getTileName(){
        return "ladder";
    }
}
