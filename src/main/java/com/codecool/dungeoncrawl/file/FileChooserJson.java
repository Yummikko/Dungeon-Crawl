package com.codecool.dungeoncrawl.file;

import com.codecool.dungeoncrawl.logic.json.GameJSONToMap;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileChooserJson extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            File fileOne = new File(".\\src\\main\\resources\\load-file-16.png");
            GameJSONToMap jsonReader = new GameJSONToMap();
            ImageView imgView = new ImageView(fileOne.toURI().toString());
            imgView.setFitWidth(24);
            imgView.setFitHeight(24);
            MenuItem item = new MenuItem("Load", imgView);
            // set title for the stage
            stage.setTitle("File Loader");
            Menu file = new Menu("Import JSON File");
            file.getItems().addAll(item);
            // create a File chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
            // create a Label
            Label label = new Label("No files selected");

            EventHandler<ActionEvent> event =
                    e -> {
                        File file1 = fileChooser.showOpenDialog(stage);
                        if (file1 != null) {
                            label.setText(file1.getAbsolutePath() + " selected");
                            jsonReader.parseJsonToMap(file1.getAbsolutePath());
                            stage.close();
                        }
                        else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Message for Player");
                            alert.setHeaderText("You didn't choose any file!");
                            alert.setContentText("Press OK to close");
                            alert.showAndWait().ifPresent(rs -> {
                                if (rs == ButtonType.OK) {
                                    System.out.println("Pressed OK.");
                                }
                            });
                        }
                    };
            item.setOnAction(event);
            //Creating a menu bar and adding menu to it.
            MenuBar menuBar = new MenuBar(file);
            Group root = new Group(menuBar);
            Scene scene = new Scene(root, 595, 355, Color.BEIGE);
            stage.setTitle("Import game's status from json");
            stage.setScene(scene);
            stage.show();
        }

        catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }
}
