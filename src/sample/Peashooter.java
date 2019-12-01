package sample;

import javafx.scene.image.Image;

import static java.lang.System.currentTimeMillis;

public class Peashooter extends Plant{

    private PlantManager plantManager;
    private long lastUpdate;

    public Peashooter(int col, int row, PlantManager plantManager){
        super(col, row, "file:images\\Peashooter_transparent_gif.gif", 125, 200);
        this.plantManager = plantManager;
        lastUpdate = currentTimeMillis();
    }

    @Override
    public void update(boolean isPaused){
        if(isPaused){
            lastUpdate = currentTimeMillis();
            return;
        }
        long currentTime = currentTimeMillis();
        if(currentTime - lastUpdate > 1700){
            lastUpdate = currentTime;
            plantManager.addPlant(new PeaBullet(col, row));
        }
    }

}
