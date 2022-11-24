package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        GameDatabaseManager manager = new GameDatabaseManager();
        manager.dbConnect();
        Main.main(args);
    }
}
