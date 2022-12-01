package com.codecool.dungeoncrawl.file;

import com.codecool.dungeoncrawl.logic.json.GameJSONGenerator;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileSaver extends Application {
    @Override
    public void start(Stage stage) throws FileNotFoundException {
        File fileOne = new File(".\\src\\main\\resources\\Save.png");
        ImageView imgView = new ImageView(fileOne.toURI().toString());
        imgView.setFitWidth(20);
        imgView.setFitHeight(20);
        Menu file = new Menu("Export JSON File");
        GameJSONGenerator jsonGenerator = new GameJSONGenerator();
        MenuItem item = new MenuItem("Save", imgView);
        file.getItems().addAll(item);
        //Creating a File chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
        //Adding action on the menu item
        item.setOnAction(e -> {
            String json;
            File selectedFile = fileChooser.showSaveDialog(stage);
            try {
                json = jsonGenerator.writeDataToJson().toString();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            if (selectedFile == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message for Player");
                alert.setHeaderText("You didn't save!");
                alert.setContentText("Press OK to close");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
            } else {
                saveJSONToFile(json, selectedFile);
                stage.close();
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
