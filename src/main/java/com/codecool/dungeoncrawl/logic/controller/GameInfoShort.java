package com.codecool.dungeoncrawl.logic.controller;

import java.time.LocalDateTime;
import java.util.StringJoiner;

public class GameInfoShort {

    private String playerName;
    private LocalDateTime time;
    private Integer stage;
    private Integer id;

    public GameInfoShort(String playerName, LocalDateTime time, Integer stage, Integer id) {
        this.playerName = playerName;
        this.time = time;
        this.stage = stage;
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GameInfoShort.class.getSimpleName() + "[", "]")
                .add("playerName='" + playerName + "'")
                .add("time=" + time)
                .add("stage=" + stage)
                .add("id=" + id)
                .toString();
    }

}
