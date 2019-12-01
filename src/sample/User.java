package sample;

import java.io.*;

public class User implements Serializable {

    private final String username;
    private int lastLevelUnlocked;
    private GameController gc;

    User(String username){
        this.username = username;
        lastLevelUnlocked = 1;
        gc = null;
        System.out.println("I'm at user constructor");
    }

    public void play(){
        gc.initialise();
        gc.play();
//        gc = null;
//        chooseLevel();
    }

    public void play(int level){
        gc = new GameController(this, level);
        play();
    }

    public void chooseLevel(){
        if(gc != null){
            System.out.println("GC isnt null so playing it");
            play();
            return;
        }
        ChooseLevelController clc = new ChooseLevelController(this);
        clc.chooseLevel();
    }

    public void setGcNull(){
        System.out.println("I am at setGcNull");
        gc = null;
    }

    public void increaseLevel(int i){
        lastLevelUnlocked = Math.min(Math.max(i, lastLevelUnlocked), 5);
    }

    public void serialize() throws IOException{
        ObjectOutputStream out = null;
        try{
            out = new ObjectOutputStream(new FileOutputStream(username + ".txt"));
            out.writeObject(this);
        }
        finally {
            out.close();
        }
    }

    public static User deserialize(String username) throws IOException, ClassNotFoundException{
        ObjectInputStream in = null;
        User u = null;
        try{
            in = new ObjectInputStream(new FileInputStream(username + ".txt"));
            u = (User) in.readObject();
        }
        finally {
            if(in != null)
                in.close();
        }
        return u;
    }

    public int getLevel(){return lastLevelUnlocked;}

}
