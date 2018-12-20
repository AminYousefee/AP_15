package Model.Animals;

import Model.Cell;
import Model.Map;
import Model.Positions.MapPosition;
import Model.Positions.Position;

public class Dog extends NonWildAnimal {



    public Dog( Map map) {
        super(Animal.Dog_Info);
    }

    @Override
    public boolean move() {
        Cell cell = map.getNearestCellWithWildAnimal(map.getCellByPosition(getMapPosition()));
        if (cell!=null){
            MapPosition goalItemPosition = cell.getMapPosition();
            MapPosition CurrentPosition = this.getMapPosition();
            if (goalItemPosition.equals(CurrentPosition)){
                //nothing here
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
    public boolean eat() {
        return false;
    }


    @Override
    public void turn() {
        this.move();

    }

    public void defend(){


    }
}
