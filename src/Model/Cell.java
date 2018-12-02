package Model;

import Model.Positions.Position;

import java.util.ArrayList;

public class Cell {
    Position position;
    ArrayList<Model.ItemView> items =new ArrayList<>(0);





    public boolean cage(){

        return true;
    }




    public boolean collect(){

    }


    public Position getPosition() {
        return position;
    }

    public ArrayList<Model.ItemView> getItems() {
        return items;
    }

    public void setPosition(Position position) {
        this.position = position;
    }




    public void addItem(Model.ItemView item){




    }


}
