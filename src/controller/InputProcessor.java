package controller;

import Model.Animals.Animal;
import Model.Farm;
import Model.GameMenu.Game;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputProcessor {
    Farm farm;
    Game game;



    public boolean process(String input){


    }
    public boolean buyAnimal(String string){
        Matcher matcher;
        String regex = "buy\\s+(\\S+)\\s+";
        if ((matcher = getMatched(regex,string))!=null){
            Animal.AnimalInfo animalInfo = Animal.findAnimalType(matcher.group(1));

            game.getFarm().buyAnimal(animalInfo);





        }
        return false;


    }



    private boolean pickup(String input){

    }

    private boolean VehicleGo(String input){

    }
    private boolean print(String input){

    }

    private boolean cage(String input){


    }
    private boolean makeWellFull(String input){


    }
    private boolean upgrade(String input){

    }

    private boolean startFactory(String input){

    }
    private boolean addToVehicle(String input){

    }



    public static Matcher getMatched(String regex, String string) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        if (!matcher.find())
            return null;
        return matcher;
    }




}
