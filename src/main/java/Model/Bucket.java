package Model;

import View.Farmys.BucketView;
import controller.ImageViewSprite;
import controller.InputProcessor;
import controller.Main;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Bucket implements Upgradable {
    static String path = "./static/Service/Well/";


    int Level;
    Integer farmMoney;
    ImageView imageView;
    ImageViewSprite sprite;
    int numOfTurns;
    private int CurrentWater;

    public Bucket(Integer currentMoney) {
        farmMoney = currentMoney;
        CurrentWater = getMaxCapacity();
    }

    //Finished
    public int getCurrentWater() {
        return CurrentWater;
    }

    public boolean consume() {
        if (CurrentWater == 0) {
            return false;
        } else {
            CurrentWater -= 1;
            return true;
        }

    }

    // Finished
    public boolean fill(Farm farm) {
        if (farm.getCurrentMoney() < getFillingCost()) {
            return false;
        }
        CurrentWater = getMaxCapacity();
        farm.pay(getFillingCost());
        return true;

    }

    private int getFillingCost() {
        switch (Level) {
            case 0:
                return 19;
            case 1:
                return 17;
            case 2:
                return 15;
            case 3:
                return 7;
            default:
                return 0;
        }
        //todo
    }

    public int getMaxCapacity() {
        switch (Level) {
            case 0:
                return 5;
            case 1:
                return 7;
            case 2:
                return 10;
            case 3:
                return 100;
            default:
                return 0;
        }
    }

    public void printBucket() {
        BucketView.PrintBucket(CurrentWater, Level);
    }

    //Finished
    @Override
    public boolean upgrade(Farm farm) {
        if (farm.getCurrentMoney() < getUpgradeCost()) {
            return false;
        }
        farm.setCurrentMoney(farm.getCurrentMoney() - getUpgradeCost());
        Level += 1;
        return true;

    }

    @Override
    public int getUpgradeCost() {
        return 0;
        //todo
    }

    public boolean hasEnoughWater() {
        return CurrentWater >= 1;
    }

    public void show() {
        Image image = null;
        try {
            image = new Image(new FileInputStream(path + "01.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        /*try {

            ImageView imageView = new ImageView(image);
            AnchorPane.setTopAnchor(imageView,50.0);
            AnchorPane.setLeftAnchor(imageView,50.0);
            Main.pane.getChildren().add(imageView);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/


        if (imageView == null || !(Main.pane.getChildren().contains(imageView))) {
            imageView = new ImageView(image);
            Main.pane.getChildren().add(imageView);
            //AnchorPane.setTopAnchor(imageView, Arrays.asList(InputProcessor.game.getFarm().factories).indexOf(this) * 50.0 + 100.0);
            //AnchorPane.setLeftAnchor(imageView, 70.0);
            imageView.setX(250.0);
            imageView.setY(Arrays.asList(InputProcessor.game.getFarm().factories).indexOf(this) * 50.0 + 100.0);
            imageView.setOnMouseClicked(keyEvent -> {
                InputProcessor.process("well");
                numOfTurns = 10 ;
                //sprite = new ImageViewSprite(imageView, image, 4, 4, 16, (int) (image.getWidth() / 4.0), (int) (image.getHeight() / 4), 30, 0, 0, false);
                sprite.start();
            });


        }


        //ImageView imageView = new ImageView();
        //Image image1 = new Image((new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Workshops/Cake (Cookie Bakery)/01.png")));
        sprite = new ImageViewSprite(imageView, image, 4, 4, 16, (int) (image.getWidth() / 4.0), (int) (image.getHeight() / 4), 30, 0, 0, false);
        //Main.pane.getChildren().add(imageView);
        sprite.start();
        if (numOfTurns == 0) {
            sprite.stop();
        }
        InputProcessor.game.getFarm().getMap().threads.add(new Thread(this::turn));
    }

    public void turn() {
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBb");
        numOfTurns--;
        if (numOfTurns == 0) {
            sprite.stop();
        }
        Object object = new Object();
        synchronized (object){
            try {
                object.wait(InputProcessor.getSpeed()*10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        InputProcessor.game.getFarm().getMap().threads.add(new Thread(() -> {
            turn();
            sprite.imageView.setViewport(new Rectangle2D(0,0,150,544/4));
        }));

    }

}
