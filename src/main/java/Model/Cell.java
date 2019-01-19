package Model;

import Model.Animals.ProductiveAnimal;
import Model.Animals.WildAnimal;
import Model.Positions.MapPosition;
import controller.Main;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.ListIterator;

public class Cell {
    Grass grass;
    MapPosition mapPosition;
    ArrayList<Item> items = new ArrayList<>(0);
    private GridPane gridPane;
    Rectangle grassRect;

    public Cell(int x, int y) {
        grass = new Grass();
        mapPosition = new MapPosition(x, y);
        items = new ArrayList<>(0);
    }

    public Cell(Grass grass, MapPosition mapPosition, ArrayList<Item> items) {
        this.grass = grass;
        this.mapPosition = mapPosition;
        this.items = items;
    }

    public Grass getGrass() {
        return grass;
    }

    public void setGrass(Grass grass) {
        this.grass = grass;
    }

    public boolean collect() {

        // Priority NonAnimal WildAnimal ProductiveAnimal Dog Cat
        Item toBeCollectedItem = null;
        for (Item item : items) {
            if (item instanceof NonAnimalItem) {
                toBeCollectedItem = item;
            }
        }
        if (toBeCollectedItem != null) {
            toBeCollectedItem.getCollected(Main.gridPane);

            return true;
        }

        for (Item item : items) {
            if (item instanceof WildAnimal && ((WildAnimal) item).getCage() != null && ((WildAnimal) item).getCage().getCompletnessPercetage() == ((WildAnimal) item).getCage().getProgressMaxValue()) {

                toBeCollectedItem = item;
            }
        }
        if (toBeCollectedItem != null) {
            toBeCollectedItem.getCollected(Main.gridPane);
            return true;
        }

        /*for (Item item:items){
            if (item instanceof ProductiveAnimal){
                toBeCollectedItem= item;
            }
        }
        if (toBeCollectedItem!=null){
            return true;
        }
        for (Item item:items){
            if (item instanceof Dog){
                toBeCollectedItem= item;
            }
        }
        if (toBeCollectedItem!=null){
            return true;
        }

        for (Item item:items){
            if (item instanceof Cat){
                toBeCollectedItem= item;
            }
        }
        if (toBeCollectedItem!=null){
            return true;
        }*/

        System.out.println("Couldn't collect anything");

        return false;


    }

    public MapPosition getMapPosition() {
        return mapPosition;
    }

    public void setMapPosition(MapPosition mapPosition) {
        this.mapPosition = mapPosition;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
        item.setPosition(this.getMapPosition());


    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public Item getCatCollectableItem() {
        for (Item item : items) {
            if (item.itemInfo instanceof NonAnimalItem.NonAnimalItemInfo) {
                return item;
            }
        }
        return null;
    }

    public void PrintCell() {
        View.Farmys.Cell.PrintCell(items, this.getMapPosition(), this.grass);
    }


    public void turn() {
        ListIterator<Item> itemIterator = items.listIterator();
        Item item;
        while (itemIterator.hasNext()) {
            item = itemIterator.next();
            item.turn(itemIterator);
        }
    }

    public boolean containsNonWildAnimalOrItem() {
        for (Item item : items) {
            if (item instanceof NonAnimalItem || item instanceof ProductiveAnimal) {
                return true;
            }
        }
        return false;
    }

    public boolean containsWildAniaml() {
        for (Item item : items) {
            if (item instanceof WildAnimal) {
                return true;
            }
        }
        return false;
    }

    public void show() {
        if (grassRect==null){
            grassRect  = new Rectangle(25, 25, Color.rgb(0, ((int) ((double) grass.getNum() / 100 * 255)), 0));
            gridPane.add(grassRect,mapPosition.getX(),mapPosition.getY());

        }else {
            grassRect.setFill(Color.rgb(0, ((int) ((double) grass.getNum()) / 100 * 255),0));
        }
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }
}
