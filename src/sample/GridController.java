package sample;

import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

import static sample.SidebarController.seedFormat;
//import static sample.SidebarController.activeSeed;

public class GridController {
    private GridPane grid;
    private final int numCols = 9;
    private final int numRows = 5;

    private PlantManager plantManager;

    public GridController(PlantManager plantManager){
        this.plantManager = plantManager;
        grid = new GridPane();
        grid.relocate(380, 120);
        grid.setGridLinesVisible(true);

        for(int i = 0; i < numCols; i++){
                ColumnConstraints colConst = new ColumnConstraints(120);
                grid.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints(147);
            grid.getRowConstraints().add(rowConst);
        }

        for(int i = 0; i < numCols; i++){
            for(int j = 0; j < numRows; j++){
                addPane(i, j);
            }
        }

    }

    private void addPane(int col, int row){
//        Pane pane = new Pane();
//        pane.setOnDragOver(event -> {
//            Dragboard db = event.getDragboard();
//            if(db.hasContent(seedFormat) && activeSeed != null){
//                event.acceptTransferModes(TransferMode.MOVE);
//            }
//        });
//        pane.setOnDragDropped(event -> {
//            Dragboard db = event.getDragboard();
//            if(db.hasContent(seedFormat)){
////                ImageView peaShooter = new ImageView(activeSeed);
////                pane.getChildren().add(peaShooter);
//                plantManager.addPlant(new Peashooter(col, row));
//                activeSeed = null;
//                event.setDropCompleted(true);
//                System.out.println("Drag Over");
//            }
//        });
//        grid.add(pane, col, row);
    }

    public GridPane getContainer(){return grid;}
}
