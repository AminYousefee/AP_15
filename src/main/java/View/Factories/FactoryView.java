package View.Factories;

import View.Viewable;

public class FactoryView implements Viewable {


    @Override
    public void existence() {

    }



    public static void unableToMakeFactoriesConfigFile(){

        System.out.println("FactoriesConfigFile.json didn't exist and FF was unable to make it.");

    }
    public static void permissionDeniedToReadFactoriesConfigFile(){
        System.out.println("Permission denied.Couldn't read FactoriesConfigFile.json");
    }
}
