package sample;

import javafx.scene.image.Image;

public class ConeHeadZombie extends Zombie {

    private transient Image imageMoving;
    private transient Image imageEating;

    ConeHeadZombie(int row){
        super(row);
    }
    @Override
    public void setBlocked(boolean b){
        super.setBlocked(b);
        if(!b){
            if(imageMoving == null)
                imageMoving = new Image("file:images\\conehead_zombie_moving.gif", 160, 160, true, true);
            super.setImage(imageMoving);
        }
        else{
            if(imageEating == null)
                imageEating = new Image("file:images\\ConeheadZombieAttack.gif", 140, 140, true, true);
            super.setImage(imageEating);
        }
    }
}
