package View.Farmys;

import Model.Grass;
import Model.Item;
import Model.Positions.MapPosition;
import Model.Positions.Position;

import java.util.ArrayList;
import java.util.regex.Matcher;

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





    public static void PrintCell(ArrayList<Item> items, MapPosition mapPosition, Grass grass) {
        System.out.println("Cell " + mapPosition.getX() + "," + mapPosition.getY());
        System.out.println("Grass = "+ grass.getNum());
        for (Item item : items) {
            System.out.print("\t");
            item.Print();
        }
    }
}
