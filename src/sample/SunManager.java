package sample;

import java.io.Serializable;
import java.util.Random;

import static java.lang.System.currentTimeMillis;

public class SunManager implements Serializable {

    private long lastUpdate;
    private Random random;
    private GameController gameController;
    private int numLanes;
    private int offset;
    private int level;

    SunManager(GameController gameController, int level){
        this.gameController = gameController;
        this.level = level;
        lastUpdate = currentTimeMillis();
        random = new Random();
        this.numLanes = LevelManager.numLanes(level);
        this.offset = LevelManager.offset(level);
    }

    public void update(boolean isPaused){
        if(isPaused){
            lastUpdate = currentTimeMillis();
            return;
        }
        long currentTime = currentTimeMillis();
        if(level == 6){
            if(currentTime - lastUpdate > 2000){
                lastUpdate = currentTime;
                new Sun(random.nextInt(9), random.nextInt(numLanes)  + offset, true, gameController);
            }
            return;
        }
        if(currentTime - lastUpdate > 10000){
            lastUpdate = currentTime;
            new Sun(random.nextInt(9), random.nextInt(numLanes)  + offset, true, gameController);
        }
    }

}
