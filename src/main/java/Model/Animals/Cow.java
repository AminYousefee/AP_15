package Model.Animals;

public class Cow extends ProductiveAnimal {
    @Override
    public boolean eat() {
        return false;
    }

    @Override
    public boolean produce() {
        return false;
    }

    @Override
    public boolean die() {
        return false;
    }
}
