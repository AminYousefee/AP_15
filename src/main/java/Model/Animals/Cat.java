package Model.Animals;

import Model.Cell;
import Model.Item;
import Model.Map;
import Model.Positions.Position;

public class Cat extends NonWildAnimal {

    transient Map map;
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
            Position goalItemPosition = goalItem.getPosition();
            Position CurrentPosition = this.getPosition();
            if (goalItemPosition.equals(CurrentPosition)){
                this.collect(goalItem);
            }else {
                double deltaX = goalItemPosition.getX()-this.getPosition().getX();
                double deltaY = goalItemPosition.getY()-this.getPosition().getY();
                if (((deltaX * deltaX) + (deltaY * deltaY)) < (this.getSpeed() * this.getSpeed())){
                    moveToPosition(goalItemPosition);

                }else {
                    double amplifier= Math.sqrt((this.getSpeed() * this.getSpeed()) / ((deltaX * deltaX) + (deltaY * deltaY)));
                    int x = (int) (amplifier*deltaX +getPosition().getX());
                    int y = (int) (amplifier*deltaY +getPosition().getY());
                    Position position=new Position(x,y);
                    moveToPosition(position);
                }
            }
        }


    }

    private void moveToPosition(Position position) {
        Cell goalCell = map.getCell(position);
        Cell CurrentCell = map.getCell(this.getPosition());
        CurrentCell.getItems().remove(this);
        goalCell.getItems().add(this);

    }

    @Override
    public int getUpgradeCost() {
        //todo
    }



    private boolean collect(Item item){

    }
}
