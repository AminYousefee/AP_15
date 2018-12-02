package Model.Animals;

import Model.ItemView;
import Model.Upgradable;

public abstract class Animal extends ItemView implements Upgradable {
    int fullness;
    int level;


    public abstract boolean move();




    private boolean getSpeed(){

    }


    private boolean eat(){

    }


}
