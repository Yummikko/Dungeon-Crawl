package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameDaoJdbc {

    private static final Map<Class, String> types = new HashMap<>();
    private final DataSource dataSource;

    public GameDaoJdbc() {
        this.dataSource = GameDatabaseManager.connect();
        types.put(Player.class, "@");
        types.put(Skeleton.class, "s");
        types.put(Octopus.class, "o");
        types.put(Spider.class, "a");
        types.put(Lich.class, "l");
        types.put(DarkLord.class, "x");
        types.put(Phantom.class, "g");
        types.put(Key.class, "k");
        types.put(Poison.class, "p");
        types.put(Shield.class, "z");
        types.put(Food.class, "f");
        types.put(Weapon.class, "w");
        types.put(Crown.class, "c");
    }

    public void add(Player player, List<Actor> enemies, List<Item> items, int currentMap) {
        int playerId = savePlayer(player);
        int gameId = saveGame(currentMap, playerId);
        saveEnemies(enemies, gameId);
        saveItems(items, gameId);
        saveInventory(player.getInventory(), playerId);
    }

    private int savePlayer(Player player) {
        int actorId = saveActor(player);
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO player (actor_id, name, hasweapon, haskey) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, actorId);
            statement.setString(2, player.getName());
            statement.setBoolean(3, player.getHasWeapon());
            statement.setBoolean(4, player.getHasKey());
            statement.executeUpdate();
            return actorId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int saveGame(int map, int playerId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO game (player_id, stage, save_date) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, playerId);
            statement.setInt(2, map);
            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveEnemies(List<Actor> enemies, int gameId) {
        for (Actor enemy : enemies) {
            int enemyId = saveActor(enemy);
            saveGameEnemy(gameId, enemyId);
        }
    }

    private void saveGameEnemy(int gameId, int enemyId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO game_enemies (game_id, actor_id) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, gameId);
            statement.setInt(2, enemyId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveGameItem(int gameId, int itemId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO game_items (game_id, item_id) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, gameId);
            statement.setInt(2, itemId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int saveActor(Actor actor) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO actor (type, y, x, health, strength) VALUES (?, ?, ?, ?, ?) returning id";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, types.get(actor.getClass()));
            statement.setInt(2, actor.getY());
            statement.setInt(3, actor.getX());
            statement.setInt(4, actor.getHealth());
            statement.setInt(5, actor.getStrength());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveItems(List<Item> items, int gameId) {
        for (Item item : items) {
            int itemId = saveItem(item);
            saveGameItem(gameId, itemId);
        }
    }

    private int saveItem(Item item) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO item (type, y, x) VALUES (?, ?, ?) returning id";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, types.get(item.getClass()));
            statement.setInt(2, item.getY());
            statement.setInt(3, item.getX());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveInventory(List<Item> inventory, int playerId) {
        for (Item item : inventory) {
            int itemId = savePlayerItem(item);
            savePlayerPlayerItem(playerId, itemId);
        }
    }

    private int savePlayerItem(Item item) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO player_item (type, name) VALUES (?, ?) returning id";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, types.get(item.getClass()));
            // todo name jest chyba niepotrzebny, bo możemy sobie nazwę brać na podstawie typu przy wczytywaniu
            statement.setString(2, item.getTileName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void savePlayerPlayerItem(int playerId, int itemId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO player_player_items (actor_id, player_item_id) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, playerId);
            statement.setInt(2, itemId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PlayerModel> getAll() {
        return null;
    }

}
