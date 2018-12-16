package Model.Animals;

import java.util.HashSet;

public class WildAnimal extends Animal {

    public static HashSet<WildAnimalInfo> wildAnimalInfos = new HashSet<>(0);

    //Finished
    public WildAnimal(WildAnimalInfo wildAnimalInfo) {
        itemInfo = wildAnimalInfo;
    }

    //Finished
    public static WildAnimal getInstance(String name) {
        for (WildAnimalInfo wildAnimalInfo :
                wildAnimalInfos) {
            if (wildAnimalInfo.getItemName().equalsIgnoreCase(name)) {
                return new WildAnimal(wildAnimalInfo);
            }

        }
    }

    @Override
    public boolean move() {
        return false;
    }

    @Override
    public boolean upgrade(Integer CurrentMoney) {
        return false;
    }

    @Override
    public int getUpgradeCost() {
        return 0;
    }


    public static class WildAnimalInfo extends AnimalInfo {


        public WildAnimalInfo(String name, int Volume) {
            super(name, Volume);
        }
    }
}
