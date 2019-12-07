package plantsVsZombies;

import static java.lang.System.currentTimeMillis;

public class CherryBomb extends Plant {

    private boolean explode;
    private long lastUpdate;
    private GameController gc;

    public CherryBomb(int col, int row, GameController gc){
        super(col, row, "file:images\\CherryBomb.gif", 200, 200);
        explode = false;
        this.gc = gc;
        lastUpdate = currentTimeMillis();
    }

    @Override
    public void update(boolean isPaused){
        if(isPaused){
            lastUpdate = currentTimeMillis();
            return;
        }
        if(currentTimeMillis() - lastUpdate > 2000){
            explode = true;
        }
        if(currentTimeMillis() - lastUpdate > 3000){
            this.changeHealth(-10000);
        }
    }

    public boolean getExplode(){return explode;}
}
