package Model;

public class Truck extends Vehicle {


    private Truck(){

    }



    public void printTruck(){


    }

    @Override
    public boolean upgrade() {
        return false;
    }

    @Override
    public boolean getUpgradeCost() {
        return false;
    }
}
