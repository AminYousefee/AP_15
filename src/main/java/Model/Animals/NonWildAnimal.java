package Model.Animals;

import Model.Item;

public class NonWildAnimal extends Animal{
    @Override
    public boolean move() {
        return false;
    }


    @Override
    public boolean upgrade(Integer CurrentMoney) {
        if (CurrentMoney<getUpgradeCost()){
            return false;
        }
        CurrentMoney -= getUpgradeCost();
        Level +=1;
        return true;
    }


    public static NonWildAnimal getInstance(String name) {

        NonWildAnimal res = ProductiveAnimal.getInstance(name);
        if (res!=null){
            return res;
        }
        if (name.equalsIgnoreCase("cat")) {
            return new Cat();
        } else if (name.equalsIgnoreCase("dog")) {
            return new Dog();
        }
    }

}
