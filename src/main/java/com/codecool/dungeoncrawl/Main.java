package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.graphics.GameMenu;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import java.io.FileNotFoundException;

public class Main extends Application {
    private GameMenu gameMenu;

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        gameMenu = new GameMenu();
        gameMenu.mainMenu(primaryStage);
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

}
