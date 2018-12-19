package View.Farmys;

import Model.Item;
import Model.Positions.Position;

import java.util.ArrayList;

public class Cell {
    Position position;


    public boolean cage() {

        return true;
    }


    public boolean collect() {
        return false;
    }


    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }





    public static void PrintCell(ArrayList<Item> items) {
        System.out.println("Cell " + getPosition().getX() + "," + getPosition().getY());
        for (Item item : items) {
            System.out.println("\t");
            item.Print();
        }
    }
}
