package plantsVsZombies;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.System.currentTimeMillis;
import static plantsVsZombies.SidebarController.*;

public class GameController implements Serializable {
    private transient Pane pane;
    private transient GraphicsContext gc;
    private final static int gridX = 380;
    private final static int gridY = 120;
    private final static int gridHeight = 147;
    private final static int gridWidth = 120;
    private final double lawnMoverSpeed = 0.3;
    private boolean occupied[][];
    private boolean isPaused;
    private SidebarController sidebar;
    private PlantManager plantManager;
    private LawnmowerManager lawnmowerManager;
    private ZombieManager zombieManager;
    private TimerController timerController;
    private SunManager sunManager;
    private transient AnimationTimer animationTimer;
    private int level;
    private User user;
    private ArrayList<Pair<Zombie,Long>> to_remove;
    private int offset;
    private int numLanes;
    private boolean gameFinished;
    private transient MediaPlayer mediaPlayer;
    public GameController(User user, int level){
        this.user = user;
        this.level = level;
        this.offset = LevelManager.offset(level);
        this.numLanes = LevelManager.numLanes(level);

        gameFinished = false;
        isPaused = false;
        plantManager = new PlantManager(this);

        occupied = new boolean[9][5];
        for(int i = 0; i < offset; i++){
            for(int j = 0; j < 9; j++){
                occupied[j][i] = true;
                occupied[j][4-i] = true;
            }
        }

        lawnmowerManager = new LawnmowerManager();
        for(int i = 0; i < 5; i++){
            lawnmowerManager.addLawnmower(new Lawnmower(i));
        }

        zombieManager = new ZombieManager(level, this);
        sidebar = new SidebarController();

        timerController = new TimerController();

        sunManager = new SunManager(this, level);
        to_remove = new ArrayList<Pair<Zombie,Long>>();
    }

    public void initialise(){

        String path = "images/CrazyDaveIntroTheme.mp3";
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        pane = new Pane();
        Image backgroundImage = LevelManager.getImage(level);
        BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(backgroundImage), CornerRadii.EMPTY, Insets.EMPTY);
        pane.setBackground(new Background(backgroundFill));

