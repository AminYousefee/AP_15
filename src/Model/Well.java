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
    public boolean useWater(){
        return true;

    }
    public boolean makeFull(){
        return true;
    }

    public int getMaxCapacity(){
        return 0;
    }



}
