package Model;

import Model.Animals.Animal;
import Model.Animals.Cat;
import Model.Animals.NonWildAnimal;
import Model.Animals.WildAnimal;
import Model.Factories.Factory;
import Model.Positions.MapPosition;

import java.util.ArrayList;
import java.util.List;

public class Farm {
    public static final int POSSIBLE_NUMBER_OF_FACTORIES = 6;
    public Factory[] factories = new Factory[POSSIBLE_NUMBER_OF_FACTORIES];
    long turnsWent;
    Map map;
    Integer CurrentMoney;
    Warehouse warehouse;
    Bucket bucket;
    Integer CagesLevel = 0;
    private Truck truck;
    private Helicopter helicopter = null;

    public Farm() {
        CurrentMoney = 10000;
        map = new Map();
        warehouse = new Warehouse(0, new ArrayList<>(0));
        truck = new Truck(0, 0, this, 0, new ArrayList<>(0), this.CurrentMoney);
        helicopter = new Helicopter(0, 0, this, 0, new ArrayList<>(0), this.CurrentMoney);
        bucket = new Bucket(CurrentMoney);


    }

    public Farm(long turnsWent, Map map, Integer currentMoney, Warehouse warehouse, Bucket bucket, Integer cagesLevel, Factory[] factories, Truck truck, Helicopter helicopter) {
        this.turnsWent = turnsWent;
        this.map = map;
        CurrentMoney = currentMoney;
        this.warehouse = warehouse;

        this.bucket = bucket;
        bucket.farmMoney = CurrentMoney;
        CagesLevel = cagesLevel;
        this.factories = factories;
        this.truck = truck;
        truck.farm = this;
        truck.FarmMoney = this.CurrentMoney;
        this.helicopter = helicopter;
        helicopter.farm = this;
        helicopter.FarmMoney = this.getCurrentMoney();
    }

    public Integer getCagesLevel() {
        return CagesLevel;
    }

    public void setCagesLevel(Integer cagesLevel) {
        CagesLevel = cagesLevel;
    }

    public long getTurnsWent() {
        return turnsWent;
    }

    public void setTurnsWent(long turnsWent) {
        this.turnsWent = turnsWent;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
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
            if (wildAnimal.getCage() == null) {
                wildAnimal.getCaged();
            } else {
                wildAnimal.getCage().addCompletenesPercentage();
            }

        }
        return true;

    }

    public Bucket getBucket() {
        return this.bucket;
    }

    public void turn() {
        for (Factory factory : factories) {
            if (factory != null) {
                factory.turn();
            }
        }
        map.turn();
        truck.turn();
        helicopter.turn();
        turnsWent += 1;


    }

    public Factory findFactory(String string) {
        for (Factory factory : factories) {
            if (factory.getFactoryType().getName().equalsIgnoreCase(string)) {
                return factory;
            }
        }
        return null;

    }

    public Map getMap() {
        return map;
    }

    public void buyAnimal(Animal animal) {

        if (animal.getBuyCost() > this.CurrentMoney) {
            System.out.println("Not Enough Money");
        } else {

            CurrentMoney -= animal.getBuyCost();
            getMap().addAnimal(animal);

        }

    }

    public void plant(MapPosition mapPosition) {
        Cell cell = getMap().getCellByPosition(mapPosition);
        if (bucket.hasEnoughWater()) {
            if (cell.getGrass().getNum() < 100) {
                cell.getGrass().setNum(100);
                bucket.consume();
            } else {
                System.out.println("The cell is already full of grass");
            }

        } else {
            System.out.println("Bucket Doesn't Have Enough Water");
        }


    }

    public void printWorkshops() {
        for (int i = 0; i < 6; i++) {
            if (factories[i] != null) {
                factories[i].print();
            }
        }


    }

    public void UpgradeCats() {
        for (Cell[] cells : getMap().cells) {
            for (Cell cell : cells) {
                for (Item item : cell.getItems()) {
                    if (item instanceof Cat) {
                        ((Cat) item).upgrade(this);
                    }
                }
            }

        }

    }

    public int findNumberOfItemX(Item.ItemInfo itemInfo) {
        int res = 0;
        if (itemInfo instanceof WildAnimal.WildAnimalInfo) {
            for (Item item : warehouse.getItems()) {
                if (item.getItemInfo().equals(itemInfo)) {
                    res += 1;
                }
            }
            return res;
        }

        if (itemInfo instanceof NonWildAnimal.NonWildAnimalInfo) {
            for (Cell[] cells : map.cells) {
                for (Cell cell : cells) {
                    for (Item item : cell.getItems()) {
                        if (item.itemInfo.equals(itemInfo)) {
                            res += 1;
                        }
                    }
                }
            }
            return res;
        }
        if (itemInfo instanceof NonAnimalItem.NonAnimalItemInfo) {
            for (Item item : warehouse.getItems()) {
                if (item.getItemInfo().equals(itemInfo)) {
                    res += 1;
                }
            }
            for (Cell[] cells : map.cells) {
                for (Cell cell : cells) {
                    for (Item item : cell.getItems()) {
                        if (item.itemInfo.equals(itemInfo)) {
                            res += 1;
                        }
                    }
                }
            }
            return res;
        }
        return 0;

    }

    public void pay(int pay) {
        CurrentMoney = CurrentMoney - pay;
    }

    public void show() {
        truck.show();
        helicopter.show();
        warehouse.show();

        for (int i=0;i<6;i++){
            if (factories[i]!=null){
                factories[i].show();
            }
        }
    }
}