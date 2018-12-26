package Model.Animals;

import Model.GameMenu.Missions.Mission;
import Model.Map;
import View.GameMenu.Missions.MissionView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import controller.InputProcessor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;

public abstract class NonWildAnimal extends Model.Animals.Animal {


    public NonWildAnimal(NonWildAnimalInfo animalInfo,Map map) {
        super(animalInfo,map);
    }

    public static NonWildAnimal getInstance(String name) {

        NonWildAnimal res = Model.Animals.ProductiveAnimal.getInstance(name);
        if (res != null) {
            return res;
        }
        if (name.equalsIgnoreCase("cat")) {
            return new Cat(InputProcessor.game.getFarm().getMap());
        } else if (name.equalsIgnoreCase("dog")) {
            return new Dog(InputProcessor.game.getFarm().getMap());
        }
        return null;
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
        public NonWildAnimalInfo(String itemName, int depotSize, int buyCost, int SaleCost, int speed) {
            super(itemName, depotSize, buyCost, SaleCost, speed);
        }
    }

}
