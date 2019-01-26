package Model;

import controller.InputProcessor;
import controller.Main;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Grass {

    //num 0-100
    int num = 0;
    ImageView imageView;
    Cell cell;

    public Grass(Cell cell) {
        this.cell = cell;
    }/*
    public Grass(){

    }*/

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        show();
    }

    public boolean getEaten() {
        if (num == 0) {

        } else {
            num -= 1;
            show();
        }
        return false;

    }

    public void show() {
        if (imageView == null || !(Main.pane.getChildren().contains(imageView))) {
            Image image = null;
            try {
                image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Grass/grass4.png"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageView = new ImageView(image);
            AnchorPane.setTopAnchor(imageView, cell.getX());
            AnchorPane.setLeftAnchor(imageView, cell.getY());


            if (Main.pane != null) {
                Main.pane.getChildren().add(imageView);
            }
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    InputProcessor.process("plant " + cell.getMapPosition().getX() + " " + cell.getMapPosition().getY());
                }
            });
        }
        int frameWidth = 48;
        int frameHeight = 48;
        int t = (int) (num / (6.25)) - 1;
        int currentCol = t % 4;
        int currentRow = t / 4;
        imageView.setViewport(new Rectangle2D(currentCol * frameWidth, currentRow * frameHeight, frameWidth, frameHeight));

    }
}
