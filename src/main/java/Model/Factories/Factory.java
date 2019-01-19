package Model.Factories;

import Model.Farm;
import Model.Item;
import Model.Positions.MapPosition;
import Model.Upgradable;
import Model.Warehouse;
import View.Factories.FactoryView;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import controller.InputProcessor;
import controller.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Factory implements Upgradable {
    public static final String FactoriesConfigFilePath = "FactoriesConfigFile.json";
    public static ArrayList<FactoryType> factoryTypeArrayList = new ArrayList<>(0);

    static {
        File file = new File(FactoriesConfigFilePath);
        try {
            FileReader fileReader = new FileReader(FactoriesConfigFilePath);
            StringBuilder stringBuilder = new StringBuilder();
            Scanner scanner = new Scanner(fileReader);

            while (scanner.hasNext()) {
                stringBuilder.append(scanner.nextLine());
            }

            Gson gson = InputProcessor.gson;

            factoryTypeArrayList.addAll(gson.fromJson(stringBuilder.toString(), new TypeToken<ArrayList<FactoryType>>() {
            }.getType()));
            //factoryTypeArrayList = (ArrayList<FactoryType>) types;
            //System.out.println("DSasd");


        } catch (FileNotFoundException e) {
            FactoryView.permissionDeniedToReadFactoriesConfigFile();
            //todo probably better to move it to view
        } /*catch (IOException e) {
            FactoryView.unableToMakeFactoriesConfigFile();
        }*/


    }

    FactoryType factoryType;
    MapPosition outputPosition;
    Process process;
    int Level;
    GridPane gridPane;
    ImageView imageView;

    public Factory(FactoryType factoryType, MapPosition outputPosition, Process process, int level) {
        this.factoryType = factoryType;
        this.outputPosition = outputPosition;
        this.process = process;
        Level = level;
        gridPane = Main.gridPane;
    }

    public static FactoryType findFactoryType(String name) {
        for (FactoryType factoryType : factoryTypeArrayList) {
            if (factoryType.getName().equalsIgnoreCase(name)) {
                return factoryType;
            }
        }
        return null;
    }

    /*public static void controller.Main(String[] args) {

        try {
            FileReader fileReader = new FileReader(FactoriesConfigFilePath);
            Scanner scanner = new Scanner(fileReader);
            String Json = scanner.nextLine();
            Gson gson = new Gson();
            Type collectionType = new TypeToken<HashSet<FactoryType>>() {
            }.getType();
            HashSet<FactoryType> II = (gson.fromJson(Json, collectionType));
            System.out.println(II);
            //fail();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //assertTrue(true);
        }


    }*/

    public FactoryType getFactoryType() {
        return factoryType;
    }

    public void setFactoryType(FactoryType factoryType) {
        this.factoryType = factoryType;
    }

    public boolean turn() {
        if (process != null) {
            if (process.getRemainedTurns() > 1) {
                process.reduceRemainedTurnsByOne();
            } else if (process.getRemainedTurns() == 1) {
                process.reduceRemainedTurnsByOne();
                finishProcess();
                return true;
            } else {
                // doing nothing now but nothing else
            }
        }
        return false;
    }

    private void finishProcess() {

        Item outputItem = Item.getInstance(factoryType.OutputItem.getItemName());
        outputItem.setPosition(outputPosition);


    }

    public boolean startProcess(Warehouse warehouse) {
        List<Item> items = warehouse.getItems();
        int min = 1000;
        if (process != null) {
            System.out.println("Factory Already doing some process");
            return false;
        }

        for (FactoryType.Isp isp : this.getFactoryType().InputItems) {

            int num = 0;
            for (Item item : items) {
                if (item.getItemInfo().equals(isp.itemInfo)) {
                    num += 1;
                }

            }

            min = Math.min(min, num / isp.weight);
        }
        if (min > this.factoryType.Ts.get(Level).ProductionNum) {
            min = this.factoryType.Ts.get(Level).ProductionNum;
        }
        if (min > 0) {
            process = new Process(getNeededTurns(), min);
            return true;
        } else {
            System.out.println("Not Any Ingredients");
            return false;
        }


    }

    private double getNeededTurns() {
        return this.factoryType.Ts.get(Level).MaxProductionTime;
    }

    private boolean isFinished() {
        return false;
    }

    @Override
    public boolean upgrade(Farm farm) {
        if (Level == this.factoryType.Ts.size() - 1) {
            System.out.println("Fully Upgraded");
            return false;
        } else {
            if (farm.getCurrentMoney() < getUpgradeCost()) {
                System.out.println("You don't have enough money");
                return false;
            } else {
                farm.setCurrentMoney(farm.getCurrentMoney() - getUpgradeCost());
                Level += 1;
                return true;
            }
        }

    }

    @Override
    public int getUpgradeCost() {
        return this.factoryType.Ts.get(Level).InGameCost;
    }

    public void print() {
        System.out.println(factoryType.name);
        System.out.println("Level = " + Level);
        //System.out.println(factoryType);
    }

    public void show() {
        if (imageView == null) {
            imageView = new ImageView(factoryType.image);
            imageView.setOnKeyPressed(keyEvent -> {
                this.startProcess(InputProcessor.game.getFarm().getWarehouse());
            });

        }
    }

    public static class Process {
        double remainedTurns;
        int numberOfInputs;
        int numberOfOutputs;


        public Process(double remainedTurns, int numberOfOutputs) {
            this.remainedTurns = remainedTurns;
            //if (numberOfOutputs<)
            this.numberOfOutputs = numberOfOutputs;
        }

        public void reduceRemainedTurnsByOne() {
            setRemainedTurns(getRemainedTurns() - 1);
        }

        public double getRemainedTurns() {
            return remainedTurns;
        }

        public void setRemainedTurns(double remainedTurns) {
            this.remainedTurns = remainedTurns;
        }

        public int getNumberOfInputs() {
            return numberOfInputs;
        }

        public void setNumberOfInputs(int numberOfInputs) {
            this.numberOfInputs = numberOfInputs;
        }

        public int getNumberOfOutputs() {
            return numberOfOutputs;
        }

        public void setNumberOfOutputs(int numberOfOutputs) {
            this.numberOfOutputs = numberOfOutputs;
        }
    }

    public static class FactoryTypeDeserializer implements JsonDeserializer<FactoryType> {


        @Override
        public FactoryType deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String name = jsonDeserializationContext.deserialize(jsonObject.get("name"), String.class);
            int numberOfInputItems = jsonDeserializationContext.deserialize(jsonObject.get("numberOfInputItems"), int.class);
            int numberOfOutputItems = jsonDeserializationContext.deserialize(jsonObject.get("numberOfOutputItems"), int.class);
            int ProcessTurns = jsonDeserializationContext.deserialize(jsonObject.get("ProcessTurns"), int.class);
            ArrayList<FactoryType.t> InputItems = jsonDeserializationContext.deserialize(jsonObject.get("InputItems"), new TypeToken<ArrayList<FactoryType.t>>() {
            }.getType());
            return new FactoryType(name, InputItems);
        }
    }

    public static class FactoryType {
        public Image image;
        String name;
        Item.ItemInfo OutputItem;
        ArrayList<Isp> InputItems;
        int numberOfInputItems;
        int numberOfOutputItems;
        int ProcessTurns;
        ArrayList<FactoryType.t> Ts = new ArrayList<>(0);

        public FactoryType(String name, ArrayList<t> ts) {
            this.name = name;
            Ts = ts;
        }

        public ArrayList<Isp> getInputItems() {
            return InputItems;
        }

        public void setInputItems(ArrayList<Isp> inputItems) {
            InputItems = inputItems;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Item.ItemInfo getOutputItem() {
            return OutputItem;
        }

        public void setOutputItem(Item.ItemInfo outputItem) {
            OutputItem = outputItem;
        }

        public int getNumberOfInputItems() {
            return numberOfInputItems;
        }

        public void setNumberOfInputItems(int numberOfInputItems) {
            this.numberOfInputItems = numberOfInputItems;
        }

        public int getNumberOfOutputItems() {
            return numberOfOutputItems;
        }

        public void setNumberOfOutputItems(int numberOfOutputItems) {
            this.numberOfOutputItems = numberOfOutputItems;
        }

        public int getProcessTurns() {
            return ProcessTurns;
        }

        public void setProcessTurns(int processTurns) {
            ProcessTurns = processTurns;
        }

        public static class t {
            int Level;
            int ProductionNum;
            double MaxProductionTime;
            int MaxLevelCost;
            int InGameCost;

            public t(int level, int productionNum, double maxProductionTime, int maxLevelCost, int inGameCost) {
                Level = level;
                ProductionNum = productionNum;
                MaxProductionTime = maxProductionTime;
                MaxLevelCost = maxLevelCost;
                InGameCost = inGameCost;
            }
        }

        public static class Isp {
            Integer weight;
            Item.ItemInfo itemInfo;

            public Isp(Integer weight, Item.ItemInfo itemInfo) {
                this.weight = weight;
                this.itemInfo = itemInfo;
            }
        }


        //todo Item input
    }
}

