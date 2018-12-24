package Model.Animals;

import Model.*;
import Model.Positions.MapPosition;
import Model.Positions.NonMapPosition;

import java.util.Random;

public abstract class Animal extends Item implements Upgradable {

    MapPosition goalPosition;


    public static final int CAT_VOLUME=0;
    public static final NonWildAnimal.NonWildAnimalInfo Cat_Info = Cat.CatInfo.getInstance();
    public static final int DOG_VOLUME=0;
    public static final NonWildAnimal.NonWildAnimalInfo Dog_Info = Dog.DogInfo.getInstance();
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
            int x = Math.abs(random.nextInt());
            int y = Math.abs(random.nextInt());
            x = x % 3 - 1;
            y = y % 3 - 1;
            x += ((MapPosition)getPosition()).getX();
            y += ((MapPosition)getPosition()).getY();
            if (x >= Map.Num_Of_CELLS_IN_ROW) {
                x = Map.Num_Of_CELLS_IN_ROW - 1;
            }else if (x<0){
                x = 0;
            }
            if (y >= Map.Num_Of_CELLS_IN_COLOUM) {
                y =Map.Num_Of_CELLS_IN_COLOUM;
            }else if (y< 0){
                y= 0;
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
        int Speed;


        public AnimalInfo(String itemName, int depotSize, int buyCost, int SaleCost, int speed) {
            super(itemName, depotSize, buyCost, SaleCost);
            Speed = speed;
        }
    }

    @Override
    public boolean turn() {
        if (this instanceof ProductiveAnimal){
            ((ProductiveAnimal) this).produce();
        }

        if (eat()){

        }else {
            return move();
        }
        return false;

    }

    public void moveToPosition(MapPosition position) {
        Cell goalCell = map.getCell(position);
        Cell CurrentCell = map.getCell((MapPosition) this.getPosition());
        CurrentCell.getItems().remove(this);
        goalCell.getItems().add(this);

    }





}
