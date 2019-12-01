package sample;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

public class MenuController implements Serializable {
    private Pane pane;
    private MediaPlayer mediaPlayer;

    public MenuController(){

        String path = "images/ElevatorMusic.mp3";
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        pane = new Pane();
        Image backgroundImage = new Image("file:images\\menu_background.jpg");
        BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(backgroundImage), CornerRadii.EMPTY, Insets.EMPTY);

        VBox menu = new VBox();
        VBox menu1 = new VBox();
        VBox menu2 = new VBox();
        VBox menu3 = new VBox();

        Button startButton = new Button("New Game");
        startButton.setGraphic(new ImageView(new Image("ButtonImg.png", 100, 140, true, true)));

        startButton.setOnMouseClicked(event -> {

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Enter Username");
            dialog.setHeaderText("Welcome to Plants vs Zombies!");
            dialog.setContentText("Please Enter your username: ");

            Optional<String> username = dialog.showAndWait();
            username.ifPresent(usrname -> {
                System.out.println("Username is " + usrname);
                if(!usrname.equals("")){
                    User u = new User(usrname);
                    mediaPlayer.stop();
                    u.chooseLevel();
                }
                else{
                    dialog.close();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Ooops, there was an error");
                    alert.setContentText("Please enter a valid username");
                    alert.showAndWait();
                }
            });


//            Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
//            primaryStage.setScene(new Scene(new GameController().getRoot(), 1530, 888));
        });
        Button resumeButton = new Button("Resume Game");
        resumeButton.setGraphic(new ImageView(new Image("ButtonImg.png", 100, 140, true, true)));
        Button exitButton = new Button("Quit Game");
        exitButton.setGraphic(new ImageView(new Image("ButtonImg.png", 100, 140, true, true)));

        resumeButton.setOnMouseClicked(e->{
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Enter Your Username");
            dialog.setHeaderText("Welcome to Plants vs Zombies!");
            dialog.setContentText("Please enter your username: ");

            Optional<String> username = dialog.showAndWait();
            username.ifPresent(usrname -> {
                System.out.println("Username is " + usrname);
                User u = null;
                try {
                    u = User.deserialize(usrname);
                } catch (IOException ex) {
//                    ex.printStackTrace();
                    dialog.close();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Ooops, there was an error");
                    alert.setContentText("Given username doesn't exist");
                    alert.showAndWait();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                if(u != null) u.chooseLevel();
            });
        });
//        Button levelButton = new Button("Choose Level");
        exitButton.setOnMouseClicked(e->{
            Main.getPrimaryStage().close();
        });
//        menu.getChildren().addAll(startButton, resumeButton, levelButton, exitButton);
        menu.getChildren().addAll(startButton);
        menu1.getChildren().add(resumeButton);
        menu3.getChildren().add(exitButton);

        menu.relocate(600,700);
        menu1.relocate(850,700);
        menu3.relocate(1100,700);

        pane.getChildren().add(menu);
        pane.getChildren().add(menu1);
        pane.getChildren().add(menu3);

        pane.setBackground(new Background(backgroundFill));
    }

    public Pane getRoot(){return pane;}
}
