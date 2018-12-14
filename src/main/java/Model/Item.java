package Model;

import Model.Positions.Position;

public abstract class Item {
    Position position;
    int Volume;
    int ID;
    int lifeTime;
    double price;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getVolume() {
        return Volume;
    }

    public void setVolume(int volume) {
        Volume = volume;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }




    public void anihilate(){

    }
    private void create(){

    }
    public void turn(){

    }



    public void toWarehouse(){

    }


    public void view(){

    }
}
