package Model.Animals;

import Model.Cell;
import Model.Item;
import Model.Map;
import Model.Positions.MapPosition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class WildAnimal extends Animal {


    public static HashSet<WildAnimalInfo> wildAnimalInfos = new HashSet<>(0);

    //Finished
    public WildAnimal(WildAnimalInfo wildAnimalInfo) {
        itemInfo = wildAnimalInfo;
    }

    //Finished
    public static WildAnimal getInstance(String name) {
        for (WildAnimalInfo wildAnimalInfo :
                wildAnimalInfos) {
            if (wildAnimalInfo.getItemName().equalsIgnoreCase(name)) {
                return new WildAnimal(wildAnimalInfo);
            }

        }
    }


    public boolean kill() {
        Cell cell = map.getCellByPosition(this.getMapPosition());
        List<Item> items = cell.getItems();
        ArrayList<Item> toBeKilledItems = new ArrayList<>(0);
        Dog dog=null;
        for (Item item : items) {
            if (item instanceof Dog) {
                dog = (Dog) item;

            } else {
                toBeKilledItems.add(item);
            }
        }
        if (dog != null) {
            cell.removeItem(dog);
            cell.removeItem(this);


        } else {
            for (Item item : toBeKilledItems) {
                cell.removeItem(item);
                this.addFullness();
            }
        }


    }


    @Override
    public boolean upgrade(Integer CurrentMoney) {
        return false;
    }

    @Override
    public int getUpgradeCost() {
        return 0;
    }

    public void getCaged(Cage cage) {
    }


    public static class WildAnimalInfo extends AnimalInfo {


        public WildAnimalInfo(String name, int Volume, int price) {
            super(name, Volume, price);
        }
    }


    @Override
    public boolean move() {
        Cell cell;
        if (fullness<30&&(cell = map.findNearestCellWithFoodForWildAnimal(map.getCellByPosition(this.getMapPosition())))!=null){
            moveToPosition(cell.getMapPosition());

        }else {

            Random random = new Random();
            int x = random.nextInt();
            int y = random.nextInt();
            x = x % 3 - 1;
            y = y % 3 - 1;
            x += getMapPosition().getX();
            y += getMapPosition().getY();
            if (x >= Map.Num_Of_CELLS_IN_ROW) {
                x = Map.Num_Of_CELLS_IN_ROW - 1;
            }
            if (y >= Map.Num_Of_CELLS_IN_COLOUM) {
                y =Map.Num_Of_CELLS_IN_COLOUM;
            }
            MapPosition mapPosition = new MapPosition(x,y);
            moveToPosition(mapPosition);

        }
    }

    @Override
    public void turn() {
        this.move();
        this.eat();


    }


    @Override
    public boolean eat() {
        //this shouldn't do anything
        this.kill();

    }
}
