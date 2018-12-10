package View.Farmys;

import Model.Item;
import Model.Positions.Position;

import java.util.ArrayList;

public class Cell {
    Position position;
    ArrayList<Item> items =new ArrayList<>(0);





    public boolean cage(){

        return true;
    }




    public boolean collect(){

    }


    public Position getPosition() {
        return position;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setPosition(Position position) {
        this.position = position;
    }




    public void addItem(Item item){




    }


}
