package Model;

import java.util.ArrayList;

public class Map implements Printable{




    Grass grass;

    private static Map ourInstance =new Map();
    private static final int Num_Of_CELLS_IN_ROW=5;
    private static final int Num_Of_CELLS_IN_COLOUM=5;
    Cell[][] cells =new Cell[Num_Of_CELLS_IN_COLOUM][Num_Of_CELLS_IN_ROW];

    private Map() {
        this.cells = cells;
    }
    public static public static Map getInstance() {
        return ourInstance;
    }

    public ArrayList<Cell> getAjacentCells(Cell cell){

    }


    public boolean cage(Position position){
        return true;
    }


    public void turn(){


    }

}
