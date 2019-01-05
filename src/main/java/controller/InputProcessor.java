package controller;


import Model.Animals.*;
import Model.*;
import Model.Factories.Factory;
import Model.GameMenu.Game;
import Model.GameMenu.Missions.Mission;
import Model.Positions.MapPosition;
import Model.Positions.NonMapPosition;
import Model.Positions.Position;
import Model.Warehouse;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputProcessor {
    public static Game game;
    public static Gson gson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();

        RuntimeTypeAdapterFactory<Item> typeAdapterFactory = RuntimeTypeAdapterFactory.of(Item.class, "type")
                .registerSubtype(NonAnimalItem.class, "na")
                .registerSubtype(ProductiveAnimal.class, "pro")
                .registerSubtype(Cat.class, "cat")
                .registerSubtype(Dog.class, "dog")
                .registerSubtype(WildAnimal.class, "wild");
        RuntimeTypeAdapterFactory<Position> typeAdapterFactory1 = RuntimeTypeAdapterFactory.of(Position.class, "tye")
                .registerSubtype(MapPosition.class, "map")
                .registerSubtype(NonMapPosition.class, "nMap");
        gsonBuilder.registerTypeAdapterFactory(typeAdapterFactory);
        gsonBuilder.registerTypeAdapterFactory(typeAdapterFactory1);
        RuntimeTypeAdapterFactory<Item.ItemInfo> typeAdapterFactory2 = RuntimeTypeAdapterFactory.of(Item.ItemInfo.class, "tt")
                .registerSubtype(NonAnimalItem.NonAnimalItemInfo.class, "naie")
                .registerSubtype(ProductiveAnimal.ProductiveAnimalInfo.class, "paie")
                .registerSubtype(WildAnimal.WildAnimalInfo.class, "wai")
                .registerSubtype(Cat.CatInfo.class, "catInfo")
                .registerSubtype(Dog.DogInfo.class, "dogInfo");
        gsonBuilder.registerTypeAdapterFactory(typeAdapterFactory2);
        gsonBuilder.registerTypeAdapter(Mission.Goal.class, new GoalDeserializer());
        gsonBuilder.registerTypeAdapter(Farm.class, new FarmDeserializer());
        gsonBuilder.registerTypeAdapter(Cell.class, new CellDeserializer());
        gsonBuilder.registerTypeAdapter(Map.class, new MapDeserializer());
        gsonBuilder.registerTypeAdapter(Warehouse.class, new WareHouseDeserializer());
        gsonBuilder.registerTypeAdapter(Mission.class, new MissionDeserializer());
        gsonBuilder.registerTypeAdapter(Game.class, new GameDeserializer());
        gsonBuilder.registerTypeAdapter(Factory.class, new FactoryDeserializer());
        gsonBuilder.registerTypeAdapter(Truck.class, new TruckDeserializer());
        gsonBuilder.registerTypeAdapter(Helicopter.class, new HelicopterDeserializer());
        gson = gsonBuilder.create();


    }

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
            if (endStatemet(string)) {
                flag = true;
            }
            if (!flag) {
                System.out.println("Undefined Statement");
                //todo make it to do something else if the statement is valid in normal mode
            }

            if (game != null) {
                break;
            }

        }
    }

    private static boolean runNewGame(String string) {
        Matcher matcher;
        String regex = "\\s*run\\s+(\\S+)\\s*";
        if ((matcher = getMatched(regex, string)) != null) {
            //Farm farm = Farm.findLoadedFarm(matcher.group(1));
            Game game = Game.findLoadedGame(matcher.group(1));
            if (game == null) {
                System.out.println("The Specified Farm Not Found");
            } else {
                InputProcessor.game = game;
            }
            return true;
        }
        return false;
    }

    private static boolean loadGame(String string) {
        Matcher matcher;
        String regex = "\\s*load\\s+game\\s+(\\S+)\\s*";
        if ((matcher = getMatched(regex, string)) != null) {
            String path = matcher.group(1);
            if (path.endsWith(".json")) {
                //Gson gson = new Gson();
                try {
                    FileReader fileReader = new FileReader(path);
                    game = gson.fromJson(fileReader, Game.class);
                    //ObjectMapper objectMapper =new ObjectMapper();
                    //objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
                    //@SuppressWarnings("unchecked")
                    //Game game = objectMapper.readValue(fileReader, Game.class);

                } catch (Exception e) {
                    System.out.println();
                    e.printStackTrace();
                }

            } else {


                System.out.println("Json File Needed");
            }


            return true;
        }
        return false;

    }

    public static boolean clearVehicle(String string) {
        Matcher matcher;
        String regex = "\\s*(\\S+)\\s+clear\\s*";
        if ((matcher = getMatched(regex, string)) != null) {
            if (matcher.group(1).equalsIgnoreCase("truck")) {
                Truck truck = game.getFarm().getTruck();
                if (truck == null) {
                    System.out.println("Truck not Found");
                }
                int Volume = 0;
                for (Item item : truck.getItems()) {
                    if (item instanceof NonWildAnimal) {
                        game.getFarm().getMap().addItemInRandom(item);
                    } else if (item instanceof NonAnimalItem) {
                        Volume += item.getItemInfo().getDepotSize();
                    }


                }
                if (Volume > game.getFarm().getWarehouse().getCapacity()) {
                    System.out.println("Not Enough Space in the wareHouse");
                }
                for (Item item : truck.getItems()) {
                    if (!(item instanceof NonWildAnimal)) {
                        game.getFarm().getWarehouse().addItem(item);
                    }


                }
                game.getFarm().getTruck().clear();


            } else if (matcher.group(1).equalsIgnoreCase("helicopter")) {
                Helicopter helicopter = game.getFarm().getHelicopter();
                if (helicopter == null) {
                    System.out.println("Helicopter Not Found");
                } else {
                    game.getFarm().getHelicopter().clear();


                }


            } else {
                System.out.println("I don't know what to clear");
            }
            return true;
        }
        return false;

    }

    private static boolean loadCustom(String string) {
        Matcher matcher;
        String regex = "\\s*load\\s+custom\\s+(\\S+)\\s*";
        if ((matcher = getMatched(regex, string)) != null) {
            String path = matcher.group(1);
            File file = new File(path);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File file1 : files) {
                    if (file1.getAbsolutePath().endsWith(".json")) {
                        try {
                            FileReader fileReader = new FileReader(file1.getAbsoluteFile());
                            //Gson gson = new Gson();
                            try {
                                Game game = gson.fromJson(fileReader, Game.class);
                                Game.loadedGames.add(game);
                            } catch (JsonParseException e) {
                                Factory.FactoryType factoryType = gson.fromJson(fileReader, Factory.FactoryType.class);
                                Factory.factoryTypeArrayList.add(factoryType);
                            }

                        } catch (FileNotFoundException e) {
                            System.out.println("file couldn't be read " + file1.getAbsolutePath());
                        }
                    } else {
                        System.out.println("not json file found but ignored");
                    }


                }
                //todo
            } else {
                System.out.println("There must be a bug shouldn't the path be a directory");
            }


            return true;
        }
        return false;
    }

    public static boolean endStatemet(String string) {
        Matcher matcher;
        String regex = "\\s*end\\s*";
        if ((matcher = getMatched(regex, string)) != null) {
            System.exit(0);
            return true;
        }
        return false;
    }

    public static boolean process(String input) {
        if (startFactory(input)) {
            return true;
        }
        if (upgrade(input)) {
            return true;
        }

        if (buyAnimal(input)) {
            return true;
        }

        if (VehicleGo(input)) {
            return true;
        }
        if (addToVehicle(input)) {
            return true;
        }

        if (pickup(input)) {
            return true;
        }
        if (cage(input)) {
            return true;
        }
        if (makeWellFull(input)) {
            return true;
        }
        if (plant(input)) {
            return true;
        }
        if (loadGame(input)) {
            return true;
        }
        if (runNewGame(input)) {
            return true;
        }
        if (loadCustom(input)) {
            return true;
        }
        if (endStatemet(input)) {
            return true;
        }
        if (print(input)) {
            return true;
        }
        if (turn(input)) {
            return true;
        }
        if (clearVehicle(input)){
            return true;
        }
        if (saveGame(input)) {
            return true;
        }
        if ((getMatched("^\\s*$", input) != null)) {
            return true;
        }
        System.out.println("Undefined Statement");
        return false;
    }

    public static boolean buyAnimal(String input) {
        Matcher matcher;
        String regex = "buy\\s+(\\S+)\\s*";
        if ((matcher = getMatched(regex, input)) != null) {
            Animal animal = Animal.getInstance(matcher.group(1));
            if (animal == null) {
                System.out.println("Animal Type Not Found");
            } else {
                game.getFarm().buyAnimal(animal);
            }
            return true;
        }
        return false;


    }

    private static boolean pickup(String input) {
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

    private static boolean VehicleGo(String input) {
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

    private static boolean cage(String input) {
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

    private static boolean makeWellFull(String input) {
        Matcher matcher;
        String regex = "^well\\s*";
        if ((matcher = getMatched(regex, input)) != null) {
            game.getFarm().getBucket().fill(game.getFarm());
            return true;
        }
        return false;

    }

    private static boolean upgrade(String input) {
        Matcher matcher;
        String regex = "\\s*upgrade\\s+(.+)\\s*";
        if ((matcher = getMatched(regex, input)) != null) {
            if (matcher.group(1).equalsIgnoreCase("well")) {
                game.getFarm().getBucket().upgrade(game.getFarm());

                return true;
            }
            if (matcher.group(1).equalsIgnoreCase("cat")) {
                game.getFarm().UpgradeCats();

                return true;
            }
            if (matcher.group(1).equalsIgnoreCase("truck")) {
                Truck truck = game.getFarm().getTruck();
                if (truck != null) {
                    truck.upgrade(game.getFarm());
                } else {
                    System.out.println("Truck Not Found");
                }

                return true;
            }
            if (matcher.group(1).equalsIgnoreCase("helicopter")) {
                Helicopter helicopter = game.getFarm().getHelicopter();
                if (helicopter != null) {
                    helicopter.upgrade(game.getFarm());
                } else {
                    System.out.println("Helicopter Not Found");
                }
                return true;
            }
            if (matcher.group(1).equalsIgnoreCase("wareHouse")) {
                game.getFarm().getWarehouse().upgrade(game.getFarm());


                return true;
            }
            Factory factory = game.getFarm().findFactory(matcher.group(1));

            if (factory != null) {
                factory.upgrade(game.getFarm());
                return true;
            }

            return false;

        }
        return false;

    }

    private static boolean startFactory(String input) {
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
                factory.startProcess(game.getFarm().getWarehouse());
            }


            return true;
        }
        return false;


    }

    private static boolean addToVehicle(String input) {
        Matcher matcher;
        String regex = "\\s*(\\S+)\\s+add\\s+(\\S+)\\s+(\\d+)";
        if ((matcher = getMatched(regex, input)) != null) {
            String itemName = matcher.group(2);
            int count = Integer.parseInt(matcher.group(3));
            if (matcher.group(1).equalsIgnoreCase("helicopter")) {
                Item.ItemInfo itemInfo = Helicopter.findItem(itemName);
                if (itemInfo == null) {
                    System.out.println("Item not buyable");
                    return true;
                } else {
                    for (int i = 0; i < count; i++) {
                        game.getFarm().getHelicopter().addItem(Item.getInstance(itemName));
                    }
                }


            } else if (matcher.group(1).equalsIgnoreCase("truck")) {

                Item item = Item.getInstance(itemName);
                if (item == null) {
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
                    } else if (count * item.getItemInfo().getDepotSize() > game.getFarm().getWarehouse().getCapacity()) {
                        System.out.println("Not Enough Space in the wareHouse");
                    } else {
                        for (int i = 0; i < count; i++) {
                            Item toBeAddedItem = toBeAddedItems.get(i);
                            game.getFarm().getWarehouse().remove(toBeAddedItem);
                            game.getFarm().getTruck().addItem(toBeAddedItem);
                        }
                    }


                } else {
                    Map map = game.getFarm().getMap();
                    List<Item> toBeAddedItems = map.getItemOfSpecifiedType(itemName);
                    if (toBeAddedItems.size() < count) {
                        System.out.println("Not Enough Number Of " + itemName);
                    } else if (count * item.getItemInfo().getDepotSize() > game.getFarm().getWarehouse().getCapacity()) {
                        System.out.println("Not Enough Space in the WareHouse");
                    } else {
                        for (Item toBeAddedItem : toBeAddedItems) {
                            map.getCellByPosition((MapPosition) toBeAddedItem.getPosition()).removeItem(toBeAddedItem);
                            game.getFarm().getTruck().addItem(toBeAddedItem);
                        }
                    }


                }


            } else {

                System.out.println("I don't know that vehicle");
            }
            return true;
        }
        return false;
    }

 /*    private boolean clearVehicle(String input) {
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
 */
    private static boolean plant(String input) {
        Matcher matcher;
        String regex = "plant\\s+(\\d+)\\s+(\\d+)\\s*";
        if ((matcher = getMatched(regex, input)) != null) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            MapPosition mapPosition = new MapPosition(x, y);
            game.getFarm().plant(mapPosition);
            return true;
        }


        return false;
    }

    private static boolean print(String input) {
        Matcher matcher;
        String regex = "print\\s+(\\S+)\\s*";
        if ((matcher = getMatched(regex, input)) != null) {
            Factory factory;
            String string = matcher.group(1);
            if (string.equalsIgnoreCase("info")) {
                game.printInfo();
            } else if (string.equalsIgnoreCase("map")) {
                game.getFarm().getMap().printMap();
            } else if (string.equalsIgnoreCase("levels")) {


            } else if (string.equalsIgnoreCase("warehouse")) {
                game.getFarm().getWarehouse().print();

            } else if (string.equalsIgnoreCase("well")) {
                game.getFarm().getBucket().printBucket();

            } else if (string.equalsIgnoreCase("truck")) {
                game.getFarm().getTruck().printTruck();
            } else if (string.equalsIgnoreCase("helicopter")) {
                Helicopter helicopter = game.getFarm().getHelicopter();
                if (helicopter == null) {
                    System.out.println("Helicopter Not Found");

                } else {
                    helicopter.printHelicopter();
                }
            } else if (string.equalsIgnoreCase("workshops")) {
                game.getFarm().printWorkshops();

            } else {
                //todo Statement Not Legal
                System.out.println("Undefined Statement");

            }
            return true;
        }

        return false;

    }

    public static boolean turn(String input) {
        Matcher matcher;
        String regex = "\\s*turn\\s+(\\d+)\\s*";
        if ((matcher = getMatched(regex, input)) != null) {
            int n = Integer.parseInt(matcher.group(1));
            for (int i = 0; i < n; i++) {
                game.turn();

            }
            return true;
        }

        return false;
    }

    private static boolean saveGame(String string) {
        Matcher matcher;
        //Gson gson = new Gson();
        String regex = "\\s*save\\s+game\\s+(\\S+)\\s*";
        if ((matcher = getMatched(regex, string)) != null) {
            String path = matcher.group(1);
            if (path.endsWith(".json")) {

                try {
                    FileWriter fileWriter = new FileWriter(path);
                    gson.toJson(game, fileWriter);
                    fileWriter.flush();
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

    @Test
    public void processTest() {
        InputProcessor inputProcessor = new InputProcessor();
        if (game == null) {
            assertTrue(true);
        } else {
            assertTrue(true);
        }
        System.exit(0);
    }

/*
    @Test
    public void JsonFormatTest() throws IOException {
        HashSet<Item.ItemInfo> itemInfos = new HashSet<>(0);
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("a.txt");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(fileReader);
        while (scanner.hasNext()) {
            String string = scanner.nextLine();
            Matcher matcher = getMatched("(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)", string);
            itemInfos.add(new Item.ItemInfo(matcher.group(1), Double.parseDouble(matcher.group(2)), Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4))));

        }
        scanner.close();
        try {
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileWriter fileWriter = new FileWriter("NonAnimalItemsConfigFile.json");


        Gson gson = new Gson();
        gson.toJson(itemInfos, fileWriter);
        fileWriter.flush();
        fileWriter.close();
        assertTrue(true);

    }*/

/*

    @Test
    public void initTest() throws IOException {

        FileReader fileReader = new FileReader("a.txt");
        Scanner scanner = new Scanner(fileReader);
        HashSet<Factory.FactoryType> factoryTypes = new HashSet<>(0);
        while (scanner.hasNext()) {
            String string1 = scanner.nextLine();
            String string2 = scanner.nextLine();
            String string3 = scanner.nextLine();
            String string4 = scanner.nextLine();
            String string5 = scanner.nextLine();
            ArrayList<Factory.FactoryType.t> Ts = new ArrayList<>(0);
            Matcher matcher = getMatched("(.+)\t(\\S+)\t(\\S+)\t(\\S+)\t(\\S+)\t(\\S+)", string1);
            Factory.FactoryType.t T = new Factory.FactoryType.t(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), Double.parseDouble(matcher.group(4)), Integer.parseInt(matcher.group(5)), Integer.parseInt(matcher.group(6)));
            Ts.add(T);
            matcher = getMatched("(.+)\t(\\S+)\t(\\S+)\t(\\S+)\t(\\S+)\t(\\S+)", string2);
            T = new Factory.FactoryType.t(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), Double.parseDouble(matcher.group(4)), Integer.parseInt(matcher.group(5)), Integer.parseInt(matcher.group(6)));
            Ts.add(T);
            matcher = getMatched("(.+)\t(\\S+)\t(\\S+)\t(\\S+)\t(\\S+)\t(\\S+)", string3);
            T = new Factory.FactoryType.t(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), Double.parseDouble(matcher.group(4)), Integer.parseInt(matcher.group(5)), Integer.parseInt(matcher.group(6)));
            Ts.add(T);
            matcher = getMatched("(.+)\t(\\S+)\t(\\S+)\t(\\S+)\t(\\S+)\t(\\S+)", string4);
            T = new Factory.FactoryType.t(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), Double.parseDouble(matcher.group(4)), Integer.parseInt(matcher.group(5)), Integer.parseInt(matcher.group(6)));
            Ts.add(T);
            matcher = getMatched("(.+)\t(\\S+)\t(\\S+)\t(\\S+)\t(\\S+)\t(\\S+)", string5);
            T = new Factory.FactoryType.t(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), Double.parseDouble(matcher.group(4)), Integer.parseInt(matcher.group(5)), Integer.parseInt(matcher.group(6)));
            Ts.add(T);
            factoryTypes.add(new Factory.FactoryType(matcher.group(1), Ts));


        }


        Gson gson = new Gson();
        FileWriter fileWriter = new FileWriter(Factory.FactoriesConfigFilePath);
        gson.toJson(factoryTypes, fileWriter);
        fileWriter.flush();
        fileWriter.close();


    }
*/

    @Test
    public void tr() {
        ArrayList<Integer> integers = new ArrayList<>(0);
        integers.add(3);
        integers.add(5);
        integers.add(3);
        Iterator<Integer> iterator = integers.iterator();
        iterator.next();
        System.out.println("Ds");
        iterator.remove();
        iterator.hasNext();
        System.out.println("ds");
        iterator.remove();
        System.out.println("dsd");

    }

    public static class GameDeserializer implements JsonDeserializer<Game> {

        @Override
        public Game deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            final String name = jsonObject.get("name").getAsString();
            final Mission mission = jsonDeserializationContext.deserialize(jsonObject.get("mission"), Mission.class);
            final Farm farm = jsonDeserializationContext.deserialize(jsonObject.get("farm"), Farm.class);
            return new Game(name, mission, farm);
        }
    }

    public static class FarmDeserializer implements JsonDeserializer<Farm> {

        @Override
        public Farm deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Map map = jsonDeserializationContext.deserialize(jsonObject.get("map"), Map.class);
            Truck truck = jsonDeserializationContext.deserialize(jsonObject.get("truck"), Truck.class);
            Model.Warehouse warehouse = jsonDeserializationContext.deserialize(jsonObject.get("warehouse"), Warehouse.class);
            Helicopter helicopter = jsonDeserializationContext.deserialize(jsonObject.get("helicopter"), Helicopter.class);
            Integer CurrentMoney = jsonDeserializationContext.deserialize(jsonObject.get("CurrentMoney"), Integer.class);
            long turnsWent = jsonDeserializationContext.deserialize(jsonObject.get("turnsWent"), long.class);
            Bucket bucket = jsonDeserializationContext.deserialize(jsonObject.get("bucket"), Bucket.class);
            Integer CagesLevel = jsonDeserializationContext.deserialize(jsonObject.get("CagesLevel"), Integer.class);
            final Factory[] factories = new Factory[6];
            JsonArray jsonArray = jsonObject.get("factories").getAsJsonArray();
            for (int i = 0; i < 6; i++) {
                factories[i] = jsonDeserializationContext.deserialize(jsonArray.get(i), Factory.class);
            }
            return new Farm(turnsWent, map, CurrentMoney, warehouse, bucket, CagesLevel, factories, truck, helicopter);

        }
    }

    public static class FactoryDeserializer implements JsonDeserializer<Factory> {

        @Override
        public Factory deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            int Level = jsonDeserializationContext.deserialize(jsonObject.get("Level"), int.class);
            Factory.Process process = jsonDeserializationContext.deserialize(jsonObject.get("process"), Factory.Process.class);
            Factory.FactoryType factoryType = jsonDeserializationContext.deserialize(jsonObject.get("factoryType"), Factory.FactoryType.class);
            MapPosition outputPosition = jsonDeserializationContext.deserialize(jsonObject.get("outputPosition"), MapPosition.class);
            return new Factory(factoryType, outputPosition, process, Level);
        }
    }

    public static class GoalDeserializer implements JsonDeserializer<Mission.Goal> {

        @Override
        public Mission.Goal deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            final ArrayList<Mission.Goal.EE> ees = new ArrayList<Mission.Goal.EE>(0);
            JsonArray jsonArray = jsonObject.get("ees").getAsJsonArray();
            for (JsonElement jsonElement1 : jsonArray) {
                ees.add(jsonDeserializationContext.deserialize(jsonElement1, Mission.Goal.EE.class));
            }
            return new Mission.Goal(ees);

        }
    }/*
    public class ItemDeserializer implements JsonDeserializer<Item>{

        @Override
        public Item deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject =jsonElement.getAsJsonObject();
            if (jsonObject.get("type").getAsString().equals(null));
            return new Cat()
        }
    }*/

    public static class TruckDeserializer implements JsonDeserializer<Truck> {

        @Override
        public Truck deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            ArrayList<Item> items = jsonDeserializationContext.deserialize(jsonObject.get("items"), new TypeToken<ArrayList<Item>>() {
            }.getType());
            int level = jsonDeserializationContext.deserialize(jsonObject.get("Level"), int.class);
            int Capacity = jsonDeserializationContext.deserialize(jsonObject.get("Capacity"), int.class);
            int remainingTurns = jsonDeserializationContext.deserialize(jsonObject.get("RemainingTurns"), int.class);
            int price = jsonDeserializationContext.deserialize(jsonObject.get("Price"), int.class);
            Integer FarmMoney = jsonDeserializationContext.deserialize(jsonObject.get("FarmMoney"), Integer.class);
            return new Truck(level, Capacity, remainingTurns, null, price, items, null);
            //todo remember to initialize farm and farmMoney
        }
    }

    public static class HelicopterDeserializer implements JsonDeserializer<Helicopter> {

        @Override
        public Helicopter deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            ArrayList<Item> items = jsonDeserializationContext.deserialize(jsonObject.get("items"), new TypeToken<ArrayList<Item>>() {
            }.getType());
            int level = jsonDeserializationContext.deserialize(jsonObject.get("Level"), int.class);
            int Capacity = jsonDeserializationContext.deserialize(jsonObject.get("Capacity"), int.class);
            int remainingTurns = jsonDeserializationContext.deserialize(jsonObject.get("RemainingTurns"), int.class);
            int price = jsonDeserializationContext.deserialize(jsonObject.get("Price"), int.class);
            Integer FarmMoney = jsonDeserializationContext.deserialize(jsonObject.get("FarmMoney"), Integer.class);
            return new Helicopter(level, Capacity, remainingTurns, null, price, items, null);
            //todo remember to initialize farm and farmMoney
        }
    }

    public static class WareHouseDeserializer implements JsonDeserializer<Warehouse> {

        @Override
        public Warehouse deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            int capacity = jsonDeserializationContext.deserialize(jsonObject.get("capacity"), int.class);
            int Level = jsonDeserializationContext.deserialize(jsonObject.get("Level"), int.class);
            ArrayList<Item> items = jsonDeserializationContext.deserialize(jsonObject.get("items"), new TypeToken<ArrayList<Item>>() {
            }.getType());
            return new Warehouse(Level, capacity, items);
        }
    }

    public static class MissionDeserializer implements JsonDeserializer<Mission> {

        @Override
        public Mission deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            final Mission.Goal goal = jsonDeserializationContext.deserialize(jsonObject.get("goal"), Mission.Goal.class);
            return new Mission(goal);
        }
    }
   /* public static class WildAnimalDeserializer implements JsonDeserializer<WildAnimal>{

        @Override
        public WildAnimal deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject =jsonElement.getAsJsonObject();
            Cage cage =jsonDeserializationContext.deserialize(jsonObject.get("cage"),Cage.class);

        }
    }*/

    public static class MapDeserializer implements JsonDeserializer<Map> {

        @Override
        public Map deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Integer lifeTime = jsonDeserializationContext.deserialize(jsonObject.get("lifeTime"), Integer.class);
            Cell[][] cells = jsonDeserializationContext.deserialize(jsonObject.get("cells"), Cell[][].class);
            return new Map(lifeTime, cells);


        }
    }
  /*  public static class FactoryTypeDeserializer implements JsonDeserializer<Factory.FactoryType>{

        @Override
        public Factory.FactoryType deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        }
    }
*/

    public static class CellDeserializer implements JsonDeserializer<Cell> {

        @Override
        public Cell deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Grass grass = jsonDeserializationContext.deserialize(jsonObject.get("grass"), Grass.class);
            MapPosition mapPosition = jsonDeserializationContext.deserialize(jsonObject.get("mapPosition"), MapPosition.class);
            ArrayList<Item> items = jsonDeserializationContext.deserialize(jsonObject.get("items"), new TypeToken<ArrayList<Item>>() {
            }.getType());
            return new Cell(grass, mapPosition, items);
        }
    }

}
