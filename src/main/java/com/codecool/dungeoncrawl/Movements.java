package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Lich;
import javafx.application.Platform;

import java.util.concurrent.atomic.AtomicBoolean;

public class Movements implements Runnable {
    private static GameMap map;
    private Game game;
    private final static AtomicBoolean running = new AtomicBoolean(true);
    public Movements(GameMap map, Game game) {
        this.map = map;
        this.game = game;
    }

    @Override
    public void run() {
        synchronized (GameMap.class) {
            int index = 0;
            while (index < 100_000 && running.get()) {
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
                    stop();
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                index++;
            }
        }
    }
    public static void stop() {
        running.set(false);
    }

    public static void start() {
        running.set(true);
    }

}
