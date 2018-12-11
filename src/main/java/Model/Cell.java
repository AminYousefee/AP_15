package Model;

import Model.Positions.Position;

import java.util.ArrayList;

public class Cell {
    Position position;
    ArrayList<Model.Item> items =new ArrayList<>(0);





    public boolean cage(){

        return true;
    }




    public boolean collect(){

    }


    public Position getPosition() {
        return position;
    }

    public ArrayList<Model.Item> getItems() {
        return items;
    }

    public void setPosition(Position position) {
        this.position = position;
    }




    public void addItem(Model.Item item){




    }


}
