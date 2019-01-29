package Model;

import Model.Animals.Animal;
import Model.Animals.WildAnimal;
import Model.GameMenu.Game;
import Model.Positions.MapPosition;
import Model.Positions.Position;
import controller.InputProcessor;
import controller.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public abstract class Item {
    protected transient Map map;
    protected ItemInfo itemInfo;
    protected Position position;
    int ID;
    int lifeTime;
    boolean isRemove = false;
    ImageView imageView;

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

    public final boolean turn() {
        //int t = (int) (getSpeed() / 50.0);
        /*
        for (int i = 0; i < t; i++) {
            this.turner(listIterator);
        }*/

        System.out.println(this.getClass());
        synchronized (Game.obj) {
            turner();
        }
        Object obj = new Object();
        synchronized (obj) {
            try {
                obj.wait(InputProcessor.getSpeed() * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        InputProcessor.game.getFarm().getMap().threads.add(new Thread(this::turn));
        return false;
    }

    public void show() {
        if (imageView == null) {
            if (this.getItemInfo().getItemName().equalsIgnoreCase("egg") || this.getItemInfo().getItemName().equalsIgnoreCase("horn") || this.getItemInfo().getItemName().equalsIgnoreCase("plume") || this.getItemInfo().getItemName().equalsIgnoreCase("wool")) {
                try {
                    imageView = new ImageView(new Image(new FileInputStream("/home/a/Projects/AP_Project/AP_15/static/Products/" + this.itemInfo.getItemName() + "/normal.png")));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    imageView = new ImageView(new Image(new FileInputStream("./static/Products/" + itemInfo.getItemName() + ".png")));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }

        }
        AnchorPane.setTopAnchor(imageView, InputProcessor.game.getFarm().getMap().getCellByPosition(((MapPosition) this.getPosition())).getX());
        AnchorPane.setLeftAnchor(imageView, InputProcessor.game.getFarm().getMap().getCellByPosition(((MapPosition) this.getPosition())).getY());
        Main.pane.getChildren().add(imageView);
    }

    public ItemInfo getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(ItemInfo itemInfo) {
        this.itemInfo = itemInfo;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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


    private void create() {

    }

    public boolean turner() {
        lifeTime++;
        return false;
    }


    public void Print() {
        System.out.println(itemInfo.getItemName());
    }


    public int getSaleCost() {
        return itemInfo.SaleCost;
    }

    public int getBuyCost() {
        return itemInfo.BuyCost;
    }

    public void getCollected() {
        Main.pane.getChildren().remove(imageView);

        if (this instanceof NonAnimalItem) {

        } else if (this instanceof WildAnimal) {
            Item item = Item.getInstance("caged" + this.itemInfo.getItemName());
            if (InputProcessor.game.getFarm().getWarehouse().getCapacity() > item.getItemInfo().getDepotSize()) {
                map.getCellByPosition((MapPosition) this.getPosition()).removeItem(this);
                InputProcessor.game.getFarm().getWarehouse().addItem(item);

            }

        }
    }

    public void die() {
        //Main.gridPane.getChildren().remove(this.imageView);
    }

    public static class ItemInfo {
        protected String ItemName;
        protected double DepotSize;
        protected int BuyCost;
        protected int SaleCost;
        Image image;

        public ItemInfo(String itemName, double depotSize, int buyCost, int SaleCost) {
            ItemName = itemName;
            DepotSize = depotSize;
            this.BuyCost = buyCost;
            this.SaleCost = SaleCost;
            /*try {
                image = new Image(new FileInputStream(ItemName + ".png"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/
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
