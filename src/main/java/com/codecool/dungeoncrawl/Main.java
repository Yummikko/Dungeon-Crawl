package com.codecool.dungeoncrawl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import java.io.FileNotFoundException;

public class Main extends Application {
    public Game game;

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        game = new Game();
        game.mainMenu(primaryStage);
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
    }

}
