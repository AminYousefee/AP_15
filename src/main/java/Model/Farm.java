package Model;

import Model.Animals.Animal;
import Model.Animals.Cage;
import Model.Animals.Cat;
import Model.Animals.WildAnimal;
import Model.Factories.Factory;
import Model.Positions.MapPosition;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.ArrayList;
import java.util.List;

public class Farm {
    public static final int POSSIBLE_NUMBER_OF_FACTORIES = 6;
    long turnsWent;
    Map map;
    String name;
    Integer CurrentMoney;
    Warehouse warehouse;
    Bucket bucket;
    private Factory[] factories = new Factory[POSSIBLE_NUMBER_OF_FACTORIES];
    private Truck truck;
    private Helicopter helicopter=null;

    public static Farm findLoadedFarm(String farmName) {
        for (Farm farm:loadedFarms){
            if (farm.name.equalsIgnoreCase(farmName)){
                return farm;
            }
        }
        return null;
    }


    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    private Farm() {
        map = new Map();
        warehouse = new Warehouse();
        truck  = new Truck(CurrentMoney);
        helicopter = new Helicopter(CurrentMoney);
        bucket = new Bucket(CurrentMoney);




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
            return false;
        }


        for (WildAnimal wildAnimal : wildAnimals) {
            if (wildAnimal.getCage()==null){
                wildAnimal.getCaged();
            }else {
                wildAnimal.getCage().addCompletenesPercentage();
            }

        }
        return true;

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
        for (Factory factory:factories){
            if (factory.getFactoryType().getName().equalsIgnoreCase(string)){
                return factory;
            }
        }
        return null;

    }


    public Map getMap() {
        return map;
    }

    public void buyAnimal(Animal animal) {
        if (animal.getPrice() > this.CurrentMoney) {
            System.out.println("Not Enough Money");
        } else {

            CurrentMoney -= animal.getPrice();
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


    public void UpgradeCats() {
        for (Cell[] cells:getMap().cells) {
            for (Cell cell : cells) {
                for (Item item:cell.getItems()){
                    if (item instanceof Cat){
                        ((Cat) item).upgrade(this.getCurrentMoney());
                    }
                }
            }

        }

    }






    public  static ArrayList<Farm> loadedFarms = new ArrayList<>(0);





}