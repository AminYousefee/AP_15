package controller;

import Model.*;
import Model.Animals.Animal;
import Model.Factories.Factory;
import Model.GameMenu.Game;
import Model.Positions.MapPosition;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import controller.Exceptions.HelicopterNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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

    public static void GameNotSpecifiedMode() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String string = scanner.nextLine();
            boolean flag = false;
            if (loadGame(string)) {
                flag = true;
            }
            if (runNewGame(string)) {
                flag = true;
            }
            if (loadCustom(string)) {
                flag = true;
            }
            if (!flag) {
                System.out.println("Undefined Statment");
                //todo make it to do something else if the statement is valid in normal mode
            }

        }
    }

    private boolean loadGame(String string) {
        Matcher matcher;
        String regex = "\\s*load\\s+game\\s+(\\S+)\\s*";
        if ((matcher = getMatched(regex, string)) != null) {
            String path = matcher.group(1);
            if (path.endsWith(".json")) {
                Gson gson = new Gson();
                try {
                    FileReader fileReader = new FileReader(path);
                    this.game = gson.fromJson(fileReader, Game.class);

                } catch (FileNotFoundException e) {
                    System.out.println("File Not Found");
                } catch (JsonSyntaxException e) {
                    System.out.println("Syntax Error In Json File");
                }

            } else {


                System.out.println("Json File Needed");
            }


            return true;
        }
        return false;

    }


    private boolean saveGame(String string) {
        Matcher matcher;
        Gson gson = new Gson();
        String regex = "\\s*save\\s+game\\s+(\\S+)\\s*";
        if ((matcher = getMatched(regex, string)) != null) {
            String path = matcher.group(1);
            if (path.endsWith(".json")) {

                try {
                    FileWriter fileWriter = new FileWriter(path);
                    gson.toJson(game, fileWriter);
                } catch (IOException e) {
                    System.out.println("Unable To Write To  the Specified File");
                }
            } else {
                System.out.println("Path should be in the form *.json");
            }
            return true;
        }
        return false;
    }


    private boolean loadCustom(String string) {
        Matcher matcher;
        String regex = "\\s*load\\s+custom\\s+(\\S+)\\s*";
        if ((matcher = getMatched(regex, string)) != null) {
            String path = matcher.group(1);
            File file = new File(path);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                //todo
            } else {
                System.out.println();
            }


            return true;
        }
        return false;
    }


    public boolean process(String input) {
        if (buyAnimal(input)) {
            return true;
        }
        if (pickup(input)) {
            return true;
        }
        if (VehicleGo(input)) {
            return true;
        }
        if ((input)) {
            return true;
        }
        if (pickup(input)) {
            return true;
        }
        if (pickup(input)) {
            return true;
        }
        if (pickup(input)) {
            return true;
        }

    }


    public boolean buyAnimal(String input) {
        Matcher matcher;
        String regex = "buy\\s+(\\S+)\\s*";
        if ((matcher = getMatched(regex, input)) != null) {
            Animal animal = Animal.getInstance(matcher.group(1));
            if (animal == null) {
                System.out.println("Animal Type Not Found");
            }
            game.getFarm().buyAnimal(animal);
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
                MapPosition mapPosition = new MapPosition(x, y);
                game.getFarm().pickUp(mapPosition);
            } catch (NumberFormatException e) {
                System.out.println("Undefined Action");
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
                MapPosition MapPosition = new MapPosition(x, y);
                game.getFarm().cage(MapPosition);
            } catch (NumberFormatException e) {
                //todo
                System.out.println("Undefined Parameters for Statement Cage");
            }
            return true;
        }
        return false;

    }

    private boolean makeWellFull(String input) {
        Matcher matcher;
        String regex = "\\s+well\\s*";
        if ((matcher = getMatched(regex, input)) != null) {
            game.getFarm().getBucket().fill(game.getFarm().getCurrentMoney());
            return true;
        }
        return false;

    }

    private boolean upgrade(String input) {
        Matcher matcher;
        String regex = "\\s+upgrade\\s+(\\.*)\\s*";
        if ((matcher = getMatched(regex, input)) != null) {
            if (matcher.group(1).equalsIgnoreCase("well")) {
                game.getFarm().getBucket().upgrade(game.getFarm().getCurrentMoney());

                return true;
            }
            if (matcher.group(1).equalsIgnoreCase("cat")) {
                game.getFarm().UpgradeCats();

                return true;
            }
            if (matcher.group(1).equalsIgnoreCase("truck")) {
                Truck truck = game.getFarm().getTruck();
                if (truck != null) {
                    truck.upgrade(game.getFarm().getCurrentMoney());
                } else {
                    System.out.println("Truck Not Found");
                }

                return true;
            }
            if (matcher.group(1).equalsIgnoreCase("helicopter")) {
                Helicopter helicopter = game.getFarm().getHelicopter();
                if (helicopter != null) {
                    helicopter.upgrade(game.getFarm().getCurrentMoney());
                } else {
                    System.out.println("Helicopter Not Found");
                }
                return true;
            }
            if (matcher.group(1).equalsIgnoreCase("wareHouse")) {
                game.getFarm().getWarehouse().upgrade(game.getFarm().getCurrentMoney());


                return true;
            }
            Factory factory = game.getFarm().findFactory(matcher.group(1));

            if (factory != null) {
                factory.upgrade(game.getFarm().getCurrentMoney());
                return true;
            }

            return true;

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
                System.out.println("Factory Not Found");
                ;
            } else {
                factory.process();
            }


            return true;
        }
        return false;


    }

    private boolean addToVehicle(String input) {
        Matcher matcher;
        String regex = "\\s*(\\S+)\\s+add(\\S+)\\s+(\\d+)";
        if ((matcher = getMatched(regex, input)) != null) {
            String itemName = matcher.group(2);
            int count = Integer.parseInt(matcher.group(3));
            if (matcher.group(1).equalsIgnoreCase("helicopter")) {
                Item.ItemInfo itemInfo = Helicopter.findItem(itemName);
                if (itemInfo==null){
                    System.out.println("Item not buyable");
                    return true;
                }else {
                    for (int i=0;i<count;i++){
                        game.getFarm().getHelicopter().addItem(Item.getInstance(itemName));
                    }
                }


            } else if (matcher.group(1).equalsIgnoreCase("truck")) {

                Item item = Item.getInstance(itemName);
                if (item==null){
                    System.out.println("Item doesn't exits");
                    return true;
                }
                if (item instanceof NonAnimalItem) {
                    ArrayList<Item> toBeAddedItems = new ArrayList<>(0);
                    List<Item> wareHouseItems = game.getFarm().getWarehouse().getItems();
                    for (Item wareHouseItem : wareHouseItems) {
                        if (wareHouseItem.getItemInfo().getItemName().equalsIgnoreCase(itemName)) {
                            toBeAddedItems.add(wareHouseItem);
                        }
                    }
                    if (toBeAddedItems.size() < count) {
                        System.out.println("Not Enough Number Of " + itemName);
                    } else if (count * item.getItemInfo().getVolume() > game.getFarm().getWarehouse().getCapacity()) {
                        System.out.println("Not Enough Space in the wareHouse");
                    } else {
                        for (Item toBeAddedItem : toBeAddedItems) {
                            game.getFarm().getWarehouse().remove(toBeAddedItem);
                            game.getFarm().getTruck().addItem(toBeAddedItem);
                        }
                    }


                } else {
                    Map map = game.getFarm().getMap();
                    List<Item> toBeAddedItems =map.getItemOfSpecifiedType(itemName);
                    if (toBeAddedItems.size() < count) {
                        System.out.println("Not Enough Number Of " + itemName);
                    } else if (count * item.getItemInfo().getVolume() > game.getFarm().getWarehouse().getCapacity()) {
                        System.out.println("Not Enough Space in the WareHouse");
                    } else {
                        for (Item toBeAddedItem : toBeAddedItems) {
                            map.getCellByPosition(toBeAddedItem.getPosition()).removeItem(toBeAddedItem);
                            game.getFarm().getTruck().addItem(toBeAddedItem);
                        }
                    }


                }


            }

            System.out.println("I don't know that vehicle");
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


    private boolean plant(String input) {
        Matcher matcher;
        String regex = "plant\\s+(\\d+)\\s+(\\d+)\\s+";
        if ((matcher = getMatched(regex, input)) != null) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            MapPosition mapPosition = new MapPosition(x, y);
            game.getFarm().plant(mapPosition);
        }


        return false;
    }


    public boolean turn(String input) {
        Matcher matcher;
        String regex = "\\s*turn\\s+(\\d+)\\*";
        if ((matcher = getMatched(regex, input)) != null) {
            game.turn();
            return true;
        }

        return false;
    }


}
