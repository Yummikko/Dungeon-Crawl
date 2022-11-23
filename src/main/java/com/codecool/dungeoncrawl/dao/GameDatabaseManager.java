package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class GameDatabaseManager {
    private PlayerDao playerDao;
    private Connection c = null;
    private Statement stmt = null;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
    }

    public void savePlayer(Player player) {
        PlayerModel model = new PlayerModel(player);
        playerDao.add(model);
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = "postgres";
        String user = "ductrung2";
        String password = "Dungeon2022?";

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }

    public void dbConnect() {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://dungeon-crawl.postgres.database.azure.com:5432/postgres",
                            "ductrung2", "Dungeon2022?");
            System.out.println("Opened database successfully");
//            stmt = c.createStatement();
//            String sql = "CREATE TABLE GAMESTATE " +
//                    "(ID INT PRIMARY KEY     NOT NULL," +
//                    " MAP           INT     NOT NULL, " +
//                    " AGE           INT     NOT NULL, " +
//                    " HP            INT     NOT NULL, " +
//                    " STRENGTH      INT     NOT NULL)";
//            stmt.executeUpdate(sql);
//            stmt.close();
//            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }
}
