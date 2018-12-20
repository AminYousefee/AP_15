package Model.Animals;

import Model.GameMenu.Missions.Mission;
import Model.Map;
import View.GameMenu.Missions.MissionView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;

public class NonWildAnimal extends Animal{


    public NonWildAnimal(NonWildAnimalInfo animalInfo) {
        super(animalInfo);
    }

    public static NonWildAnimal getInstance(String name, Map map) {

        NonWildAnimal res = ProductiveAnimal.getInstance(name);
        if (res != null) {
            return res;
        }
        if (name.equalsIgnoreCase("cat")) {
            return new Cat(map);
        } else if (name.equalsIgnoreCase("dog")) {
            return new Dog(map);
        }
        return null;
    }

    @Override
    public boolean move() {
        return false;
    }

    @Override
    protected void addFullness() {

    }

    @Override
    public boolean upgrade(Integer CurrentMoney) {
        if (CurrentMoney < getUpgradeCost()) {
            return false;
        }
        CurrentMoney -= getUpgradeCost();
        Level += 1;
        return true;
    }

    @Override
    public int getUpgradeCost() {
        return 0;
    }

    public static class NonWildAnimalInfo extends AnimalInfo {

        public NonWildAnimalInfo(String name, int Volume, int price) {
            super(name, Volume, price);

        }
    }

}
