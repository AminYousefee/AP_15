package Model.Animals;

import Model.Item;
import Model.Positions.Position;
import Model.Upgradable;

import java.util.Random;

public abstract class Animal extends Item implements Upgradable {

    public static final AnimalInfo Cat_Info;
    public static final AnimalInfo Dog_Info;

    static {
        Cat_Info = new AnimalInfo();
        //todo make these better
        Dog_Info = new AnimalInfo();
    }

    int fullness;
    int level;
    private int lastDirect;
    private int howManyMove;

    public static AnimalInfo findAnimalType(String name) {
        if (name.equalsIgnoreCase("cat")) {
            return Cat_Info;
        } else if (name.equalsIgnoreCase("dog")) {
            return Dog_Info;
        } else {
            return ProductiveAnimal.findAnimalType(name);
        }


    }

    public void move(){
        int speed = this.getSpeed();
        Position position = this.getPosition();
        Random random = new Random();
        int direct;
        int moveRand;
        try {
            if (howManyMove == 0) {
                direct = random.nextInt(8);
                moveRand = random.nextInt(3);
            } else {
                direct = this.lastDirect;
                moveRand = this.howManyMove;
            }

            switch (direct) {
                case 0:
                    position.setX(position.getX() + speed);
                    this.lastDirect = 0;
                    moveRand--;
                    this.howManyMove = moveRand;

                case 1:
                    position.setX(position.getX() - speed);
                    this.lastDirect = 1;
                    moveRand--;
                    this.howManyMove = moveRand;
                    break;
                case 2:
                    position.setY(position.getY() + speed);
                    this.lastDirect = 2;
                    moveRand--;
                    this.howManyMove = moveRand;
                    break;
                case 3:
                    position.setY(position.getY() - speed);
                    this.lastDirect = 3;
                    moveRand--;
                    this.howManyMove = moveRand;
                    break;
                case 4:
                    position.setX(position.getX() + speed);
                    position.setY(position.getY() + speed);
                    this.howManyMove = 0;
                    break;
                case 5:
                    position.setX(position.getX() + speed);
                    position.setY(position.getY() - speed);
                    this.howManyMove = 0;
                    break;
                case 6:
                    position.setX(position.getX() - speed);
                    position.setY(position.getY() + speed);
                    this.howManyMove = 0;
                    break;
                case 7:
                    position.setX(position.getX() - speed);
                    position.setY(position.getY() - speed);
                    this.howManyMove = 0;
                    break;

            }


        } catch (Exception e) {
            this.howManyMove = 0;
            this.move();
        }

    }


    abstract int getSpeed();


    private boolean eat() {
        return false;
    }

    public static class AnimalInfo {
        int id;
        String name;
        int ProductId;


        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getProductId() {
            return ProductId;
        }
    }


}
