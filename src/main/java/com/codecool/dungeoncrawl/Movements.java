package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Lich;
import javafx.application.Platform;

public class Movements implements Runnable {
    private GameMap map;
    private Game game;
    private static boolean running = true;

    public Movements(GameMap map, Game game) {
        this.map = map;
        this.game = game;
    }

    @Override
    public void run() {
        synchronized (GameMap.class) {
            int index = 0;
            while (index < 100_000 && running) {
                try {
                    Thread.sleep(500);
                    for (Actor enemy : map.getEnemies()) {
                            if(enemy instanceof Lich)
                                if(index % 8 == 0)
                                    Platform.runLater(() -> enemy.move(map));
                    }
                    Platform.runLater(() -> game.refresh());
                    Thread.sleep(500);
                    for (Actor enemy : map.getEnemies()) {
                        if(index % 4 == 0)
                            Platform.runLater(() -> enemy.move(map));
                    }
                    Platform.runLater(() -> game.refresh());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                index++;
                if (index == 100_000)
                    running = false;
            }
        }
    }
    static void setRunning() {
        running = false;
    }
}
