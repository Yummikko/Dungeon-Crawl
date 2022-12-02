package com.codecool.dungeoncrawl.logic.controller;

import com.codecool.dungeoncrawl.dao.GameDaoJdbc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class LoadController {

    private final GameDaoJdbc gameDaoJdbc = new GameDaoJdbc();
    private final ObservableList<GameInfoShort> games = FXCollections.observableArrayList();
    @FXML
    private Button cancel_btn;
    @FXML
    private Button load_btn;
    @FXML
    private TableView<GameInfoShort> tbl_games;
    @FXML
    private TableColumn<GameInfoShort, String> tc_name;
    @FXML
    private TableColumn<GameInfoShort, LocalDateTime> tc_time;
    @FXML
    private TableColumn<GameInfoShort, Integer> tc_stage;
    private GameInfoShort selectedGame;

    public void initialize() {
        gameDaoJdbc.setGamesInfo(games);

        load_btn.setDisable(true);
        handleGameSelected();

        tc_name.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        tc_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        tc_stage.setCellValueFactory(new PropertyValueFactory<>("stage"));
        tbl_games.setItems(games);
        tbl_games.getSelectionModel().getSelectedItem();
    }

    private void handleGameSelected() {
        tbl_games.getSelectionModel()
                .selectedItemProperty()
                .addListener((observableValue, oldValue, newValue) -> {
                    if (tbl_games.getSelectionModel().getSelectedItem() != null) {
                        TableView.TableViewSelectionModel<GameInfoShort> selectionModel = tbl_games.getSelectionModel();
                        selectedGame = selectionModel.getSelectedItem();
                        load_btn.setDisable(false);
                    }
                });
    }

    @FXML
    void loadGame() {
        System.out.println("loading game " + selectedGame);
        gameDaoJdbc.load(selectedGame);
        System.out.println("game loaded");
        closeStage();
    }

    @FXML
    void cancelLoading() {
        System.out.println("closing load screen");
        closeStage();
    }

    private void closeStage() {
        Stage window = (Stage) cancel_btn.getScene().getWindow();
        window.close();
    }

}
