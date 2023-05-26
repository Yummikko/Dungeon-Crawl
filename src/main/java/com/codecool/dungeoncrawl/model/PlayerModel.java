package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.Game;
import com.codecool.dungeoncrawl.graphics.GameMenu;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;
import java.util.List;

public class PlayerModel extends BaseModel {
    private String playerName;
    private int hp;
    private int strength;
    private int x;
    private int y;

    public PlayerModel(Player player) {
        this.playerName = player.getName();
        this.x = player.getX();
        this.y = player.getY();
        this.strength = player.getStrength();
        this.hp = player.getHealth();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setY(int y) {
        this.y = y;
    }
}
