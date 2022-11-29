package com.codecool.dungeoncrawl;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class FileSaver extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        File fileOne = new File("C:\\Users\\Trung\\IdeaProjects\\dungeon-crawl-2-java-Pastor28\\src\\main\\resources\\Save.png");
        ImageView imgView = new ImageView(fileOne.toURI().toString());
        imgView.setFitWidth(20);
        imgView.setFitHeight(20);
        Menu file = new Menu("File");
        MenuItem item = new MenuItem("Save", imgView);
        file.getItems().addAll(item);
        //Creating a File chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("json", "json"));
        //Adding action on the menu item
        item.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //Opening a dialog box
                fileChooser.showSaveDialog(stage);
            }
        });
        //Creating a menu bar and adding menu to it.
        MenuBar menuBar = new MenuBar(file);
        Group root = new Group(menuBar);
        Scene scene = new Scene(root, 595, 355, Color.BEIGE);
        stage.setTitle("Saving current game's status to json");
        stage.setScene(scene);
        stage.show();
    }
}
