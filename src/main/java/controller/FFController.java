package controller;

import Model.GameMenu.Game;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import java.io.*;

public class FFController {
    public static FFController ourInstance = new FFController();
    Game currentGame;
    File MissionsConfigFile;
    File FactoriesConfigFile;
    File NonAnimalItemsConfigFile;
    File WildAnimalConfigFile;
    File ProductiveAnimalConfigFile;
    public static final String MissionsConfigFilePath = "./MissionsConfigFile.json";

    public static final String NonAnimalItemsConfigFilePath = "./NonAnimalItemsConfigFile.json";
    public static final String WildAnimalConfigFilePath  = "./WildAnimalConfigFile.json";
    public static final String ProductiveAnimalConfigFilePath = "./ProductiveAnimalConfigFile.json";
    private FFController() {
        MissionsConfigFile = new File(MissionsConfigFilePath);
    }

    public static FFController getInstance() {
        return ourInstance;
    }

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
        String Json = gson.toJson(this.game);
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


    private boolean loadCustom(String input) {
        return false;
    }
}
