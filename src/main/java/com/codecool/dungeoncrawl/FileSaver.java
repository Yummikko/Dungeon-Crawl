package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.json.GameJSONGenerator;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class FileSaver extends Application {
    @Override
    public void start(Stage stage) throws FileNotFoundException {
        File fileOne = new File(".\\src\\main\\resources\\Save.png");
        ImageView imgView = new ImageView(fileOne.toURI().toString());
        imgView.setFitWidth(20);
        imgView.setFitHeight(20);
        Menu file = new Menu("File");
        GameJSONGenerator jsonGenerator = new GameJSONGenerator();
        MenuItem item = new MenuItem("Save", imgView);
        file.getItems().addAll(item);
        //Creating a File chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        File selectedFile = fileChooser.showSaveDialog(null);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
        //Adding action on the menu item
        item.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //Opening a dialog box
                File file = fileChooser.showSaveDialog(stage);
                String json = null;
                try {
                    json = jsonGenerator.writeDataToJson().toString();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                if (file != null)
                    saveJSONToFile(json, file);
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

    private void saveJSONToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(FileSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
