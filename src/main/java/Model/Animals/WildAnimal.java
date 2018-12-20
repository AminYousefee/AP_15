package Model.Animals;

import Model.Cell;
import Model.Item;
import Model.Map;
import Model.Positions.MapPosition;
import Model.Positions.NonMapPosition;

import java.security.acl.NotOwnerException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class WildAnimal extends Animal {
    private Cage cage;

    public Cage getCage() {
        return cage;
    }

    public void setCage(Cage cage) {
        this.cage = cage;
    }

    public static HashSet<WildAnimalInfo> wildAnimalInfos = new HashSet<>(0);

    //Finished
    public WildAnimal(WildAnimalInfo wildAnimalInfo) {
        super(wildAnimalInfo);
    }

    //Finished
    public static WildAnimal getInstance(String name) {
        for (WildAnimalInfo wildAnimalInfo :
                wildAnimalInfos) {
            if (wildAnimalInfo.getItemName().equalsIgnoreCase(name)) {
                return new WildAnimal(wildAnimalInfo);
            }

        }
        return null;
    }


    public boolean kill() {
        Cell cell = map.getCellByPosition((MapPosition) this.getPosition());
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
            return true;


        } else {
            if (toBeKilledItems.size()>0) {
                for (Item item : toBeKilledItems) {
                    cell.removeItem(item);
                    this.addFullness();
                }
                return true;
            }else {
                return false;
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

    public void getCaged() {
        setCage(new Cage(((MapPosition)getPosition()).getX(), ((MapPosition)getPosition()).getY()));
    }


    public static class WildAnimalInfo extends AnimalInfo {


        public WildAnimalInfo(String name, int Volume, int price) {
            super(name, Volume, price);
        }
    }


    @Override
    public boolean move() {
        if (getPosition() instanceof NonMapPosition){
            return false;
        }
        Cell cell;
        if (fullness<30&&(cell = map.findNearestCellWithFoodForWildAnimal(map.getCellByPosition((MapPosition) this.getPosition())))!=null){
            if (cell!=null) {
                MapPosition goalItemPosition = cell.getMapPosition();
                MapPosition CurrentPosition = (MapPosition) this.getPosition();
                if (goalItemPosition.equals(CurrentPosition)) {
                    //nothing here
                } else {
                    double deltaX = goalItemPosition.getX() - ((MapPosition)this.getPosition()).getX();
                    double deltaY = goalItemPosition.getY() - ((MapPosition)this.getPosition()).getY();
                    if (((deltaX * deltaX) + (deltaY * deltaY)) < (this.getSpeed() * this.getSpeed())) {
                        moveToPosition(goalItemPosition);

                    } else {
                        double amplifier = Math.sqrt((this.getSpeed() * this.getSpeed()) / ((deltaX * deltaX) + (deltaY * deltaY)));
                        int x = (int) (amplifier * deltaX + ((MapPosition)this.getPosition()).getX());
                        int y = (int) (amplifier * deltaY + ((MapPosition)this.getPosition()).getY());
                        MapPosition position = new MapPosition(x, y);
                        moveToPosition(position);
                    }
                }


            }









                return true;

        }else {

            Random random = new Random();
            int x = random.nextInt();
            int y = random.nextInt();
            x = x % 3 - 1;
            y = y % 3 - 1;
            x += ((MapPosition)this.getPosition()).getX();
            y += ((MapPosition)this.getPosition()).getX();
            if (x >= Map.Num_Of_CELLS_IN_ROW) {
                x = Map.Num_Of_CELLS_IN_ROW - 1;
            }
            if (y >= Map.Num_Of_CELLS_IN_COLOUM) {
                y =Map.Num_Of_CELLS_IN_COLOUM;
            }
            MapPosition mapPosition = new MapPosition(x,y);
            moveToPosition(mapPosition);
            return true;

        }
    }

    @Override
    public void turn() {
        cage.turn();
        if (this.getCage().getCompletnessPercetage()==100){

        }else {
            this.move();
            this.eat();
        }


    }


    @Override
    public boolean eat() {
        //this shouldn't do anything
        return this.kill();

    }

    @Override
    protected void addFullness() {
        fullness+=20;
    }
}
