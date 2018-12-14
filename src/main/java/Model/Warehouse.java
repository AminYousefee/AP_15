package Model;

import java.util.ArrayList;

public class Warehouse {


    int capacity;
    ArrayList<Item> items = new ArrayList<>(0);


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
