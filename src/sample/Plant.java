package sample;

import javafx.scene.image.Image;

import java.io.Serializable;

public abstract class Plant implements Serializable {

    int col, row;
    private transient Image image;
    private String imageUrl;
    private int imageWidth;
    private int imageHeight;
    private int health = 1000;

    public Plant(int col, int row, String imageUrl, int imageWidth, int imageHeight){
        this.col = col;
        this.row = row;
        this.imageUrl = imageUrl;
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;
        this.image = new Image(imageUrl, imageWidth, imageHeight, true, true);
    }

    public void update(boolean isPaused){}

    public Image getImage(){
        if(image == null){
            image = new Image(imageUrl, imageWidth, imageHeight, true, true);
        }
        return image;
    }

    public double getX(){return GameController.getXPos(col);}
    public double getY(){return GameController.getYPos(row);}

    public int getCol() {
        return col;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getRow() {
        return row;
    }

    public int getHealth(){return health;}
    public void changeHealth(int delta){ health += delta;}
}
