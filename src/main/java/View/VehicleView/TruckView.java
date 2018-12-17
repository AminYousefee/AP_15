package View.VehicleView;

import Model.Item;
import Model.Truck;

import java.util.ArrayList;

public class TruckView extends Vehicle {


    private TruckView(){

    }


    public static void PrintTruck(Truck truck) {
        System.out.println("Truck:");
        ArrayList<Item> items  = truck.getItems();
        for (Item item:items){
            item.Print();
        }
    }
}
