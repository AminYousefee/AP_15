package View.VehicleView;

public class Truck extends Vehicle {


    private Truck(){

    }



    private static Truck ourInstance=new Truck();



    public static public static Truck getInstance() {
        return ourInstance;
    }

}
