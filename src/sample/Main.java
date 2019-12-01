package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.media.MediaView;

import java.io.File;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.primaryStage = primaryStage;
        MenuController menu = new MenuController();
        Scene menuScene = new Scene(menu.getRoot(), 1530, 888);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Plants vs Zombies");
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    public static Stage getPrimaryStage(){return primaryStage;}

    public static void main(String[] args) {

        launch(args);
    }
}
