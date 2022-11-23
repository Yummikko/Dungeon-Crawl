package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;

public class App {
    public static void main(String[] args) {
        GameDatabaseManager manager = new GameDatabaseManager();
        manager.dbConnect();
        Main.main(args);
    }
}
