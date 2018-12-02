package Model;
import Model.Animals.Animal;
import Model.Factories.Factory;
import Model.Positions.Position;

public class Farm{
    public static final int POSSIBLE_NUMBER_OF_FACTORIES;
    Map map;
    Vehicle vehicle;
    Well well;

    private Factory[] factories= new Factory[POSSIBLE_NUMBER_OF_FACTORIES];
    private Warehouse = Warehouse.getInstanse();
    private Truck truck=Truck.getInstance();
    private Helicopter helicopter=Helicopter.getInstance();
    private Farm(){



    }

    
    public void save(){

    }








    public void pickUp(Position position) {

    }


    public boolean cage(Position position){
        return false;
    }



    public Well getWell(){
        return this.well;
    }


    public void turn(){


    }


































    public void buyAnimal(Animal.AnimalInfo animalInfo){



    }
    public void plant(Position position) {
    }



}
