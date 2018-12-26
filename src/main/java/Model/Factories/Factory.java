package Model.Factories;

import Model.Item;
import Model.Positions.MapPosition;
import Model.Upgradable;
import Model.Warehouse;
import View.Factories.FactoryView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Factory implements Upgradable {
    public static final String FactoriesConfigFilePath = "FactoriesConfigFile.json";
    public static ArrayList<FactoryType> factoryTypeArrayList = new ArrayList<>(0);

    static {
        File file = new File(FactoriesConfigFilePath);
        try {
            boolean t = file.createNewFile();
            FileReader fileReader = new FileReader(file);
            //todo in this we add the files to the hashmap
            Scanner scanner = new Scanner(fileReader);

        } catch (FileNotFoundException e) {
            FactoryView.permissionDeniedToReadFactoriesConfigFile();
            //todo probably better to move it to view
        } catch (IOException e) {
            FactoryView.unableToMakeFactoriesConfigFile();
        }


    }

    public Factory(FactoryType factoryType, MapPosition outputPosition, Process process, int level) {
        this.factoryType = factoryType;
        this.outputPosition = outputPosition;
        this.process = process;
        Level = level;
    }

    FactoryType factoryType;
    MapPosition outputPosition;
    Process process;
    int Level;

    public static FactoryType findFactoryType(String name) {
        for (FactoryType factoryType : factoryTypeArrayList) {
            if (factoryType.getName().equalsIgnoreCase(name)) {
                return factoryType;
            }
        }
        return null;
    }

    /*public static void main(String[] args) {

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
        if (process.getRemainedTurns() > 1) {
            process.reduceRemainedTurnsByOne();
        } else if (process.getRemainedTurns() == 1) {
            process.reduceRemainedTurnsByOne();
            finishProcess();
            return true;
        } else {
            // doing nothing now but nothing else
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
        process = new Process(getNeededTurns(), min);
        return true;


    }

    private int getNeededTurns() {
        return 0;
    }

    private boolean isFinished() {
        return false;
    }

    @Override
    public boolean upgrade(Integer CurrentMoney) {
        return false;
    }

    @Override
    public int getUpgradeCost() {
        return 0;
    }

    public static class Process {
        int remainedTurns;
        int numberOfInputs;
        int numberOfOutputs;


        public Process(int remainedTurns, int numberOfOutputs) {
            this.remainedTurns = remainedTurns;
            this.numberOfOutputs = numberOfOutputs;
        }

        public void reduceRemainedTurnsByOne() {
            setRemainedTurns(getRemainedTurns() - 1);
        }

        public int getRemainedTurns() {
            return remainedTurns;
        }

        public void setRemainedTurns(int remainedTurns) {
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

    public static class FactoryType {
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

        static class Isp {
            Integer weight;
            Item.ItemInfo itemInfo;
        }


        //todo Item input
    }


}
