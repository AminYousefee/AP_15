package Model;

import View.Farmys.BucketView;
import controller.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Bucket implements Upgradable {
    static String path = "./static/Service/Well/";





    int Level;
    Integer farmMoney;
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
        farm.setCurrentMoney(farm.getCurrentMoney()-getUpgradeCost());
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




    public static void show(){
        try {
            Image image =  new Image(new FileInputStream(path+"01.png"));
            ImageView imageView = new ImageView(image);
            AnchorPane.setTopAnchor(imageView,50.0);
            AnchorPane.setLeftAnchor(imageView,50.0);
            Main.pane.getChildren().add(imageView);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
