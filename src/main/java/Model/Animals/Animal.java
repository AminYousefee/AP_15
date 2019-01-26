package Model.Animals;

import Model.Cell;
import Model.GameMenu.Game;
import Model.Item;
import Model.Map;
import Model.Positions.MapPosition;
import Model.Upgradable;
import controller.ImageViewSprite;
import controller.InputProcessor;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public abstract class Animal extends Item implements Upgradable {
    public static final int CAT_VOLUME = 0;
    public static final NonWildAnimal.NonWildAnimalInfo Cat_Info = Cat.CatInfo.getInstance();
    public static final int DOG_VOLUME = 0;
    public static final NonWildAnimal.NonWildAnimalInfo Dog_Info = Dog.DogInfo.getInstance();
    static HashMap<String, ImageViewSprite.Keep> keepHashMap;

    static {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("death.png");
        strings.add("down.png");
        strings.add("eat.png");
        strings.add("left.png");
        strings.add("up.png");
        strings.add("up_left.png");
        strings.add("down_left.png");
        File file = new File("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa");


        Image image = null;
        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Ostrich/death.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ImageViewSprite.Keep keep = new ImageViewSprite.Keep(image, 6, 4, 24, 144, 128, 30, false);
        keepHashMap.put("TurkeyDeath", keep);
        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Ostrich/down.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 6, 4, 24, 144, 128, 30, false);
        keepHashMap.put("TurkeyDown", keep);

        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Ostrich/down_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 6, 4, 24, 144, 128, 30, false);
        keepHashMap.put("TurkeyDown_left", keep);


        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Ostrich/eat.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 4, 6, 24, 144, 128, 30, false);
        keepHashMap.put("TurkeyEat", keep);

        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Ostrich/left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 4, 6, 24, 144, 128, 30, false);
        keepHashMap.put("TurkeyLeft", keep);

        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Ostrich/left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 4, 6, 24, 144, 128, 30, false);
        keepHashMap.put("TurkeyRight", keep);


        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Ostrich/up.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 4, 6, 24, 144, 128, 30, false);
        keepHashMap.put("TurkeyUp", keep);


        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Ostrich/up_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 4, 6, 24, 144, 128, 30, false);
        keepHashMap.put("TurkeyUp_left", keep);


        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Ostrich/up_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 4, 6, 24, 144, 128, 30, true);
        keepHashMap.put("TurkeyDown_right", keep);

        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Ostrich/down_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 6, 4, 24, 144, 128, 30, true);
        keepHashMap.put("TurkeyDown_right", keep);












        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Lion/down.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 5, 5, 24, 144, 128, 30, false);
        keepHashMap.put("LionDown", keep);

        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Lion/down_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 4, 6, 24, 144, 128, 30, false);
        keepHashMap.put("LionDown_left", keep);


        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Lion/caged.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 6, 4, 24, 144, 128, 30, false);
        keepHashMap.put("LionCaged", keep);

        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Lion/left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 3, 8, 24, 144, 128, 30, false);
        keepHashMap.put("LionLeft", keep);

        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Lion/left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 3, 8, 24, 144, 128, 30, false);
        keepHashMap.put("LionRight", keep);


        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Lion/up.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 6, 4, 24, 144, 128, 30, false);
        keepHashMap.put("LionUp", keep);


        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Lion/up_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 6, 4, 24, 144, 128, 30, false);
        keepHashMap.put("LionUp_left", keep);


        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Lion/up_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 6, 4, 24, 144, 128, 30, true);
        keepHashMap.put("LionDown_right", keep);

        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Lion/down_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 4, 6, 24, 144, 128, 30, true);
        keepHashMap.put("LionDown_right", keep);























        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Grizzly/down.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 4, 6, 24, 144, 128, 30, false);
        keepHashMap.put("GrizzlyDown", keep);

        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Grizzly/down_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 4, 6, 24, 144, 128, 30, false);
        keepHashMap.put("GrizzlyDown_left", keep);


        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Grizzly/caged.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 6, 4, 24, 144, 128, 30, false);
        keepHashMap.put("GrizzlyCaged", keep);

        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Grizzly/left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 4, 6, 24, 144, 128, 30, false);
        keepHashMap.put("GrizzlyLeft", keep);

        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Grizzly/left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 4, 6, 24, 144, 128, 30, false);
        keepHashMap.put("GrizzlyRight", keep);


        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Grizzly/up.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 4, 6, 24, 144, 128, 30, false);
        keepHashMap.put("GrizzlyUp", keep);


        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Grizzly/up_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 4, 6, 24, 144, 128, 30, false);
        keepHashMap.put("GrizzlyUp_left", keep);


        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Grizzly/up_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 4, 6, 24, 144, 128, 30, true);
        keepHashMap.put("GrizzlyDown_right", keep);

        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Grizzly/down_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 4, 6, 24, 144, 128, 30, true);
        keepHashMap.put("GrizzlyDown_right", keep);



























        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Dog/down.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 6, 4, 24, 144, 128, 30, false);
        keepHashMap.put("DogDown", keep);

        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Dog/down_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 5, 5, 24, 144, 128, 30, false);
        keepHashMap.put("DogDown_left", keep);




        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Dog/left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 6, 4, 24, 144, 128, 30, false);
        keepHashMap.put("DogLeft", keep);

        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Dog/left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 6, 4, 24, 144, 128, 30, false);
        keepHashMap.put("DogRight", keep);


        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Dog/up.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 6, 4, 24, 144, 128, 30, false);
        keepHashMap.put("DogUp", keep);


        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Dog/up_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 5, 5, 24, 144, 128, 30, false);
        keepHashMap.put("DogUp_left", keep);


        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Dog/up_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 5, 5, 24, 144, 128, 30, true);
        keepHashMap.put("DogDown_right", keep);

        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Dog/down_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 5, 5, 24, 144, 128, 30, true);
        keepHashMap.put("DogDown_right", keep);



















        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Cat/down.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 6, 4, 24, 144, 128, 30, false);
        keepHashMap.put("CatDown", keep);

        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Cat/down_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 6, 4, 24, 144, 128, 30, false);
        keepHashMap.put("CatDown_left", keep);




        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Cat/left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 4, 6, 24, 144, 128, 30, false);
        keepHashMap.put("CatLeft", keep);

        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Cat/left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 4, 6, 24, 144, 128, 30, false);
        keepHashMap.put("CatRight", keep);


        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Cat/up.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 6, 4, 24, 144, 128, 30, false);
        keepHashMap.put("CatUp", keep);


        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Cat/up_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 6, 4, 24, 144, 128, 30, false);
        keepHashMap.put("CatUp_left", keep);


        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Cat/up_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 6, 4, 24, 144, 128, 30, true);
        keepHashMap.put("CatDown_right", keep);

        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Cat/down_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 6, 4, 24, 144, 128, 30, true);
        keepHashMap.put("CatDown_right", keep);
























        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Cow/death.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ImageViewSprite.Keep keep = new ImageViewSprite.Keep(image, 3, 8, 24, 144, 128, 30, false);
        keepHashMap.put("CowDeath", keep);
        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Cow/down.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 3, 8, 24, 144, 128, 30, false);
        keepHashMap.put("CowDown", keep);

        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Cow/down_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 3, 8, 24, 144, 128, 30, false);
        keepHashMap.put("CowDown_left", keep);


        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Cow/eat.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 4, 6, 24, 144, 128, 30, false);
        keepHashMap.put("CowEat", keep);

        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Cow/left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 3, 8, 24, 144, 128, 30, false);
        keepHashMap.put("CowLeft", keep);

        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Cow/left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 3, 8, 24, 144, 128, 30, false);
        keepHashMap.put("CowRight", keep);


        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Cow/up.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 4, 6, 24, 144, 128, 30, false);
        keepHashMap.put("CowUp", keep);


        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Cow/up_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 3, 8, 24, 144, 128, 30, false);
        keepHashMap.put("CowUp_left", keep);


        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Cow/up_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 3, 8, 24, 144, 128, 30, true);
        keepHashMap.put("CowDown_right", keep);

        try {
            image = new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/Cow/down_left.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        keep = new ImageViewSprite.Keep(image, 3, 8, 24, 144, 128, 30, true);
        keepHashMap.put("CowDown_right", keep);













    }

    MapPosition goalPosition;
    int fullness;
    int Level;
    int directive;
    ImageView imageView;

    /*    public static AnimalInfo findAnimalType(String name) {
            if (name.equalsIgnoreCase("cat")) {
                return Cat_Info;
            } else if (name.equalsIgnoreCase("dog")) {
                return Dog_Info;
            } else {
                return ProductiveAnimal.findAnimalType(name);
            }


        }*/
    ImageViewSprite sprite;

    //Finished
    public Animal(AnimalInfo animalInfo, Map map) {
        itemInfo = animalInfo;
        this.map = map;
        //this.show();
    }

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

    //@Override
    public void show(String state) {


        sprite = new ImageViewSprite(imageView, keepHashMap.get(state));
        sprite.start();
        new Thread(new Runnable() {
            Object object = new Object();

            @Override
            public void run() {
                sprite.start();
                synchronized (object) {
                    try {
                        object.wait(InputProcessor.getSpeed());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                sprite.stop();
                imageView.setViewport(new Rectangle2D(0, 0, keepHashMap.get(state).frameWidth, keepHashMap.get(state).frameHeight));

            }
        });


    }

    public abstract boolean turner(ListIterator<Item> listIterator);

    public boolean turn(ListIterator<Item> listIterator) {
        //int t = (int) (getSpeed() / 50.0);
        /*
        for (int i = 0; i < t; i++) {
            this.turner(listIterator);
        }*/

        synchronized (Game.obj) {
            turner(listIterator);
        }
        Object obj = new Object();
        synchronized (obj) {
            try {
                obj.wait(InputProcessor.getSpeed());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        new Thread(() -> turn(listIterator));
        return false;
    }

    //public void show()

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
            final String path = "/home/a/Projects/AP_Project/AP_15/static/Animals/Africa/" + str + "/eat.png";
            try {
                images[0] = new Image(new FileInputStream(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //this.show();
        }
    }


}
