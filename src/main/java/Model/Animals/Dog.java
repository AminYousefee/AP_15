package Model.Animals;

import Model.Cell;
import Model.Map;
import Model.Positions.MapPosition;
import Model.Positions.NonMapPosition;

public class Dog extends NonWildAnimal {



    public Dog( Map map) {
        super(Animal.Dog_Info);
    }

    @Override
    public boolean move() {
        if (getPosition() instanceof NonMapPosition){
            return false;
        }
        Cell cell = map.getNearestCellWithWildAnimal(map.getCellByPosition((MapPosition) getPosition()));
        if (cell!=null){
            MapPosition goalItemPosition = cell.getMapPosition();
            MapPosition CurrentPosition = (MapPosition) this.getPosition();
            if (goalItemPosition.equals(CurrentPosition)){
                //nothing here
            }else {
                double deltaX = goalItemPosition.getX()-((MapPosition)this.getPosition()).getX();
                double deltaY = goalItemPosition.getY()-((MapPosition)this.getPosition()).getY();
                if (((deltaX * deltaX) + (deltaY * deltaY)) < (this.getSpeed() * this.getSpeed())){
                    moveToPosition(goalItemPosition);

                }else {
                    double amplifier= Math.sqrt((this.getSpeed() * this.getSpeed()) / ((deltaX * deltaX) + (deltaY * deltaY)));
                    int x = (int) (amplifier*deltaX + ((MapPosition)this.getPosition()).getX());
                    int y = (int) (amplifier*deltaY + ((MapPosition)this.getPosition()).getX());
                    MapPosition position=new MapPosition(x,y);
                    moveToPosition(position);
                }
            }
        }
        return true;


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
