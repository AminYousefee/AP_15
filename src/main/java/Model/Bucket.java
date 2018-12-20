package Model;

import View.Farmys.BucketView;

public class Bucket implements Upgradable {
    int Level;
    Integer farmMoney;
    private int CurrentWater;

    public Bucket(Integer currentMoney) {
        farmMoney =currentMoney;
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
    public boolean fill(Integer CurrentMoney) {
        if (CurrentMoney < getFillingCost()) {
            return false;
        }
        CurrentWater = getMaxCapacity();
        CurrentMoney = CurrentMoney - getFillingCost();
        return true;

    }


    private int getFillingCost() {
        return 0;
        //todo
    }

    public int getMaxCapacity() {
        return 0;
        //todo
    }

    public void printBucket() {
        BucketView.PrintBucket(CurrentWater, Level);
    }


    //Finished
    @Override
    public boolean upgrade(Integer CurrentMoney) {
        if (CurrentMoney < getUpgradeCost()) {
            return false;
        }
        Level += 1;
        return true;

    }




    @Override
    public int getUpgradeCost() {
        return 0;
        //todo
    }

    public boolean hasEnoughWater() {
        return CurrentWater>=1;
    }
}