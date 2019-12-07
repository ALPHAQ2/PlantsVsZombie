package plantsVsZombies;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.System.currentTimeMillis;

public class ZombieManager implements Serializable {

    ArrayList<Zombie> zombies;
    private long lastUpdate;
    private long lastUpdate1;
    private Random random;
    private int numZombie;
    private GameController gc;
    private int offset;
    private int numLanes;
    private int numConeZombie;
    private int level;

    public ZombieManager(int level, GameController gc){
        this.numZombie = LevelManager.numZombie(level);
        this.offset = LevelManager.offset(level);
        this.numLanes = LevelManager.numLanes(level);
        this.gc = gc;
        this.level = gc.getLevel();
        this.numConeZombie = LevelManager.numConeZombie(level);
        zombies = new ArrayList<Zombie>();
        lastUpdate = currentTimeMillis();
        lastUpdate1 = currentTimeMillis();
        random = new Random();
    }

    public void updateZombies(boolean isPaused){
        for(Zombie x : new ArrayList<Zombie>(zombies)){
            x.update(isPaused);
//            System.out.println("Zombie is Updating " + isPaused);
            if(x.getX() < 100){
                gc.gameLost();
                zombies.remove(x);
            }
            if(x.getHealth() <= 0){
                zombies.remove(x);
            }
        }
        if(isPaused){
            lastUpdate = currentTimeMillis();
            return;
        }
        long currentTime = currentTimeMillis();

        if(level == 6){
            if (currentTime - lastUpdate > 5000 && numZombie > 0) {
                addZombie(new Zombie(random.nextInt(numLanes) + offset));
                numZombie--;
                lastUpdate = currentTimeMillis();
            }
            if (currentTime - lastUpdate1 > 7500 && numConeZombie > 0) {
                addZombie(new ConeHeadZombie(random.nextInt(numLanes) + offset));
                numConeZombie--;
                lastUpdate1 = currentTimeMillis();
            }
        }
        else {
            if (currentTime - lastUpdate > 10000 && numZombie > 0) {
                addZombie(new Zombie(random.nextInt(numLanes) + offset));
                numZombie--;
                lastUpdate = currentTimeMillis();
            }
            if (currentTime - lastUpdate1 > 15000 && numConeZombie > 0) {
                addZombie(new ConeHeadZombie(random.nextInt(numLanes) + offset));
                numConeZombie--;
                lastUpdate1 = currentTimeMillis();
            }
        }
        if(numZombie == 0 && numConeZombie == 0 && zombies.isEmpty()){
            gc.gameWon();
        }
    }

    public void addZombie(Zombie z){
        zombies.add(z);
    }

    public ArrayList<Zombie> getZombies(){return zombies;}

}
