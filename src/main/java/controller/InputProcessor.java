package controller;

import Model.Animals.Animal;
import Model.GameMenu.Game;
import Model.Positions.Position;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputProcessor {
    Game game;

    public static Matcher getMatched(String regex, String string) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        if (!matcher.find())
            return null;
        return matcher;
    }

    public boolean process(String input) {
        if (buyAnimal(input)) {
            return true;
        }

    }

    public boolean buyAnimal(String input) {
        Matcher matcher;
        String regex = "buy\\s+(\\S+)\\s*";
        if ((matcher = getMatched(regex, input)) != null) {
            Animal.AnimalInfo animalInfo = Animal.findAnimalType(matcher.group(1));
            if (animalInfo == null) {
                throw AnimalTypeNotFoundException;
            }
            game.getFarm().buyAnimal(animalInfo);
        }
        return false;


    }

    private boolean pickup(String input) {
        Matcher matcher
        String regex = "pickup\\s+(\\S+)\\s+(\\S+)\\s*";
        if ((matcher = getMatched(regex, input)) != null) {
            try {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                Position position = new Position(x, y);

                game.getFarm().pickUp(position);
            } catch (NumberFormatException e) {
                //todo
            }


            return true;
        }
        return false;


    }

    private boolean VehicleGo(String input) {

    }

    private boolean print(String input) {

    }

    private boolean cage(String input) {
        Matcher matcher;
        String regex = "\\s+cage\\s+(\\S+)\\s+(\\S+)\\s*";
        if ((matcher = getMatched(regex, input)) != null) {
            try {

                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                Position position = new Position(x, y);
                game.getFarm().cage(position);
            } catch (NumberFormatException e) {
                //todo
            }
            return true;
        }
        return false;

    }

    private boolean makeWellFull(String input) {
        Matcher matcher;
        String regex = "\\s+well\\s*";
        if ((matcher = getMatched(regex, input)) != null) {
            game.getFarm().getWell().makeFull();
        }


    }

    private boolean upgrade(String input) {
        Matcher matcher;
        String regex = "\\s+upgrade\\s+(\\.*)\\s*";
        if ((matcher = getMatched(regex, input)) != null) {
            if (matcher.group(1).equalsIgnoreCase("well")) {


                return true
            }
            if (matcher.group(1).matches("cat")) {

                return true;
            }
            if (matcher.group(1).matches())


                return true;
        }
        return false;

    }

    private boolean startFactory(String input) {

    }

    private boolean addToVehicle(String input) {

    }

    private boolean turn(String input) {
        Matcher matcher;
        String regex = "\\s+turn\\s+(\\S+)\\s+";
        if ((matcher = getMatched(regex, input)) != null) {
            try {

                int n = Integer.parseInt(matcher.group(1));
                game.getFarm().turn(n);
            } catch (NumberFormatException e) {
                //todo
            }
            return true;


        }
        return false;


    }


}
