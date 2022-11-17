package com.codecool.dungeoncrawl.logic.enviroment;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;


public class Stairs extends Enviroment {
    public Stairs(Cell cell){super(cell);}

    @Override
    public String getTileName(){
        return "stairs";
    }
}
