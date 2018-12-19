package Model;

import Model.Animals.Animal;
import Model.Positions.MapPosition;
import Model.Positions.Position;

public abstract class Item {
    protected transient Map map;

    protected ItemInfo itemInfo;
    protected MapPosition mapPosition;
    int ID;
    int lifeTime;


    public static Item getInstance(String name, Position outputPosition) {
        Item result = NonAnimalItem.getInstance(name);
        if (result!=null){
            return result;
        }
        result = Animal.getInstance(name);

    }

    public MapPosition getMapPosition() {
        return mapPosition;
    }

    public void setMapPosition(Position mapPosition) {
        this.mapPosition = mapPosition;
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
        lifeTime++;
    }


    public void Print() {
        System.out.println(itemInfo.getItemName()+":");
        System.out.println("Price = "+itemInfo.price);
        System.out.println("Volume = "+ itemInfo.getVolume());
    }

    public static class ItemInfo {
        protected String ItemName;
        protected int Volume;
        protected int price;
        public boolean isCatCollecable;
        public ItemInfo(String name, int volume,int price) {
            ItemName =name;
            Volume = volume;
            this.price = price;
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
