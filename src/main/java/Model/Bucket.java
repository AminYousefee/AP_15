package Model;

import View.Farmys.BucketView;

public class Bucket implements Upgradable {
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
        return CurrentWater >= 1;
    }


}
