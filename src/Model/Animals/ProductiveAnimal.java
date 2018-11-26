package Model.Animals;

public class ProductiveAnimal extends FarmAnimal{
    public double getSatisfiction() {
        return satisfiction;
    }

    public void setSatisfiction(double satisfiction) {
        this.satisfiction = satisfiction;
    }

    private double satisfiction;

    public abstract boolean eat();
    public abstract boolean produce();
    public abstract boolean die();

}
