package sample;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.Serializable;

import static java.lang.System.currentTimeMillis;

public class SidebarController implements Serializable {

    private transient HBox sidebar;

    public static int imageId;
    public final static int peaShooterId = 1;
    public final static int sunFlowerId = 2;
    public final static int wallnutId = 3;
    public final static int cherryBombId = 4;
    public static final DataFormat seedFormat = new DataFormat("MySeed");
    public static final DataFormat shovelFormat = new DataFormat("MyShovel");
    private final int imageSize = 150;
    private int sunToken;
    private transient Label sunCounter;
    private long lastUpdate;
    private final long peaTimeLimit = 9000000;
    private final long sunTimeLimit = 9000000;
    private final long wallnutTimeLimit = 9000000;
    private final long cherryTimeLimit = 9000000;
    private final int peaCost = 100;
    private final int sunCost = 50;
    private final int wallnutCost = 50;
    private final int cherryCost = 150;
    private long peaTimer;
    private long sunTimer;
    private long wallnutTimer;
    private long cherryTimer;
    private transient ImageView peashooterSeed;
    private transient ImageView cherryBombSeed;
    private transient ImageView sunflowerSeed;
    private transient ImageView wallnutSeed;

    public SidebarController(){
        imageId = -1;
        lastUpdate = currentTimeMillis();
        sunToken = 100;

        peaTimer = 0;
        sunTimer = 0;
        cherryTimer = 0;
        wallnutTimer = 0;

        initialise();
    }

    public void initialise(){
        sidebar = new HBox(10);
        VBox buyPlant = new VBox(10);

        Image peashooterSeedImage = new Image("file:images\\PeashooterSeed.png", imageSize, imageSize, true, true);
        peashooterSeed = new ImageView(peashooterSeedImage);
        peashooterSeed.setOnDragDetected(event -> {
            System.out.println("Drag Entered");
            Dragboard db = peashooterSeed.startDragAndDrop(TransferMode.MOVE);
            db.setDragView(peashooterSeed.snapshot(null, null));
            ClipboardContent cc = new ClipboardContent();
            cc.put(seedFormat, " ");
            db.setContent(cc);
            imageId = peaShooterId;
        });

        Image cherryBombSeedImage = new Image("file:images\\CherryBombSeed.png", imageSize, imageSize, true, true);
        cherryBombSeed = new ImageView(cherryBombSeedImage);
        cherryBombSeed.setOnDragDetected(event -> {
            Dragboard db = cherryBombSeed.startDragAndDrop(TransferMode.MOVE);
            db.setDragView(cherryBombSeed.snapshot(null, null));
            ClipboardContent cc = new ClipboardContent();
            cc.put(seedFormat, " ");
            db.setContent(cc);
            imageId = cherryBombId;
        });

        Image sunflowerSeedImage = new Image("file:images\\SunflowerSeed.png", imageSize, imageSize, true, true);
        sunflowerSeed = new ImageView(sunflowerSeedImage);
        sunflowerSeed.setOnDragDetected(event -> {
            Dragboard db = sunflowerSeed.startDragAndDrop(TransferMode.MOVE);
            db.setDragView(sunflowerSeed.snapshot(null, null));
            ClipboardContent cc = new ClipboardContent();
            cc.put(seedFormat, " ");
            db.setContent(cc);
            imageId = sunFlowerId;
        });

        Image wallnutSeedImage = new Image("file:images\\WallNutSeed.png", imageSize, imageSize, true, true);
        wallnutSeed = new ImageView(wallnutSeedImage);
        wallnutSeed.setOnDragDetected(event -> {
            Dragboard db = wallnutSeed.startDragAndDrop(TransferMode.MOVE);
            db.setDragView(wallnutSeed.snapshot(null, null));
            ClipboardContent cc = new ClipboardContent();
            cc.put(seedFormat, " ");
            db.setContent(cc);
            imageId = wallnutId;
        });

        buyPlant.getChildren().addAll(sunflowerSeed, wallnutSeed, peashooterSeed, cherryBombSeed);
        sidebar.getChildren().add(buyPlant);

        sunCounter = new Label("Sun Token: " + sunToken);
        sunCounter.setFont(Font.font("Cambria", 32));
        sunCounter.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        sidebar.getChildren().add(sunCounter);
        sidebar.relocate(10, 10);

        //Shovel
        Image shovelImage = new Image("file:images//Shovel.jpg", 80, 80, true, true);
        ImageView shovelImageView = new ImageView(shovelImage);
        shovelImageView.setOnDragDetected(event -> {
            Dragboard db = shovelImageView.startDragAndDrop(TransferMode.MOVE);
            db.setDragView(shovelImageView.snapshot(null, null));
            ClipboardContent cc = new ClipboardContent();
            cc.put(shovelFormat, " ");
            db.setContent(cc);
            imageId = wallnutId;
        });

        sidebar.getChildren().add(shovelImageView);
    }

    public void update(boolean isPaused){
        if(isPaused){
            lastUpdate = currentTimeMillis();
            return;
        }
        long currentTime = currentTimeMillis();
        peaTimer = Math.max(0, peaTimer - currentTime + lastUpdate);
        sunTimer = Math.max(0, sunTimer - currentTime + lastUpdate);
        cherryTimer = Math.max(0, cherryTimer - currentTime + lastUpdate);
        wallnutTimer = Math.max(0, wallnutTimer - currentTime + lastUpdate);
        if(peaTimer != 0 || sunToken < peaCost){
            peashooterSeed.setDisable(true);
            GaussianBlur blur = new GaussianBlur(10);
            peashooterSeed.setEffect(blur);
        }
        else{
            peashooterSeed.setDisable(false);
            GaussianBlur blur = new GaussianBlur(0);
            peashooterSeed.setEffect(blur);
        }
        if(sunTimer != 0 || sunToken < sunCost){
            sunflowerSeed.setDisable(true);
            GaussianBlur blur = new GaussianBlur(10);
            sunflowerSeed.setEffect(blur);
        }
        else{
            sunflowerSeed.setDisable(false);
            GaussianBlur blur = new GaussianBlur(0);
            sunflowerSeed.setEffect(blur);
        }
        if(cherryTimer != 0 || sunToken < cherryCost){
            cherryBombSeed.setDisable(true);
            GaussianBlur blur = new GaussianBlur(10);
            cherryBombSeed.setEffect(blur);
        }
        else{
            cherryBombSeed.setDisable(false);
            GaussianBlur blur = new GaussianBlur(0);
            cherryBombSeed.setEffect(blur);
        }
        if(wallnutTimer != 0 || sunToken < wallnutCost){
            wallnutSeed.setDisable(true);
            GaussianBlur blur = new GaussianBlur(10);
            wallnutSeed.setEffect(blur);
        }
        else{
            wallnutSeed.setDisable(false);
            GaussianBlur blur = new GaussianBlur(0);
            wallnutSeed.setEffect(blur);
        }
    }

    public void refreshPeaTimer(){peaTimer = peaTimeLimit;}
    public void refreshSunTimer(){sunTimer = sunTimeLimit;}
    public void refreshCherryTimer(){cherryTimer = cherryTimeLimit;}
    public void refreshWallnutTimer(){wallnutTimer = wallnutTimeLimit;}

    public void changeSunCounter(int x){
        sunToken += x;
        sunCounter.setText("Sun Token: " + sunToken);
    }

    public HBox getContainer(){return sidebar;}

}
