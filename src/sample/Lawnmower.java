package sample;

import javafx.scene.image.Image;

import java.io.Serializable;

import static java.lang.System.currentTimeMillis;

public class Lawnmower implements Serializable {
    private final int row;
    private final double y;
    private double x;
    private double vecX;
    private long lastUpdate;
    private transient Image image;

    public Lawnmower(int row){
        this.row = row;
        y = GameController.getYPos(row);
        x = 280;
        vecX = 0;
        lastUpdate = currentTimeMillis();
        image = new Image("file:images\\lawnmower.png", 125, 125, true, true);
    }

    public void update(boolean isPaused){
        if(isPaused){
            lastUpdate = currentTimeMillis();
            return;
        }
        long currentTime = currentTimeMillis();
        x += vecX*(currentTime - lastUpdate);
        lastUpdate = currentTime;
    }

    public Image getImage(){
        if(image == null){
            image = new Image("file:images\\lawnmower.png", 125, 125, true, true);
        }
        return image;
    }
    public double getX(){return x;}
    public double getY(){return y;}
    public void setVecX(double d){vecX = d;}
}
