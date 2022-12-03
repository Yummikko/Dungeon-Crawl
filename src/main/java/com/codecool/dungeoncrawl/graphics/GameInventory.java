package com.codecool.dungeoncrawl.graphics;

import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Item;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.util.List;

public class GameInventory extends GridPane {

    private Canvas canvas;
    private GraphicsContext context;
    public static ObservableList<Item> inventory;
    private Player player;

    public GameInventory() {
        super();
        inventory = FXCollections.observableArrayList();
        inventoryLayout();
        inventory.addListener(new ListChangeListener<Item>() {
            @Override
            public void onChanged(Change<? extends Item> change) {
                createLootUI();
            }
        });
    }

    public static List<Item> getItems() { return  inventory; }

    public void setPlayer(Player player) { this.player = player; }
    public void removeItemFromLoot(Item item) { this.getChildren().remove(item); }
    public void clearInventoryUI() { this.getChildren().clear(); }

    public void createLootUI() {
        GridPane uiInventory = new GridPane();
        clearInventoryUI();
        int colIndex = 0;
        int rowIndex = 0;
        for (Item item : inventory) {
            this.canvas = new Canvas(Tiles.TILE_WIDTH, Tiles.TILE_WIDTH);
            this.context = canvas.getGraphicsContext2D();
            StackPane holder = new StackPane();

            holder.getChildren().add(canvas);
            holder.setStyle("-fx-padding: 0 3.3 0 3.3; -fx-border-style: solid; -fx-border-width: 1px; -fx-border-color: #6D99AE;");
            Tiles.drawTile(context, item, 0, 0);
            if (colIndex % 4 == 0) {
                colIndex = 0;
                rowIndex++;
            }
            uiInventory.add(holder, colIndex++, rowIndex);
        }
        this.add(uiInventory, 0, 14, 3, 1);
    }

    private void inventoryLayout() {
        setPrefSize(6 * Tiles.TILE_WIDTH, 250);
        setMargin(this, new Insets(10, 0, 20, 0));
        setPadding(new Insets(10, 15, 10, 15));
        setBorder(new Border(new BorderStroke(Color.ALICEBLUE,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
        setBackground(new Background(new BackgroundFill(Color.valueOf("#203E54"), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void add(Item item) {
        this.inventory.add(item);
    }
}
