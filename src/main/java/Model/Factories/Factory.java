package Model.Factories;

import Model.Item;
import Model.Positions.Position;

public class Factory {
    Position outputPosition;
    FactoryType factoryType;
    Process process;

    public boolean turn() {
        if (process.getRemainedTurns() > 1) {
            process.reduceRemainedTurnsByOne();
        } else if (process.getRemainedTurns() == 1) {
            process.reduceRemainedTurnsByOne();
            finishProcess();
        } else {
            // doing nothing now but nothing else
        }


    }

    private void finishProcess() {
        Item outputItem = new


    }

    public boolean process() {
        return true;
    }

    private boolean isFinished() {
        return false;
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

    private class FactoryType {
        Item OutputItem;
        Item InputItem;
        int numberOfInputItems;
        int numberOfOutputItems;
        int ProcessTurns;

        public Item getOutputItem() {
            return OutputItem;
        }

        public void setOutputItem(Item outputItem) {
            OutputItem = outputItem;
        }

        public Item getInputItem() {
            return InputItem;
        }

        public void setInputItem(Item inputItem) {
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
}
