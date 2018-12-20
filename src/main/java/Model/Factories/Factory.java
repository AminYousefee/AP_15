package Model.Factories;

import Model.Item;
import Model.Positions.MapPosition;
import Model.Positions.Position;
import Model.NonAnimalItem;
import Model.Upgradable;
import View.Factories.FactoryView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Factory implements Upgradable {
    public static final String FactoriesConfigFilePath = "./FactoriesConfigFile.json";
    public static ArrayList<FactoryType> factoryTypeArrayList =new ArrayList<>(0);

    static {
        File file = new File(FactoriesConfigFilePath);
        try {
            boolean t = file.createNewFile();
            FileReader fileReader = new FileReader(file);
            //todo in this we add the files to the hashmap
            Scanner scanner =new Scanner(fileReader);

        } catch (FileNotFoundException e) {
            FactoryView.permissionDeniedToReadFactoriesConfigFile();
            //todo probably better to move it to view
        } catch (IOException e) {
            FactoryView.unableToMakeFactoriesConfigFile();
        }


    }

    public FactoryType getFactoryType() {
        return factoryType;
    }

    public void setFactoryType(FactoryType factoryType) {
        this.factoryType = factoryType;
    }

    FactoryType factoryType;
    MapPosition outputPosition;
    Process process;

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
        outputItem.setMapPosition(outputPosition);







    }

    public boolean process() {
        return true;
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


    private static class Process {
        int remainedTurns;
        int numberOfInputs;
        int numberOfOutputs;

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

    public class FactoryType {
        String name;
        Item.ItemInfo OutputItem;
        Item.ItemInfo InputItem;
        int numberOfInputItems;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        int numberOfOutputItems;
        int ProcessTurns;

        public Item.ItemInfo getOutputItem() {
            return OutputItem;
        }

        public void setOutputItem(Item.ItemInfo outputItem) {
            OutputItem = outputItem;
        }

        public Item.ItemInfo getInputItem() {
            return InputItem;
        }

        public void setInputItem(Item.ItemInfo inputItem) {
            InputItem = inputItem;
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








        //todo Item input 
    }



    public static FactoryType findFactoryType(String name){
        for (FactoryType factoryType:factoryTypeArrayList){
            if (factoryType.getName().equalsIgnoreCase(name)){
                return factoryType;
            }
        }
        return null;
    }
}
