package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Direction;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Enemy;
import com.codecool.dungeoncrawl.logic.actors.Lich;
import javafx.application.Platform;

public class Movements implements Runnable {
    private GameMap map;
    private Game game;

    public Movements(GameMap map, Game game) {
        this.map = map;
        this.game = game;
    }

    @Override
    public void run() {
        synchronized (GameMap.class) {
            int index = 0;
            while (index < 100_000) {
                try {
                    Thread.sleep(500);
                    for (Actor enemy : map.getEnemies()) {
                            if(enemy instanceof Lich)
                                Platform.runLater(() -> enemy.move(map));
                    }
                    Platform.runLater(() -> game.refresh());
                    Thread.sleep(500);
                    for (Actor enemy : map.getEnemies()) {
                        Platform.runLater(() -> enemy.move(map));
                    }
                    Platform.runLater(() -> game.refresh());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                index++;
            }
        }
    }
}
