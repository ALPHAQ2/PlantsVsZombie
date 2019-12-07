package plantsVsZombies;

import javafx.scene.image.Image;

import java.io.Serializable;

import static java.lang.System.currentTimeMillis;

public class Zombie implements Serializable {
    private final int row;
    private final double y;
    private double x;
    private double vecX;
    private long lastUpdate;
    private transient Image image;
    private int health = 10;
    private boolean blocked = false;

    public Zombie(int row){
        this.row = row;
        y = GameController.getYPos(row);
        x = 1530;
        vecX = -0.03;
        lastUpdate = currentTimeMillis();
        image = new Image("file:images\\zombie_normal.gif", 140, 140, true, true);
    }

    public void update(boolean isPaused){
        if(isPaused || blocked){
            lastUpdate = currentTimeMillis();
            return;
        }
        long currentTime = currentTimeMillis();
        x += vecX*(currentTime - lastUpdate);
        lastUpdate = currentTime;
    }

    public Image getImage(){
        if(image == null){
            image = new Image("file:images\\zombie_normal.gif", 140, 140, true, true);
        }
        return image;
    }
    public double getX(){return x;}
    public double getY(){return y;}

    public void setBlocked(boolean b){
        blocked = b;
    }

    public void changeHealth(int delta){health += delta;}

    public int getHealth(){
        return health;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    protected void setHealth(int health) {
        this.health = health;
    }
}
