package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.List;

public class EnemyModel extends BaseModel {
    private String enemyName;
    private int hp;
    private int x;
    private int y;

    public EnemyModel(Actor actor) {
        this.enemyName = actor.getCell().getActor().toString();
        this.x = actor.getX();
        this.y = actor.getY();
        this.hp = actor.getHealth();
    }

    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
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

    public void setY(int y) {
        this.y = y;
    }
}
