package Model.Animals;

import Model.Cell;
import Model.Item;
import Model.Map;
import Model.Positions.MapPosition;
import Model.Upgradable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;

public abstract class Animal extends Item implements Upgradable {

    public static final int CAT_VOLUME = 0;
    public static final NonWildAnimal.NonWildAnimalInfo Cat_Info = Cat.CatInfo.getInstance();
    public static final int DOG_VOLUME = 0;
    public static final NonWildAnimal.NonWildAnimalInfo Dog_Info = Dog.DogInfo.getInstance();
    MapPosition goalPosition;
    int fullness;
    int Level;
    int directive;
    ImageView imageView;

    //Finished
    public Animal(AnimalInfo animalInfo, Map map) {
        itemInfo = animalInfo;
        this.map = map;
        this.show();
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

    public boolean move(ListIterator<Item> itemIterator) {
        Random random = new Random();
        int x = Math.abs(random.nextInt());
        int y = Math.abs(random.nextInt());
        x = x % /*Map.Num_Of_CELLS_IN_COLOUM*/3;
        y = y % /*Map.Num_Of_CELLS_IN_ROW*/3;
        x += ((MapPosition) getPosition()).getX();
        y += ((MapPosition) getPosition()).getY();

        MapPosition goalPosition = new MapPosition(x, y);


        MapPosition CurrentPosition = (MapPosition) this.getPosition();
        if (goalPosition.equals(CurrentPosition)) {
            return false;
        } else {
            double deltaX = goalPosition.getX() - ((MapPosition) this.getPosition()).getX();
            double deltaY = goalPosition.getY() - ((MapPosition) this.getPosition()).getY();
            if (((deltaX * deltaX) + (deltaY * deltaY)) < (this.getSpeed() * this.getSpeed())) {
                return moveToPosition(goalPosition, itemIterator);
            } else {
                double amplifier = Math.sqrt((this.getSpeed() * this.getSpeed()) / ((deltaX * deltaX) + (deltaY * deltaY)));
                x = (int) (amplifier * deltaX + ((MapPosition) this.getPosition()).getX());
                y = (int) (amplifier * deltaY + ((MapPosition) this.getPosition()).getY());
                MapPosition position = new MapPosition(x, y);
                return moveToPosition(position, itemIterator);
            }
        }


















   /*     x += ((MapPosition) getPosition()).getX();
        y += ((MapPosition) getPosition()).getY();
        if (x >= Map.Num_Of_CELLS_IN_ROW) {
            x = Map.Num_Of_CELLS_IN_ROW - 1;
        } else if (x < 0) {
            x = 0;
        }
        if (y >= Map.Num_Of_CELLS_IN_COLOUM) {
            y = Map.Num_Of_CELLS_IN_COLOUM - 1;
        } else if (y < 0) {
            y = 0;
        }
        MapPosition mapPosition = new MapPosition(x, y);
        return moveToPosition(mapPosition, itemIterator);*/
    }

    public int getSpeed() {
        return 5;

    }

    public boolean moveToPosition(MapPosition position, Iterator<Item> itemIterator) {
        Cell goalCell = map.getCell(position);
        Cell CurrentCell = map.getCell((MapPosition) this.getPosition());

        //maybe there we'll be a better way of organizing it
        if (goalCell == CurrentCell) {
            return false;
        }
        itemIterator.remove();
        //Main.gridPane.getChildren().remove(this.imageView);
        int tempX = goalCell.getMapPosition().getX() - CurrentCell.getMapPosition().getX();
        int tempY = goalCell.getMapPosition().getY() - CurrentCell.getMapPosition().getY();
        if (Math.abs(tempX) > Math.abs(tempY)) {
            if (tempX > 0) {
                directive = 0;
            } else {
                directive = 1;
            }
        } else {
            if (tempY > 0) {
                directive = 2;
            } else {
                directive = 3;
            }

        }

        //Main.gridPane.add(imageView,goalCell.getMapPosition().getX(),goalCell.getMapPosition().getY());
        goalCell.getItems().add(this);
        setPosition(position);

        return true;
    }

    @Override
    public void show() {

    }

    public abstract boolean turner(ListIterator<Item> listIterator);

    public boolean turn(ListIterator<Item> listIterator) {
        int t = (int) (getSpeed() / 50.0);
        for (int i = 0; i < t; i++) {
            this.turner(listIterator);
        }
        return false;
    }

    public static class AnimalInfo extends ItemInfo {
        int Speed;
        Image[] images;


        public AnimalInfo(String itemName, int depotSize, int buyCost, int SaleCost, int speed) {
            super(itemName, depotSize, buyCost, SaleCost);
            Speed = speed;
            /*for (int i = 0; i < 4; i++) {
                try {
                    images[i] = new Image(new FileInputStream(itemName + i + ".png"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }*/
            String str = itemName;
            if (str.equalsIgnoreCase("turkey")) {
                str = "Ostrich";
            }
            final String path = "/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/" + str +"/eat.png";
            try {
                images[0] = new Image(new FileInputStream(path));
            }catch (IOException e){
                e.printStackTrace();
            }
            //this.show();
        }
    }

    //public void show()


}
