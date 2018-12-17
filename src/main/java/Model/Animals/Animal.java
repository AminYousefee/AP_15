package Model.Animals;

import Model.Farm;
import Model.Item;
import Model.Upgradable;

public abstract class Animal extends Item implements Upgradable {




    public static final int CAT_VOLUME;
    public static final AnimalInfo Cat_Info = new AnimalInfo("cat", CAT_VOLUME);
    public static final int DOG_VOLUME;
    public static final AnimalInfo Dog_Info = new AnimalInfo("Dog", DOG_VOLUME);
    int fullness;
    int Level;

    public static AnimalInfo findAnimalType(String name) {
        if (name.equalsIgnoreCase("cat")) {
            return Cat_Info;
        } else if (name.equalsIgnoreCase("dog")) {
            return Dog_Info;
        } else {
            return ProductiveAnimal.findAnimalType(name);
        }


    }

    //Finished
    public static Animal getInstance(String name) {
        Animal res =WildAnimal.getInstance(name);
        if (res!=null){
            return res;
        }
        res = NonWildAnimal.getInstance(name);
        if (res!=null){
            return res;
        }
    }

    public abstract boolean move();

    int getSpeed() {
        return false;

    }

    private boolean eat() {
        return false;
    }

    public static class AnimalInfo extends ItemInfo {
        public AnimalInfo(String name, int Volume) {
            super(name, Volume);
        }
    }


    //Finished
    public Animal(AnimalInfo animalInfo) {
        itemInfo =animalInfo;
    }
}
