package plantsVsZombies;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;

import java.io.File;

public class ChooseLevelController {

    private Pane pane;
    private Integer level;
    private User user;
    private MediaPlayer mediaPlayer;
    ChooseLevelController(User user){
        this.user = user;
        pane = new Pane();
        level = -1;

        String path = "images/ElevatorMusic.mp3";
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        VBox menu = new VBox();
        VBox menu1 = new VBox();
        VBox menu2 = new VBox();
        VBox menu3 = new VBox();
        VBox menu4 = new VBox();
        VBox menu5 = new VBox();
        VBox menu6 = new VBox();

        Button level1 = new Button("Level 1");
        level1.setGraphic(new ImageView(new Image("file:images\\ButtonImg.png", 250, 250, true, true)));
        menu.getChildren().add(level1);
        level1.setOnMouseClicked(e ->{
            mediaPlayer.stop();
            user.play(1);
        });

        Button level2 = new Button("Level 2");
        level2.setGraphic(new ImageView(new Image("file:images\\ButtonImg.png", 250, 250, true, true)));
        menu1.getChildren().add(level2);
        level2.setOnMouseClicked(e ->{
            mediaPlayer.stop();
            user.play(2);
        });
        if(user.getLevel() < 2){
            level2.setDisable(true);
            level2.setEffect(new GaussianBlur(10));
        }

        Button level3 = new Button("Level 3");
        level3.setGraphic(new ImageView(new Image("file:images\\ButtonImg.png", 250, 250, true, true)));
        menu2.getChildren().add(level3);
        level3.setOnMouseClicked(e ->{
            mediaPlayer.stop();
            user.play(3);
        });
        if(user.getLevel() < 3){
            level3.setDisable(true);
            level3.setEffect(new GaussianBlur(10));
        }

        Button level4 = new Button("Level 4");
        level4.setGraphic(new ImageView(new Image("file:images\\ButtonImg.png", 250, 250, true, true)));
        menu3.getChildren().add(level4);
        level4.setOnMouseClicked(e ->{
            mediaPlayer.stop();
            user.play(4);
        });
        if(user.getLevel() < 4){
            level4.setDisable(true);
            level4.setEffect(new GaussianBlur(10));
        }

        Button level5 = new Button("Level 5");
        level5.setGraphic(new ImageView(new Image("file:images\\ButtonImg.png", 250, 250, true, true)));
        menu4.getChildren().add(level5);
        level5.setOnMouseClicked(e ->{
            mediaPlayer.stop();
            user.play(5);
        });
        if(user.getLevel() < 5){
            level5.setDisable(true);
            level5.setEffect(new GaussianBlur(10));
        }

        Button level6 = new Button("Go Back");
        level6.setGraphic(new ImageView(new Image("file:images\\ButtonImg.png", 250, 250, true, true)));
        menu5.getChildren().add(level6);
        level6.setOnMouseClicked(e ->{
            mediaPlayer.stop();
            MenuController mc = new MenuController();
            Main.getPrimaryStage().setScene(new Scene(mc.getRoot(), 1530, 888));
        });

        Button level7 = new Button("Bonus Level");
        level7.setGraphic(new ImageView(new Image("file:images\\ButtonImg.png", 250, 250, true, true)));
        menu6.getChildren().add(level7);
        level7.setOnMouseClicked(e ->{
            mediaPlayer.stop();
            user.play(6);
        });

        menu.relocate(600, 100);
        menu1.relocate(600,200);
        menu2.relocate(600,300);
        menu3.relocate(600,400);
        menu4.relocate(600,500);
        menu5.relocate(600,700);
        menu6.relocate(600,600);

        Image backgroundImage = new Image("file:images\\level.jpg");
        BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(backgroundImage), CornerRadii.EMPTY, Insets.EMPTY);
        pane.setBackground(new Background(backgroundFill));
        pane.getChildren().addAll(menu,menu1,menu2,menu3,menu4, menu6);
        pane.getChildren().add(menu5);
    }

    public void chooseLevel(){
        Main.getPrimaryStage().setScene(new Scene(pane, 1530, 888));
    }

}