        Canvas canvas = new Canvas(1530, 888);
        pane.getChildren().add(canvas);
        pane.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();
            if(db.hasContent(seedFormat) && imageId != -1){
                event.acceptTransferModes(TransferMode.MOVE);
            }
            else if(db.hasContent(shovelFormat)){
                event.acceptTransferModes(TransferMode.MOVE);
            }
        });
        pane.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            if(db.hasContent(seedFormat)){
                int col = getCol((int)event.getX());
                int row = getRow((int)event.getY());
                if(!occupied[col][row]){
                    occupied[col][row] = true;
                    switch (imageId){
                        case peaShooterId:
                            plantManager.addPlant(new Peashooter(col, row, plantManager));
                            sidebar.changeSunCounter(-100);
                            sidebar.refreshPeaTimer();
                            break;
                        case sunFlowerId:
                            plantManager.addPlant(new Sunflower(col, row, this));
                            sidebar.changeSunCounter(-50);
                            sidebar.refreshSunTimer();
                            break;
                        case wallnutId:
                            plantManager.addPlant(new Wallnut(col, row));
                            sidebar.changeSunCounter(-50);
                            sidebar.refreshWallnutTimer();
                            break;
                        case cherryBombId:
                            plantManager.addPlant(new CherryBomb(col, row, this));
                            sidebar.changeSunCounter(-150);
                            sidebar.refreshCherryTimer();
                            break;
                    }
                }
                imageId = -1;
                event.setDropCompleted(true);
                System.out.println("Drag Over");
            }
            else if(db.hasContent(shovelFormat)){
                int col = getCol((int)event.getX());
                int row = getRow((int)event.getY());
                plantManager.removePlant(col, row);
                occupied[col][row] = false;
            }
        });

        sidebar.initialise();
        pane.getChildren().add(sidebar.getContainer());

        gc = canvas.getGraphicsContext2D();

        Button menuButton = new Button("Menu");
        menuButton.relocate(1450, 10);
        menuButton.setFont(Font.font("Cambria", 18));
        pane.getChildren().add(menuButton);

        menuButton.setOnAction(e -> {
            pane.setEffect(new GaussianBlur());
            isPaused = true;

            VBox pauseRoot = new VBox(5);
            pauseRoot.getChildren().add(new Label("Paused"));
            pauseRoot.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");
            pauseRoot.setAlignment(Pos.CENTER);
            pauseRoot.setPadding(new Insets(20));

            Button resume = new Button("Resume Game");
            pauseRoot.getChildren().add(resume);

            Button save = new Button("Save Game");
            pauseRoot.getChildren().add(save);

            Button levelMenu = new Button("Level Menu");
            pauseRoot.getChildren().add(levelMenu);

            Button quit = new Button("Quit Game");
            pauseRoot.getChildren().add(quit);

            Stage popupStage = new Stage(StageStyle.TRANSPARENT);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(pauseRoot, Color.TRANSPARENT));


            resume.setOnAction(event -> {
                isPaused = false;
                pane.setEffect(null);
                popupStage.hide();
            });

            save.setOnAction(event -> {
                try {
                    user.serialize();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            levelMenu.setOnAction(event -> {
                gameLost();
                popupStage.hide();
            });

            quit.setOnAction(event -> {
                popupStage.hide();
                animationTimer.stop();
                mediaPlayer.stop();
                MenuController menuController = new MenuController();
                Main.getPrimaryStage().setScene(new Scene(menuController.getRoot(), 1530, 888));
            });

            popupStage.show();
        });

        isPaused = true;
        plantManager.updatePlant(isPaused);
        zombieManager.updateZombies(isPaused);
        lawnmowerManager.updateLawnmower(isPaused);
        timerController.update(isPaused);
        sunManager.update(isPaused);
        sidebar.update(isPaused);
        isPaused = false;

        animationTimer = new AnimationTimer(){
            @Override
            public void handle(long currentNanoTime){
                if(!gameFinished)
                    gc.clearRect(0, 0, 1530, 888);
                handle_zomb(to_remove);

                for(Zombie zombie : zombieManager.getZombies()){
                    if(zombie.getHealth() > 0) zombie.setBlocked(false);
                }

                for(Plant plant : new ArrayList<>(plantManager.getPlants())){
                    for(Zombie zombie : new ArrayList<>(zombieManager.getZombies())){
                        if(plant instanceof  CherryBomb){
                            if(((CherryBomb)plant).getExplode() && (plant.getX() - zombie.getX())*(plant.getX() -
                                    zombie.getX()) + (plant.getY() - zombie.getY())*(plant.getY() - zombie.getY()) < 200*200){
                                zombie.setImage( new Image("file:images\\Incinerated_Zombie.gif", 140, 140, true, true));
                                zombie.setBlocked(true);

                                to_remove.add(new Pair(zombie, currentTimeMillis()));
                                plant.changeHealth(-100000);

                                System.out.println(plant.getCol() + " " + plant.getRow());
                            }
                        }
                        else if(isColliding(plant, zombie)){
                            if(plant instanceof PeaBullet){
                                plant.changeHealth(-100000);
                                zombie.changeHealth(-1);
                            }
                            else{
                                plant.changeHealth(-1);
                                zombie.setBlocked(true);
                            }
                        }
                    }
                }

                for(Lawnmower lawnmower : lawnmowerManager.getLawnmowers()){
                    for(Zombie zombie : zombieManager.getZombies()){
                        if(isColliding(lawnmower, zombie)){
                            zombie.setImage(new Image("file:images\\zombie_normal_dying.gif", 125, 140, true, true));
                            to_remove.add(new Pair(zombie, currentTimeMillis()));

                            lawnmower.setVecX(lawnMoverSpeed);
                        }
                    }
                }

                plantManager.updatePlant(isPaused);
                lawnmowerManager.updateLawnmower(isPaused);
                zombieManager.updateZombies(isPaused);
                timerController.update(isPaused);
                sidebar.update(isPaused);
                sunManager.update(isPaused);

                ArrayList<Plant> plants = plantManager.getPlants();
                for(Plant p : plants) {
                    gc.drawImage(p.getImage(), p.getX(), p.getY());
                }
                ArrayList<Lawnmower> lawnmowers = lawnmowerManager.getLawnmowers();
                for(Lawnmower l: lawnmowers){
                    gc.drawImage(l.getImage(), l.getX(), l.getY());
                }
                ArrayList<Zombie> zombies = zombieManager.getZombies();
                for(Zombie z: zombies){
                    gc.drawImage(z.getImage(), z.getX(), z.getY());
                }
                gc.fillRect(timerController.getRectangleX(), timerController.getRectangleY(), timerController.getWidth(), timerController.getHeight());
                gc.drawImage(timerController.getZombieHead(), timerController.getZombieX(), timerController.getZombieY());
            }
        };
    }

    public void play(){
        Main.getPrimaryStage().setScene(new Scene(pane, 1530, 888));
        animationTimer.start();
    }

    private boolean isColliding(Plant p, Zombie z){

        if(p instanceof PeaBullet){
            if(p.getY() != z.getY() + 40)
                return false;
            if(abs(p.getX() - z.getX()) < 10)
                return true;
            return false;
        }

        if(p.getY() != z.getY() && p.getY() != z.getY() + 40)
            return false;
        if(z.getX() - p.getX() < 40 && z.getX() - p.getX() > 0){
            return true;
        }
        return false;
    }

    private boolean isColliding(Lawnmower l, Zombie z){
        if(l.getY() != z.getY())
            return false;
        if(abs(z.getX() - l.getX()) < 40){
            return true;
        }
        return false;
    }

    public void handle_zomb(ArrayList<Pair<Zombie,Long>> to_remove){
        for(Pair<Zombie, Long> p : new ArrayList<Pair<Zombie, Long>>(to_remove)){
            long currentTime = currentTimeMillis();
            if(currentTime - p.getValue() > 1000){
                p.getKey().changeHealth(-100000);
                to_remove.remove(p);
            }
        }
    }

    public static int getCol(int xPos){
        int temp = (xPos - gridX)/gridWidth;
        if(temp < 0 || temp >= 9)
            return -1;
        return temp;
    }

    public static int getRow(int yPos){
        int temp = (yPos - gridY)/gridHeight;
        if(temp < 0 || temp >= 5)
            return -1;
        return temp;
    }

    public void addSun(ImageView imageView){
        pane.getChildren().add(imageView);
    }

    public void collectSun(ImageView imageView){
        pane.getChildren().remove(imageView);
        sidebar.changeSunCounter(50);
    }

    public void setOccupied(int col, int row, boolean b){occupied[col][row] = b;}

    public static double getXPos(int col){
        return gridX + col*gridWidth;
    }

    public static double getYPos(int row){
        return gridY + row*gridHeight;
    }

    public void gameLost(){
        animationTimer.stop();
        System.out.println("Game Lost :(");
        long lastUpdate = currentTimeMillis();
        while(currentTimeMillis() - lastUpdate < 2000){}
        user.setGcNull();
        user.chooseLevel();
        mediaPlayer.stop();
    }

    public void gameWon(){
        isPaused = true;
        animationTimer.stop();
        mediaPlayer.stop();

        long lastUpdate = currentTimeMillis();
        while(currentTimeMillis() - lastUpdate < 2000){}

        System.out.println("Game Won :)");


        user.increaseLevel(level + 1);
        user.setGcNull();
        user.chooseLevel();
    }

    public int getLevel(){return level;}

    public Pane getRoot(){return pane;}
}