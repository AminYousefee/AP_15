package Model;
import Model.Animals.Animal;
import Model.Factories.Factory;
import Model.Positions.Position;

public class Farm{
    public static final int POSSIBLE_NUMBER_OF_FACTORIES = 6;
    Map map;
    Integer CurrentMoney;

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public Helicopter getHelicopter() {
        return helicopter;
    }

    public void setHelicopter(Helicopter helicopter) {
        this.helicopter = helicopter;
    }


    Bucket bucket;

    private Factory[] factories= new Factory[POSSIBLE_NUMBER_OF_FACTORIES];


    private Truck truck;
    private Helicopter helicopter;
    private Farm(){



    }

    
    public void save(){

    }








    public void pickUp(Position position) {

    }


    public boolean cage(Position position){
        return false;
    }



    public Bucket getBucket(){
        return this.bucket;
    }


    public void turn(){


    }


    public Factory findFactory(String string){
        return null;

    }


    public Map getMap() {
        return map;
    }

    public void buyAnimal(Animal.AnimalInfo animalInfo){



    }
    public void plant(Position position) {
    }


    public void printWorkshops() {

    }
}
