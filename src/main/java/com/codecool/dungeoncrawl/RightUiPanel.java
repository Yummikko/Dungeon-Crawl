package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.awt.*;

public class RightUiPanel extends GridPane {
    Label nameLabel = new Label();
    Label healthLabel = new Label();
    Label strengthLabel = new Label();
    Label playerInventory = new Label("INVENTORY: ");

    public Button pickUpButton = new Button("Pick up item");

    public RightUiPanel(Player player,GameMap map) {
        super();
        this.setPrefWidth(200);
        this.setPadding(new Insets(10));
        this.setStyle("-fx-background-color: #6C8D9E; -fx-font-size: 18px; -fx-text-fill: #6B8D9E;");
        setTextForRightUI(player);
        this.add(new Label("Name: "),0, 0 );
        this.add(nameLabel, 1, 0);
        this.add(new Label("Health: "), 0, 1);
        this.add(healthLabel, 1, 1);
        this.add(new Label("Strength: "), 0, 2);
        this.add(strengthLabel, 1, 2);
        this.add(pickUpButton, 0, 5);
        hideButton();
        this.add(new Label("INVENTORY:"), 0, 7);
        this.add(playerInventory, 0, 8);
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
