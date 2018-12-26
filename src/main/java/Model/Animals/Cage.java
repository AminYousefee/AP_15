package Model.Animals;

import Model.Positions.MapPosition;
import Model.Upgradable;
import controller.InputProcessor;

public class Cage implements Upgradable {
    transient WildAnimal wildAnimal;
    int CompletnessPercetage;
    int remainingTimeTo;
    int turnsCompleted;

    public Cage(int remainingTimeTo, int numOfWildAnimalsInTheCell) {
        CompletnessPercetage = 0;

    }

    public Cage(WildAnimal wildAnimal) {
        this.wildAnimal =wildAnimal;
    }

    public int getCompletnessPercetage() {
        return CompletnessPercetage;
    }

    public void setCompletnessPercetage(int completnessPercetage) {
        CompletnessPercetage = completnessPercetage;
    }

    public int getCagingPrice(int numberOfWildAnimalsInTheCell) {
        //todo
        return 0;
    }

    public void addCompletenesPercentage() {
        CompletnessPercetage += 20;
        if (CompletnessPercetage >= getProgressMaxValue()) {
            if (CompletnessPercetage ==getProgressMaxValue()+20){
                System.out.println("It's already completed:the cage");
            }else {
                turnsCompleted =0;
            }
            CompletnessPercetage = getProgressMaxValue();




        }

    }

    public void turn() {
        if (this.CompletnessPercetage ==getProgressMaxValue()){
            turnsCompleted++;
        }
        if (turnsCompleted == getEscapeTurn() && wildAnimal.getPosition() instanceof MapPosition) {
            wildAnimal.escape();
        }
        if (getCompletnessPercetage() != getProgressMaxValue()) {

            setCompletnessPercetage(getCompletnessPercetage() - 5);
        }
        if (getCompletnessPercetage() <= 0) {
            setCompletnessPercetage(0);
        }

    }

    private int getEscapeTurn() {
        return 6 + 2 * InputProcessor.game.getFarm().getCagesLevel();
    }

    @Override
    public boolean upgrade(Integer CurrentMoney) {

        Integer integer = InputProcessor.game.getFarm().getCagesLevel();
        if (integer == 3) {
            System.out.println("More upgrade not possible");
            return false;
        } else if (CurrentMoney < getUpgradeCost()) {
            System.out.println("Not Enough money the specified upgrade");
            return false;
        } else {
            CurrentMoney = CurrentMoney - getUpgradeCost();
            integer = integer + 1;

            return true;


        }
    }

    @Override
    public int getUpgradeCost() {
        switch (InputProcessor.game.getFarm().getCagesLevel()) {
            case 0:
                return 0;
            case 1:
                return 100;
            case 2:
                return 500;
            case 3:
                return 5000;
            default:
                return 0;
        }
    }


    public int getProgressMaxValue() {
        switch (InputProcessor.game.getFarm().getCagesLevel()) {
            case 0:
                return 100;
            case 1:
                return 60;
            case 2:
                return 40;
            case 3:
                return 20;
            default:
                return 0;
        }
    }


}
