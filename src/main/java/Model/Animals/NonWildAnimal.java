package Model.Animals;

import Model.GameMenu.Missions.Mission;
import View.GameMenu.Missions.MissionView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;

public class NonWildAnimal extends Animal {




    public static NonWildAnimal getInstance(String name) {

        NonWildAnimal res = ProductiveAnimal.getInstance(name);
        if (res != null) {
            return res;
        }
        if (name.equalsIgnoreCase("cat")) {
            return new Cat();
        } else if (name.equalsIgnoreCase("dog")) {
            return new Dog();
        }
    }

    @Override
    public boolean move() {
        return false;
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

    public static class NonWildAnimalInfo extends AnimalInfo {

        public NonWildAnimalInfo(String name, int Volume, int price) {
            super(name, Volume, price);

        }
    }

}
