package Model.Positions;

public class MapPosition extends Position {


    int x, y;

    public MapPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }







    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MapPosition)){
            return false;
        }
        if (((MapPosition) obj).getX()==this.getX()&&((MapPosition) obj).getY()==this.getY()){
            return true;
        }else {
            return false;
        }

    }
}
