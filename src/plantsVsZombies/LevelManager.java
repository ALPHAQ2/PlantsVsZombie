package plantsVsZombies;

import javafx.scene.image.Image;

public class LevelManager {

    public static Image getImage(int level){
        if(level == 1){
            return new Image("file:images\\LawnOneLane.jpg");
        }
        if(level == 2){
            return new Image("file:images\\LawnThreeLane.jpg");
        }
        return new Image("file:images\\Lawn.jpg");
    }

    public static int numLanes(int level){
        if(level == 1){
            return 1;
        }
        if(level == 2){
            return 3;
        }
        return 5;
    }

    public static int offset(int level){
        if(level == 1){
            return 2;
        }
        if(level == 2){
            return 1;
        }
        return 0;
    }

    public static int numZombie(int level){
        if(level == 1){
            return 1;
        }
        if(level == 2){
            return 4;
        }
        if(level == 3){
            return 8;
        }
        if(level == 4){
            return 7;
        }
        if(level == 5){
            return 6;
        }
        return 100;
    }

    public static int numConeZombie(int level){
        if(level == 1){
            return 0;
        }
        if(level == 2){
            return 0;
        }
        if(level == 3){
            return 0;
        }
        if(level == 4){
            return 3;
        }
        if(level == 5){
            return 6;
        }
        return 100;
    }
}
