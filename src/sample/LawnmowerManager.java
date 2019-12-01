package sample;

import java.io.Serializable;
import java.util.ArrayList;

public class LawnmowerManager implements Serializable {

    ArrayList<Lawnmower> lawnmowers;

    public LawnmowerManager(){
        lawnmowers = new ArrayList<Lawnmower>();
    }

    public void updateLawnmower(boolean isPaused){
        for(Lawnmower x : new ArrayList<>(lawnmowers)){
            x.update(isPaused);
            if(x.getX() > 1600){
                lawnmowers.remove(x);
            }
        }
    }

    public void addLawnmower(Lawnmower l){
        lawnmowers.add(l);
    }

    public ArrayList<Lawnmower> getLawnmowers(){return lawnmowers;}

}