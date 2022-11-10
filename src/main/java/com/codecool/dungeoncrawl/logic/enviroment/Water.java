package com.codecool.dungeoncrawl.logic.enviroment;

import com.codecool.dungeoncrawl.logic.Cell;

public class Water extends Enviroment {
    public Water(Cell cell){super(cell);}

    @Override
    public String getTileName(){
        return "water";
    }
}
