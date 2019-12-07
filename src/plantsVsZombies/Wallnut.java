package plantsVsZombies;

import javafx.scene.image.Image;

public class Wallnut extends Plant {
    boolean check1 = false,check2  = false;
    public Wallnut(int col, int row){
        super(col, row, "file:images\\wallnut.gif", 90, 120);
    }
    public void changeHealth(int delta){
        super.changeHealth(delta);
        if(super.getHealth() > 500 && super.getHealth() < 750 && !check1) {
            super.setImage(new Image("file:images\\wallnut_2.png", 90, 120, true, true));
            System.out.println("#####");
            check1 = true;
        }
        else if(super.getHealth() < 500 && !check2) {
            super.setImage(new Image("file:images\\wallnut_3.png", 90, 120, true, true));
            System.out.println("&&&");
            check2 = true;
        }
    }
}
