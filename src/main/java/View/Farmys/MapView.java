package View.Farmys;

import Model.Item;
import Model.Map;
import View.Viewable;

import java.util.ArrayList;

public class MapView implements Viewable {


    public static void PrintMap(Model.Cell[][] cells) {
        System.out.println("Map:");
        for (Model.Cell[] cells1:cells){
            for (Model.Cell cell:cells1){
                cell.PrintCell();
            }
        }


    }

    @Override
    public void existence() {

    }
}
