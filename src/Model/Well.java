package Model;

public class Well {

    private int remainedWater ;


    private static Well ourInstance = new Well();

    public static Well getInstance() {
        return ourInstance;
    }

    private Well() {
    }

    public int getRemainedWater() {
        return remainedWater;
    }

    public void setRemainedWater(int remainedWater) {
        this.remainedWater = remainedWater;
    }

    public int getMaxCapacity(){
        return 0;
    }



}
