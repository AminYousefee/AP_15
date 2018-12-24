package Model.Animals;

import Model.Item;
import Model.Map;
import Model.NonAnimalItem;
import Model.Positions.MapPosition;
import Model.Positions.NonMapPosition;
import controller.InputProcessor;
import controller.Print;

public class Cat extends NonWildAnimal {


    Item goalItem;


    public Cat(Map map) {
        super(Animal.Cat_Info, InputProcessor.game.getFarm().getMap());
        this.map = map;
    }

    @Override
    public boolean move() {
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
            return super.move();
        } else {

            MapPosition goalItemPosition = (MapPosition) goalItem.getPosition();
            MapPosition CurrentPosition = (MapPosition) this.getPosition();
            if (goalItemPosition.equals(CurrentPosition)) {
                this.collect(goalItem);
            } else {
                double deltaX = goalItemPosition.getX() - ((MapPosition) this.getPosition()).getX();
                double deltaY = goalItemPosition.getY() - ((MapPosition) this.getPosition()).getY();
                if (((deltaX * deltaX) + (deltaY * deltaY)) < (this.getSpeed() * this.getSpeed())) {
                    moveToPosition(goalItemPosition);

                } else {
                    double amplifier = Math.sqrt((this.getSpeed() * this.getSpeed()) / ((deltaX * deltaX) + (deltaY * deltaY)));
                    int x = (int) (amplifier * deltaX + ((MapPosition) this.getPosition()).getX());
                    int y = (int) (amplifier * deltaY + ((MapPosition) this.getPosition()).getY());
                    MapPosition position = new MapPosition(x, y);
                    moveToPosition(position);
                }
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
        ((NonAnimalItem) item).getCollected();
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

}
