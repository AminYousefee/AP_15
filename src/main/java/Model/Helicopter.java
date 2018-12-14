package Model;

public class Helicopter extends Vehicle{
    public void printHelicopter() {
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
