package Model.Animals;

public class FarmAnimal extends Animal{
    @Override
    public boolean move() {
        return false;
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
