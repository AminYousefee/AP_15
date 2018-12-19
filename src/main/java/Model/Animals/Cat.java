package Model.Animals;

import Model.Item;
import Model.Map;
import Model.NonAnimalItem;
import Model.Positions.MapPosition;
import Model.Positions.Position;

public class Cat extends NonWildAnimal {


    Item goalItem;



    public Cat(Map map){
        this.map =map;
    }


    @Override
    public boolean move() {
        if (goalItem!=null){

        }else if (goalItem==null){
            goalItem=map.getCatCollectableItem();
        }

        if (goalItem==null){
            super.move();
        }else {
            MapPosition goalItemPosition = goalItem.getMapPosition();
            MapPosition CurrentPosition = this.getMapPosition();
            if (goalItemPosition.equals(CurrentPosition)){
                this.collect(goalItem);
            }else {
                double deltaX = goalItemPosition.getX()-this.getMapPosition().getX();
                double deltaY = goalItemPosition.getY()-this.getMapPosition().getY();
                if (((deltaX * deltaX) + (deltaY * deltaY)) < (this.getSpeed() * this.getSpeed())){
                    moveToPosition(goalItemPosition);

                }else {
                    double amplifier= Math.sqrt((this.getSpeed() * this.getSpeed()) / ((deltaX * deltaX) + (deltaY * deltaY)));
                    int x = (int) (amplifier*deltaX + getMapPosition().getX());
                    int y = (int) (amplifier*deltaY + getMapPosition().getY());
                    Position position=new MapPosition(x,y);
                    moveToPosition(position);
                }
            }
        }


    }


    @Override
    public int getUpgradeCost() {
        //todo
    }



    private boolean collect(Item item){
        ((NonAnimalItem)item).getCollected();
        map.getCell(item.getMapPosition()).removeItem(item);
        

    }
}
