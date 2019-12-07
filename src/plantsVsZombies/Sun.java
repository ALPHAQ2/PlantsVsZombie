package plantsVsZombies;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Sun {
    private final int row, col;
    private final double x;
    private final double vecY = 0.15;
    private double y;
    Image image;
    private long lastUpdate;
    private ImageView imageView;
    private Timeline timeline;
    private GameController gc;

    public Sun(int col, int row, boolean moving, GameController gc){
        this.col = col;
        this.row = row;
        this.gc = gc;
        x = GameController.getXPos(col);
        if(moving)
            y = 0;
        else
            y = GameController.getYPos(row);
        image = new Image("file:images\\Sun.png", 100, 100, true, true);
        imageView = new ImageView(image);
        imageView.relocate(x, y);
        if(moving){
            timeline = new Timeline();
            timeline.setCycleCount(1);
            timeline.setAutoReverse(false);
            KeyValue keyValue = new KeyValue(imageView.yProperty(), GameController.getYPos(row));
            KeyFrame keyFrame = new KeyFrame(Duration.millis(15000), keyValue);
            timeline.getKeyFrames().add(keyFrame);
            timeline.play();
        }
        gc.addSun(imageView);
        imageView.setOnMouseClicked(event -> {
            gc.collectSun(imageView);
        });
    }

    public Timeline getTimeline(){return timeline;}
    public Image getImage(){return image;}
    public ImageView getImageView(){return imageView;}
    public double getX(){return x;}
    public double getY(){return y;}
}