package Model;

import Model.Animals.Animal;
import Model.Animals.WildAnimal;
import Model.Positions.MapPosition;
import Model.Positions.Position;
import controller.InputProcessor;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class Item {
    protected transient Map map;
    protected ItemInfo itemInfo;
    protected Position Position;
    int ID;
    int lifeTime;

    public static Item getInstance(String name) {
        Item result = NonAnimalItem.getInstance(name);
        if (result != null) {
            return result;
        }
        result = Animal.getInstance(name);
        if (result != null) {
            return result;
        }
        return null;

    }

    public ItemInfo getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(ItemInfo itemInfo) {
        this.itemInfo = itemInfo;
    }

    public Position getPosition() {
        return Position;
    }

    public void setPosition(Position position) {
        this.Position = position;
    }

    public double getVolume() {
        return itemInfo.DepotSize;
    }

    public void setVolume(int volume) {
        itemInfo.DepotSize = volume;
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

    public void anihilate() {


    }

    private void create() {

    }

    public boolean turn() {
        lifeTime++;
        return false;
    }


    public void Print() {
        System.out.println(itemInfo.getItemName() + ":");
        System.out.println("BuyCost = " + itemInfo.getBuyCost());
        System.out.println("SaleCost = " + itemInfo.getSaleCost());
        System.out.println("DepotSize = " + itemInfo.getDepotSize());
    }



    public int getSaleCost() {
        return itemInfo.SaleCost;
    }

    public int getBuyCost() {
        return itemInfo.BuyCost;
    }

    public void getCollected(){

        if (this instanceof NonAnimalItem){
            if (InputProcessor.game.getFarm().getWarehouse().getCapacity()>this.getItemInfo().getDepotSize()){

                map.getCellByPosition((MapPosition) this.getPosition()).removeItem(this);
                InputProcessor.game.getFarm().getWarehouse().addItem(this);
            }else {
                System.out.println("Not Enough Space in WareHouse");
            }
        }else if (this instanceof WildAnimal){
            Item item =Item.getInstance("caged"+this.itemInfo.getItemName());
            if (InputProcessor.game.getFarm().getWarehouse().getCapacity()>item.getItemInfo().getDepotSize()){
                map.getCellByPosition((MapPosition) this.getPosition()).removeItem(this);
                InputProcessor.game.getFarm().getWarehouse().addItem(item);

            }

        }
    }

    public static class ItemInfo {
        protected String ItemName;
        protected double DepotSize;
        protected int BuyCost;
        protected int SaleCost;

        public ItemInfo(String itemName, double depotSize, int buyCost, int SaleCost) {
            ItemName = itemName;
            DepotSize = depotSize;
            this.BuyCost = buyCost;
            this.SaleCost = SaleCost;
        }

        public int getBuyCost() {
            return BuyCost;
        }

        public void setBuyCost(int buyCost) {
            this.BuyCost = buyCost;
        }

        public int getSaleCost() {
            return SaleCost;
        }

        public void setSaleCost(int saleCost) {
            this.SaleCost = saleCost;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String itemName) {
            ItemName = itemName;
        }

        public double getDepotSize() {
            return DepotSize;
        }

        public void setDepotSize(double depotSize) {
            DepotSize = depotSize;
        }


        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ItemInfo) {
                return getItemName().equals(((ItemInfo) obj).getItemName());
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return getItemName().hashCode();
        }
    }
}
