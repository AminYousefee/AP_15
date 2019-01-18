package View;

import com.google.gson.JsonSyntaxException;

public class NonAnimalItemView extends ItemView {
    public static void FileNotFoundException() {
        System.out.println("FileNotFoundException Occured");
    }

    @Override
    public void showCreation() {

    }

    @Override
    public void showAnihilation() {

    }

    @Override
    public void showExistense() {

    }

    @Override
    public void showToWarehouse() {

    }

    @Override
    public void existence() {

    }




    public static void JsonSyntaxExceptionOccured(){
        System.out.println("JsonSyntaxExceptionOccured in file NonAnimalItemsConfigFile.json");
    }
}
