package Model.Animals;

import Model.Item;
import Model.ItemView;
import Model.Upgradable;

public abstract class Animal extends Item implements Upgradable {

    int fullness;
    int level;
    public static final AnimalInfo Cat_Info
    public static final AnimalInfo Dog_Info ;



    public class AnimalInfo{
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


    public abstract boolean move();




    private boolean getSpeed(){

    }


    private boolean eat(){

    }



    public static AnimalInfo findAnimalType(String name){
        if (name.equalsIgnoreCase("cat")){
            return Cat_Info;
        }else if (name.equalsIgnoreCase("dog")){
            return Dog_Info;
        }else {
            return ProductiveAnimal.findAnimalType(name);
        }


    }


}
