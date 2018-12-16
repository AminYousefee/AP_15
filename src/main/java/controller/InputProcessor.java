package controller;

import Model.Animals.Animal;
import Model.Factories.Factory;
import Model.GameMenu.Game;
import Model.Helicopter;
import Model.Positions.Position;
import controller.Exceptions.AnimalTypeNotFoundException;
import controller.Exceptions.HelicopterNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputProcessor {
    private Game game;

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
        if (pickup(input)){
            return true;
        }
        if (VehicleGo(input)){
            return true;
        }if ((input)){
            return true;
        }if (pickup(input)){
            return true;
        }if (pickup(input)){
            return true;
        }if (pickup(input)){
            return true;
        }

    }


    @Test
    public boolean processTest() {
        InputProcessor inputProcessor = new InputProcessor();
        inputProcessor.process();

    }

    public boolean buyAnimal(String input) {
        Matcher matcher;
        String regex = "buy\\s+(\\S+)\\s*";
        if ((matcher = getMatched(regex, input)) != null) {
            Animal.AnimalInfo animalInfo = Animal.findAnimalType(matcher.group(1));
            if (animalInfo == null) {
                throw new AnimalTypeNotFoundException();
            }
            game.getFarm().buyAnimal(animalInfo);
        }
        return false;


    }

    private boolean pickup(String input) {
        Matcher matcher;
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
        Matcher matcher;
        String regex = "\\s*(.*)\\s+go\\s*";
        if ((matcher = getMatched(regex, input)) != null) {
            if (matcher.group(1).equalsIgnoreCase("Helicopter")) {
                game.getFarm().getHelicopter().go();


            } else if (matcher.group(1).equalsIgnoreCase("Truck")) {
                game.getFarm().getTruck().go();

            }
            return true;
        }
        return false;
    }


    private boolean print(String input) {
        Matcher matcher;
        String regex = "print\\s+(.+)";
        if ((matcher = getMatched(regex, input)) != null) {
            Factory factory;
            String string = matcher.group(1);
            if (string.equalsIgnoreCase("info")) {
                game.printInfo();
            } else if (string.equalsIgnoreCase("map")) {
                game.getFarm().getMap().printMap();
            } else if (string.equalsIgnoreCase("levels")) {

            } else if (string.equalsIgnoreCase("warehouse")) {

            } else if (string.equalsIgnoreCase("well")) {
                game.getFarm().getBucket().printBucket();

            } else if (string.equalsIgnoreCase("truck")) {
                game.getFarm().getTruck().printTruck();
            } else if (string.equalsIgnoreCase("helicopter")) {
                Helicopter helicopter = game.getFarm().getHelicopter();
                if (helicopter == null) {
                    throw new HelicopterNotFoundException();

                } else {
                    helicopter.printHelicopter();
                }
            } else if (string.equalsIgnoreCase("workshops")) {
                game.getFarm().printWorkshops();

            } else {
                //todo Statement Not Legal

            }
        }


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
            game.getFarm().getBucket().fill();
        }


    }

    private boolean upgrade(String input) {
        Matcher matcher;
        String regex = "\\s+upgrade\\s+(\\.*)\\s*";
        if ((matcher = getMatched(regex, input)) != null) {
            if (matcher.group(1).equalsIgnoreCase("well")) {


                return true;
            }
            if (matcher.group(1).matches("cat")) {

                return true;
            }
            if (matcher.group(1).matches()) {


                return true;
            }
        }
        return false;

    }

    private boolean startFactory(String input) {
        Matcher matcher;
        String regex = "\\s*start\\s+(.+)\\s*";
        if ((matcher = getMatched(regex, input)) != null) {
            String string = matcher.group(1);

            Factory factory = game.getFarm().findFactory(string);
            if (factory == null) {
                //todo
                throw FactoryNotFoundException;
            } else {
                factory.process();
            }


            return true;
        }
        return false;


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


    private boolean clearVehicle(String input) {
        Matcher matcher;
        String regex = "\\s*(.*)\\s+clear\\s*";
        if ((matcher = getMatched(regex, input)) != null) {
            if (matcher.group(1).equalsIgnoreCase("Helicopter")) {
                game.getFarm().getHelicopter().clear();


            } else if (matcher.group(1).equalsIgnoreCase("Truck")) {
                game.getFarm().getTruck().clear();

            }
            return true;
        }
        return false;
    }


}
