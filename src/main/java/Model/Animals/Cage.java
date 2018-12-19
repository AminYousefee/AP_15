package Model.Animals;

import Model.Upgradable;

import java.util.Collections;

public class Cage extends Upgradable {
    int CompletnessPercetage;
    int remainingTimeTo;
    int numOfWildAnimalsInTheCell;

    public Cage( int remainingTimeTo, int numOfWildAnimalsInTheCell) {
        CompletnessPercetage += 20;
        this.numOfWildAnimalsInTheCell = numOfWildAnimalsInTheCell;
    }

    public int getCompletnessPercetage() {
        return CompletnessPercetage;
    }


    public int getCagingPrice(int numberOfWildAnimalsInTheCell){
        //todo
    }

    public void addCompletenesPercentage() {
        CompletnessPercetage+=20;
        if (CompletnessPercetage>=100){
            CompletnessPercetage=100;

        }
    }



    public void turn(){
        if ()

    }
}
