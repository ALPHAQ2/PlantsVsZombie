package sample;

import static java.lang.System.currentTimeMillis;

public class PeaBullet extends Plant {
    private double x, y;
    private final double vecX = 0.2;
    private long lastUpdate;
    public PeaBullet(int col, int row){
        super(col, row, "file:images\\Pea_bullet.png", 40, 40);
        x = GameController.getXPos(col) + 80;
        y = GameController.getYPos(row) + 40;
        lastUpdate = currentTimeMillis();
    }

    @Override
    public void update(boolean isPaused){
        if(isPaused){
            lastUpdate = currentTimeMillis();
            return;
        }
        long currentTime = currentTimeMillis();
        x += vecX*(currentTime-lastUpdate);
        lastUpdate = currentTime;
    }

    @Override
    public double getX(){return x;}
    @Override
    public double getY(){return y;}
}
