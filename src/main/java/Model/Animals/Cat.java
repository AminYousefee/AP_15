package Model.Animals;

import Model.Item;
import Model.Map;
import Model.NonAnimalItem;
import Model.Positions.MapPosition;
import Model.Positions.NonMapPosition;
import controller.InputProcessor;
import controller.Main;
import controller.Print;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;

public class Cat extends NonWildAnimal {


    Item goalItem;


    public Cat(Map map) {
        super(Animal.Cat_Info, InputProcessor.game.getFarm().getMap());
        //this.map = map;
    }

    @Override
    public boolean move(ListIterator<Item> itemIterator) {
        if (getPosition() instanceof NonMapPosition) {
            return false;
        }
        if (goalItem != null) {

        } else if (goalItem == null) {
            if (this.Level == 1) {
                goalItem = map.getCatCollectableItem();

            } else {
                goalItem = map.getNearestCatCollectableItem(this.map.getCellByPosition((MapPosition) this.getPosition()));
            }
        }

        if (goalItem.getPosition() instanceof NonMapPosition){
            if (this.Level == 1) {
                goalItem = map.getCatCollectableItem();

            } else {
                goalItem = map.getNearestCatCollectableItem(this.map.getCellByPosition((MapPosition) this.getPosition()));
            }
        }else if (! map.getCellByPosition((MapPosition) goalItem.getPosition()).getItems().contains(goalItem)){
            if (this.Level == 1) {
                goalItem = map.getCatCollectableItem();

            } else {
                goalItem = map.getNearestCatCollectableItem(this.map.getCellByPosition((MapPosition) this.getPosition()));
            }
        }

        if (goalItem == null) {
            Random random = new Random();
            int x = Math.abs(random.nextInt());
            int y = Math.abs(random.nextInt());
            x = x % Map.Num_Of_CELLS_IN_ROW - 1;
            y = y % Map.Num_Of_CELLS_IN_COLOUM - 1;
            x += ((MapPosition)getPosition()).getX();
            y += ((MapPosition)getPosition()).getY();
            if (x >= Map.Num_Of_CELLS_IN_ROW) {
                x = Map.Num_Of_CELLS_IN_ROW - 1;
            }else if (x<0){
                x = 0;
            }
            if (y >= Map.Num_Of_CELLS_IN_COLOUM) {
                y =Map.Num_Of_CELLS_IN_COLOUM-1;
            }else if (y< 0){
                y= 0;
            }
            MapPosition mapPosition = new MapPosition(x,y);
            return moveToPosition(mapPosition,itemIterator);
        } else {

            MapPosition goalItemPosition = (MapPosition) goalItem.getPosition();
            MapPosition CurrentPosition = (MapPosition) this.getPosition();
            if (goalItemPosition.equals(CurrentPosition)) {
                this.collect(goalItem);
            } else {
                double deltaX = goalItemPosition.getX() - ((MapPosition) this.getPosition()).getX();
                double deltaY = goalItemPosition.getY() - ((MapPosition) this.getPosition()).getY();
                /*if (((deltaX * deltaX) + (deltaY * deltaY)) < (this.getSpeed() * this.getSpeed())) {
                    moveToPosition(goalItemPosition,itemIterator);
                } else {
                    double amplifier = Math.sqrt((this.getSpeed() * this.getSpeed()) / ((deltaX * deltaX) + (deltaY * deltaY)));
                    int x = (int) (amplifier * deltaX + ((MapPosition) this.getPosition()).getX());
                    int y = (int) (amplifier * deltaY + ((MapPosition) this.getPosition()).getY());
                    MapPosition position = new MapPosition(x, y);
                    moveToPosition(position,itemIterator);
                }*/
                deltaX = Math.signum(deltaX);
                deltaY = Math.signum(deltaY);
                MapPosition p = new MapPosition(((int)deltaX),((int)deltaY));
                moveToPosition(p,itemIterator);
            }
            return true;
        }


    }


    @Override
    public int getUpgradeCost() {
        return 0;
        //todo
    }


    private boolean collect(Item item) {
        //((NonAnimalItem) item).getCollected(Main.gridPane);
        map.getCell((MapPosition) item.getPosition()).removeItem(item);

        return true;
    }


    public static class CatInfo extends NonWildAnimalInfo{
        private static CatInfo ourInstance = new CatInfo();
        int sleepTime =10;
        int MaxLevelUpgradeCost=  500;

        public static CatInfo getInstance() {
            return ourInstance;
        }
        public CatInfo() {
            super("Cat", -1, 2500, -1, 38);
        }
    }

    @Override
    public boolean turner(ListIterator<Item> itemIterator) {
        super.turn(itemIterator);
        return this.move(itemIterator);
    }
}
