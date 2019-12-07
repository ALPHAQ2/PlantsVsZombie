package plantsVsZombies;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

import static java.lang.System.currentTimeMillis;

public class TimerController implements Serializable {

    public Rectangle getRectangle() {
        if(rectangle == null){
            rectangle = new Rectangle(100, 10);
            rectangle.setFill(Color.DARKCYAN);
        }
        return rectangle;
    }

    public int getRectangleX() {
        return rectangleX;
    }

    public int getRectangleY() {
        return rectangleY;
    }

    public Image getZombieHead() {
        if(zombieHead == null){
            zombieHead = new Image("file:images\\ZombieHead.png", 65, 65, true, true);
        }
        return zombieHead;
    }

    public double getZombieX() {
        return zombieX;
    }

    public double getZombieY() {
        return zombieY;
    }

    private transient Rectangle rectangle;
    private final int rectangleX = 1100;
    private final int rectangleY = 30;
    private final int width = 300;
    private final int height = 20;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private transient Image zombieHead;
    private double zombieX = 1400;
    private double zombieY = 12;
    private long lastUpdate;
    private final double vecX;

    public TimerController(){
        rectangle = new Rectangle(100, 10);
        rectangle.setFill(Color.DARKCYAN);
        zombieHead = new Image("file:images\\ZombieHead.png", 65, 65, true, true);
        lastUpdate = currentTimeMillis();
        vecX = -0.005;
    }

    public void update(boolean isPaused){
        if(isPaused){
            lastUpdate = currentTimeMillis();
            return;
        }
        long currentTime = currentTimeMillis();
        zombieX = Math.max(1100.0, zombieX + vecX*(currentTime - lastUpdate));
        lastUpdate = currentTime;
    }
}
