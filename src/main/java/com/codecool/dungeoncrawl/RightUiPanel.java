package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.graphics.GameInventory;
import com.codecool.dungeoncrawl.logic.actors.Player;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;


public class RightUiPanel extends GridPane {
    private Label nameLabel = new Label();
    private Label healthLabel = new Label();
    private Label strengthLabel = new Label();
    private Label playerInventory = new Label("INVENTORY: ");
    private GameInventory inventory;
    public Button pickUpButton = new Button("Pick up item");
    public Button exportButton = new Button("Export");
    public Button importButton = new Button("Import");

    public RightUiPanel(Player player) {
        super();
        this.setPrefWidth(225);
        this.setPadding(new Insets(13));
        this.setStyle("-fx-background-color: #6C8D9E; -fx-font-size: 18px; -fx-text-fill: #6B8D9E;");
        setTextForRightUI(player);
        this.add(new Label("Name: "),0, 0 );
        this.add(nameLabel, 1, 0);
        this.add(new Label("Health: "), 0, 1);
        this.add(healthLabel, 1, 1);
        this.add(new Label("Strength: "), 0, 2);
        this.add(strengthLabel, 1, 2);
        this.add(pickUpButton, 0, 5);
        this.add(exportButton, 0, 35);
        this.add(importButton, 1, 35);
        pickUpButton.setFocusTraversable(false);
        exportButton.setFocusTraversable(false);
        importButton.setFocusTraversable(false);
        this.inventory = new GameInventory();
        hideButton();
        this.add(new Label("INVENTORY:"), 0, 7);
        this.add(inventory, 0, 8, 3, 1);
    }

    public GameInventory getInventory() {
        return inventory;
    }

    public void setInventory(GameInventory inventory) {
        this.inventory = inventory;
    }

    public void hideButton() {
        pickUpButton.setVisible(false);
    }
    public void showButton() {
        pickUpButton.setVisible(true);
    }

    void setTextForRightUI(Player player) {
        nameLabel.setText("" + player.getName());
        healthLabel.setText("" + player.getHealth());
        strengthLabel.setText("" + player.getStrength());
        playerInventory.setText("" + player.inventoryToString());
    }

}
