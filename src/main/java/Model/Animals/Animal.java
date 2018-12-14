package Model.Animals;

import Model.Item;
import Model.Upgradable;

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

    public static AnimalInfo findAnimalType(String name) {
        if (name.equalsIgnoreCase("cat")) {
            return Cat_Info;
        } else if (name.equalsIgnoreCase("dog")) {
            return Dog_Info;
        } else {
            return ProductiveAnimal.findAnimalType(name);
        }


    }

    public abstract boolean move();


    private boolean getSpeed() {
        return false;

    }


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
