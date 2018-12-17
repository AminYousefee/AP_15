package Model;

import Model.Animals.Animal;
import Model.Positions.Position;

import java.util.ArrayList;

public abstract class Item {

    protected ItemInfo itemInfo;
    Position position;
    int ID;
    int lifeTime;


    public static Item getInstance(String name) {
        Item result = NonAnimalItems.getInstance(name);
        if (result!=null){
            return result;
        }
        result = Animal.getInstance(name);

    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getVolume() {
        return itemInfo.Volume;
    }

    public void setVolume(int volume) {
        itemInfo.Volume = volume;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    public double getPrice() {
        return itemInfo.price;
    }

    public void setPrice(int price) {
        itemInfo.price = price;
    }

    public void anihilate() {

    }

    private void create() {

    }

    public void turn() {

    }

    public void toWarehouse() {

    }

    public void view() {

    }

    public void Print() {
        System.out.println(itemInfo.getItemName()+":");
        System.out.println("Price = "+itemInfo.price);
        System.out.println("Volume = "+ itemInfo.getVolume());
    }

    public static class ItemInfo {
        String ItemName;
        int Volume;
        int price;
        public boolean isCatCollecable;
        public ItemInfo(String name, int volume) {
            ItemName =name;
            Volume = volume;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String itemName) {
            ItemName = itemName;
        }

        public int getVolume() {
            return Volume;
        }

        public void setVolume(int volume) {
            Volume = volume;
        }
    }
}
