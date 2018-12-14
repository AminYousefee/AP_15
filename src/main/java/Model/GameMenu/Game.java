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

    public static Game loadGame(String filename) {
        try {
            File file = new File(filename);
            FileReader fileReader = new FileReader(file);
            JsonReader jsonReader = new JsonReader(fileReader);
            Gson gson = new Gson();
            Game game = gson.fromJson(jsonReader, Game.class);
            return game;
        } catch (IOException e) {
            //todo something in view
        } catch (JsonIOException e) {
            //todo the file is not Json
        } catch (JsonSyntaxException e) {
            //todo Syntax Error in Json File
        }
        return null;

    }

    public boolean saveGame(String filename) {
        Gson gson = new Gson();
        String Json = gson.toJson(this);
        try {

            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write(Json);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            //todo something in view
        } finally {

        }

        return false;
    }

    public Farm getFarm() {
        return farm;
    }


    public void printInfo() {

    }
}