package Model.Animals;

import Model.*;
import Model.Positions.MapPosition;
import Model.Positions.NonMapPosition;
import controller.InputProcessor;
import org.junit.jupiter.api.Test;

import javax.print.attribute.standard.MediaPrintableArea;
import java.util.Random;

public abstract class Animal extends Item implements Upgradable {

    MapPosition goalPosition;


    public static final int CAT_VOLUME=10;
    public static final NonWildAnimal.NonWildAnimalInfo Cat_Info = new NonWildAnimal.NonWildAnimalInfo("cat", CAT_VOLUME, 30);
    public static final int DOG_VOLUME=10;
    public static final NonWildAnimal.NonWildAnimalInfo Dog_Info = new NonWildAnimal.NonWildAnimalInfo("Dog", DOG_VOLUME, 30);
    int fullness;
    int Level;

    //Finished
    public Animal(AnimalInfo animalInfo,Map map) {
        itemInfo = animalInfo;
        this.map =map;
    }

/*    public static AnimalInfo findAnimalType(String name) {
        if (name.equalsIgnoreCase("cat")) {
            return Cat_Info;
        } else if (name.equalsIgnoreCase("dog")) {
            return Dog_Info;
        } else {
            return ProductiveAnimal.findAnimalType(name);
        }


    }*/

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
        return null;
    }

    public boolean move() {
        if (getPosition() instanceof NonMapPosition){
            return false;
            //it doesn't move in this way
        }
        Cell cell;
        if (fullness<30&&(cell = map.findNearestCellWithGrass(map.getCellByPosition((MapPosition) this.getPosition())))!=null){



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
                        int x = (int) (amplifier * deltaX + ((MapPosition)getPosition()).getX());
                        int y = (int) (amplifier * deltaY + ((MapPosition)getPosition()).getY());
                        MapPosition position = new MapPosition(x, y);
                        moveToPosition(position);
                    }
                }

            }
        }else {

            Random random = new Random();
            int x = random.nextInt();
            int y = random.nextInt();
            x = x % 3 - 1;
            y = y % 3 - 1;
            x += ((MapPosition)getPosition()).getX();
            y += ((MapPosition)getPosition()).getY();
            if (x >= Map.Num_Of_CELLS_IN_ROW) {
                x = Map.Num_Of_CELLS_IN_ROW - 1;
            }
            if (y >= Map.Num_Of_CELLS_IN_COLOUM) {
                y =Map.Num_Of_CELLS_IN_COLOUM;
            }
            MapPosition mapPosition = new MapPosition(x,y);
            moveToPosition(mapPosition);

        }
        return true;

    }

    public int getSpeed() {
        return 5;

    }

    public boolean eat() {
        if (getPosition() instanceof NonMapPosition){
            return false;
            //do nothing in this case
        }
        Cell cell = map.getCellByPosition((MapPosition) getPosition());
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
        if (this instanceof ProductiveAnimal){
            ((ProductiveAnimal) this).produce();
        }
    }

    public void moveToPosition(MapPosition position) {
        Cell goalCell = map.getCell(position);
        Cell CurrentCell = map.getCell((MapPosition) this.getPosition());
        CurrentCell.getItems().remove(this);
        goalCell.getItems().add(this);

    }





}
