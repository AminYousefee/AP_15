package Model;

import Model.Animals.Animal;
import Model.Positions.Position;

import java.util.ArrayList;

public abstract class Item {
    protected ItemInfo itemInfo;
    Position position;
    int Volume;
    int ID;
    int lifeTime;
    double price;


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
        return Volume;
    }

    public void setVolume(int volume) {
        Volume = volume;
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
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public static class ItemInfo {
        String ItemName;
        int Volume;

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
