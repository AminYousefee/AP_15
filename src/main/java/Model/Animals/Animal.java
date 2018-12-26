package Model.Animals;

import Model.*;
import Model.Positions.MapPosition;
import Model.Positions.NonMapPosition;

import java.util.Random;

public abstract class Animal extends Item implements Upgradable {

    MapPosition goalPosition;


    public static final int CAT_VOLUME=0;
    public static final NonWildAnimal.NonWildAnimalInfo Cat_Info = Cat.CatInfo.getInstance();
    public static final int DOG_VOLUME=0;
    public static final NonWildAnimal.NonWildAnimalInfo Dog_Info = Dog.DogInfo.getInstance();
    int fullness;
    int Level;

    //Finished
    public Animal(AnimalInfo animalInfo,Map map) {
        itemInfo = animalInfo;
        this.map =map;
    }

/*    public static AnimalInfo findAnimalType(String name) {
        if (name.equalsIgnoreCase("cat")) {
            return Cat_Info;
        } else if (name.equalsIgnoreCase("dog")) {
            return Dog_Info;
        } else {
            return ProductiveAnimal.findAnimalType(name);
        }


    }*/

    //Finished
    public static Animal getInstance(String name) {
        Animal res = WildAnimal.getInstance(name);
        if (res != null) {
            return res;
        }
        res = NonWildAnimal.getInstance(name);
        if (res != null) {
            return res;
        }
        return null;
    }

    public abstract boolean move();

    public int getSpeed() {
        return 5;

    }

    public boolean eat() {
        if (this instanceof ProductiveAnimal && fullness<=0){
            this.anihilate();
            return true;//well this bad smell I should do it
        }
        if (getPosition() instanceof NonMapPosition){
            return false;
            //do nothing in this case
        }

        Cell cell = map.getCellByPosition((MapPosition) getPosition());
        if (cell.getGrass().getNum()>0&&fullness<7.0/10*((ProductiveAnimal.ProductiveAnimalInfo)this.itemInfo).HungryValue){
             cell.getGrass().getEaten();
            this.addFullness();
            return true;
        }else {
            return false;
        }
    }

    protected abstract void addFullness();

    public static class AnimalInfo extends ItemInfo {
        int Speed;


        public AnimalInfo(String itemName, int depotSize, int buyCost, int SaleCost, int speed) {
            super(itemName, depotSize, buyCost, SaleCost);
            Speed = speed;
        }
    }

    @Override
    public boolean turn() {
        if (this instanceof ProductiveAnimal){
            ((ProductiveAnimal) this).produce();
            this.fullness-=((ProductiveAnimal.ProductiveAnimalInfo)this.itemInfo).HungrySpeed;
        }

        if (eat()){

        }else {
            return move();
        }
        return false;

    }

    public boolean moveToPosition(MapPosition position) {
        Cell goalCell = map.getCell(position);
        Cell CurrentCell = map.getCell((MapPosition) this.getPosition());

        //maybe there we'll be a better way of organizing it
        if (goalCell ==CurrentCell){
            return false;
        }
        CurrentCell.getItems().remove(this);
        goalCell.getItems().add(this);
        setPosition(position);

        return true;
    }





}
