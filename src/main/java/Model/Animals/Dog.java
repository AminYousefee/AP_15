package Model.Animals;

import Model.Cell;
import Model.Item;
import Model.Map;
import Model.Positions.MapPosition;
import Model.Positions.NonMapPosition;
import controller.InputProcessor;

import java.util.Iterator;
import java.util.ListIterator;

public class Dog extends NonWildAnimal {



    public Dog( Map map) {
        super(Animal.Dog_Info, InputProcessor.game.getFarm().getMap());
    }

    @Override
    public boolean move(ListIterator<Item> itemIterator) {
        if (getPosition() instanceof NonMapPosition){
            return false;
        }
        Cell cell = map.getNearestCellWithWildAnimal(map.getCellByPosition((MapPosition) getPosition()));
        if (cell!=null){
            MapPosition goalItemPosition = cell.getMapPosition();
            MapPosition CurrentPosition = (MapPosition) this.getPosition();
            if (goalItemPosition.equals(CurrentPosition)){
                return false;
                //nothing here
            }else {
                double deltaX = goalItemPosition.getX()-((MapPosition)this.getPosition()).getX();
                double deltaY = goalItemPosition.getY()-((MapPosition)this.getPosition()).getY();
                /*if (((deltaX * deltaX) + (deltaY * deltaY)) < (this.getSpeed() * this.getSpeed())){
                    moveToPosition(goalItemPosition,itemIterator);

                }else {
                    double amplifier= Math.sqrt((this.getSpeed() * this.getSpeed()) / ((deltaX * deltaX) + (deltaY * deltaY)));
                    int x = (int) (amplifier*deltaX + ((MapPosition)this.getPosition()).getX());
                    int y = (int) (amplifier*deltaY + ((MapPosition)this.getPosition()).getX());
                    MapPosition position=new MapPosition(x,y);
                    moveToPosition(position,itemIterator);
                }*/
                deltaX = Math.signum(deltaX);
                deltaY = Math.signum(deltaY);
                MapPosition p = new MapPosition((int)deltaX,(int)deltaY);
                moveToPosition(p,itemIterator);
                return true;
            }
        }else {
            return super.move(itemIterator);
        }



    }



    @Override
    public boolean turner(ListIterator<Item> itemIterator) {
        super.turn(itemIterator);
        return this.move(itemIterator);

    }

    public void defend(){


    }

    public static class DogInfo extends NonWildAnimalInfo{
        public static final DogInfo ourInstance = new DogInfo();

        public static DogInfo getInstance() {
            return ourInstance;
        }
        public DogInfo() {
            super("Dog", -1, -1, -1, 65);
        }
    }



}
