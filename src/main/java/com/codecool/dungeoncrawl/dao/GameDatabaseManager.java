package com.codecool.dungeoncrawl.dao;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class GameDatabaseManager {

    public static DataSource connect() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://dungeon-crawl.postgres.database.azure.com:5432/postgres");
        dataSource.setUser("ductrung2");
        dataSource.setPassword("Dungeon2022?");

        return dataSource;
    }

}
