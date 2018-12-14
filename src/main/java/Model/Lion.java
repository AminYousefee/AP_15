package Model;

import Model.Animals.WildAnimal;

public class Lion extends WildAnimal {
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
