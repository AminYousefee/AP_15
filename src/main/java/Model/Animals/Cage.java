package Model.Animals;

import Model.Upgradable;

import java.util.Collections;

public class Cage implements Upgradable {
    transient WildAnimal wildAnimal;

    public void setCompletnessPercetage(int completnessPercetage) {
        CompletnessPercetage = completnessPercetage;
    }

    int CompletnessPercetage;
    int remainingTimeTo;

    public Cage( int remainingTimeTo, int numOfWildAnimalsInTheCell) {
        CompletnessPercetage += 20;

    }

    public int getCompletnessPercetage() {
        return CompletnessPercetage;
    }


    public int getCagingPrice(int numberOfWildAnimalsInTheCell){
        //todo
        return 0;
    }

    public void addCompletenesPercentage() {
        CompletnessPercetage+=20;
        if (CompletnessPercetage>=100){
            CompletnessPercetage=100;


        }

    }



    public void turn(){
        setCompletnessPercetage(getCompletnessPercetage()-5);
        if (getCompletnessPercetage()==0){
            wildAnimal.setCage(null);
        }

    }

    @Override
    public boolean upgrade(Integer CurrentMoney) {
        return false;
    }

    @Override
    public int getUpgradeCost() {
        return 0;
    }
}
