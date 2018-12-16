package Model.GameMenu;

import Model.Farm;
import Model.GameMenu.Missions.Mission;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Game {


    private Mission mission;
    private Farm farm;


    public Farm getFarm() {
        return farm;
    }


    public void printInfo() {

    }
}
