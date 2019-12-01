package sample;

import java.io.Serializable;
import java.util.ArrayList;

public class PlantManager implements Serializable {

    private ArrayList<Plant> plants;
    private GameController gc;

    public PlantManager(GameController gc){
        this.gc = gc;
        plants = new ArrayList<Plant>();
    }

    public void updatePlant(boolean isPaused){
        for(Plant x : new ArrayList<Plant>(plants)){
            x.update(isPaused);
            if(x.getX() > 1600){
                plants.remove(x);
            }
            if(x.getHealth() <= 0){
                gc.setOccupied(x.getCol(), x.getRow(), false);
                plants.remove(x);
            }
        }
    }

    public void addPlant(Plant p){
        plants.add(p);
    }

    public void removePlant(int col, int row){
        for(Plant p: new ArrayList<Plant>(plants)){
            if(p.getCol() == col && p.getRow() == row && !(p instanceof PeaBullet))
                plants.remove(p);
        }
    }

    public ArrayList<Plant> getPlants(){return plants;}

}
