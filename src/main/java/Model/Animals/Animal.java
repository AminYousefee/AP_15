package Model.Animals;

import Model.Cell;
import Model.Item;
import Model.Map;
import Model.Positions.MapPosition;
import Model.Positions.Position;
import Model.Upgradable;

import java.util.Random;

public abstract class Animal extends Item implements Upgradable {
    MapPosition goalPosition;


    public static final int CAT_VOLUME;
    public static final AnimalInfo Cat_Info = new AnimalInfo("cat", CAT_VOLUME, price);
    public static final int DOG_VOLUME;
    public static final AnimalInfo Dog_Info = new AnimalInfo("Dog", DOG_VOLUME, price);
    int fullness;
    int Level;

    //Finished
    public Animal(AnimalInfo animalInfo) {
        itemInfo = animalInfo;
    }

    public static AnimalInfo findAnimalType(String name) {
        if (name.equalsIgnoreCase("cat")) {
            return Cat_Info;
        } else if (name.equalsIgnoreCase("dog")) {
            return Dog_Info;
        } else {
            return ProductiveAnimal.findAnimalType(name);
        }


    }

    //Finished
    public static Animal getInstance(String name) {
        Animal res = WildAnimal.getInstance(name);
        if (res != null) {
            return res;
        }
        res = NonWildAnimal.getInstance(name);
        if (res != null) {
            return res;
        }
    }

    public boolean move() {
        Cell cell;
        if (fullness<30&&(cell = map.findNearestCellWithGrass(map.getCellByPosition(this.getMapPosition())))!=null){
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

    int getSpeed() {
        return false;

    }

    public boolean eat() {
        Cell cell = map.getCellByPosition(getMapPosition());
        if (cell.getGrass().getNum()>0&&fullness<70){
             cell.getGrass().getEaten();
            this.addFullness();
            return true;
        }else {
            return false;
        }
    }

    protected abstract void addFullness();

    public static class AnimalInfo extends ItemInfo {
        public AnimalInfo(String name, int Volume, int price) {
            super(name, Volume, price);
        }
    }

    @Override
    public void turn() {
        if (eat()){

        }else {
            move();
        }
    }

    public void moveToPosition(Position position) {
        Cell goalCell = map.getCell(position);
        Cell CurrentCell = map.getCell(this.getMapPosition());
        CurrentCell.getItems().remove(this);
        goalCell.getItems().add(this);

    }

}
