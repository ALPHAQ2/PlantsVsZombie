package plantsVsZombies;

import static java.lang.System.currentTimeMillis;


public class Sunflower extends Plant {

    private long lastUpdate;
    private GameController gameController;

    public Sunflower(int col, int row, GameController gameController){
        super(col, row, "file:images\\Sunflower_transparent.gif", 125, 200);
        lastUpdate = currentTimeMillis();
        this.gameController = gameController;
    }

    @Override
    public void update(boolean isPaused){
        if(isPaused){
            lastUpdate = currentTimeMillis();
            return;
        }
        long currentTime = currentTimeMillis();
        if(currentTime - lastUpdate > 10000){
            lastUpdate = currentTimeMillis();
            new Sun(this.col, this.row, false, gameController);
        }
    }
}
