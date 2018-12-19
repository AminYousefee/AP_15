package Model;

import Model.Animals.Animal;
import Model.Animals.Cage;
import Model.Animals.WildAnimal;
import Model.Factories.Factory;
import Model.Positions.MapPosition;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.ArrayList;
import java.util.List;

public class Farm {
    public static final int POSSIBLE_NUMBER_OF_FACTORIES = 6;
    Map map;
    Integer CurrentMoney;
    Bucket bucket;
    private Factory[] factories = new Factory[POSSIBLE_NUMBER_OF_FACTORIES];
    private Truck truck;
    private Helicopter helicopter=null;

    private Farm() {
        map = new Map();
        truck  = new Truck(CurrentMoney);
        helicopter = new Helicopter(CurrentMoney);
        bucket = new Bucket(CurrentMoney);




    }
    private Farm(Map map){
        this.map = map;
        new

    }

    public Integer getCurrentMoney() {
        return CurrentMoney;
    }

    public void setCurrentMoney(Integer currentMoney) {
        CurrentMoney = currentMoney;
    }

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

    public void save() {

    }


    public void pickUp(MapPosition mapPosition) {
        Map map = getMap();
        Cell cell = map.getCellByPosition(mapPosition);
        cell.collect();


    }


    public boolean cage(MapPosition mapPosition) {
        Cell cell = getMap().getCellByPosition(mapPosition);
        List<Item> items = cell.getItems();
        ArrayList<WildAnimal> wildAnimals = new ArrayList<>(0);
        for (Item item : items) {
            if (item instanceof WildAnimal) {
                wildAnimals.add((WildAnimal) item);
            }
        }
        if (wildAnimals.size() == 0) {
            System.out.println("No Wild Animal In the Specified Cell");
        }
        Cage cage =cell.getCage();
        if (cage == null) {
            cell.setCage(new Cage());
            cell.getCage();
        } else {
            cage.addCompletenesPercentage();
        }

        for (WildAnimal wildAnimal : wildAnimals) {
            wildAnimal.getCaged(cage);

        }

    }


    public Bucket getBucket() {
        return this.bucket;
    }


    public void turn() {
        for (Factory factory:factories) {
            factory.turn();
        }
        map.turn();
        truck.turn();
        helicopter.turn();


    }


    public Factory findFactory(String string) {
        return null;

    }


    public Map getMap() {
        return map;
    }

    public void buyAnimal(Animal animal) {
        if (animal.getPrice() > this.CurrentMoney) {
            System.out.println("Not Enough Money");
        } else {

            CurrentMoney - = animal.getPrice();
            getMap().addAnimal(animal);

        }

    }

    public void plant(MapPosition mapPosition) {
        Cell cell = getMap().getCellByPosition(mapPosition);
        if (bucket.hasEnoughWater()) {
            cell.getGrass().setNum(100);
            bucket.consume();

        } else {
            System.out.println("Bucket Doesn't Have Enough Water");
        }


    }


    public void printWorkshops() {

    }


}
