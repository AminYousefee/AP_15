package Model.Factories;

import Model.Item;

public class Process {
    Item inputItems;
    Item outputItems;
    int remainedNeededTerms;


    public boolean turn() {
        return false;
    }


    public Item getInputItems() {
        return inputItems;
    }

    public void setInputItems(Item inputItems) {
        this.inputItems = inputItems;
    }

    public Item getOutputItems() {
        return outputItems;
    }

    public void setOutputItems(Item outputItems) {
        this.outputItems = outputItems;
    }

    public int getRemainedNeededTerms() {
        return remainedNeededTerms;
    }

    public void setRemainedNeededTerms(int remainedNeededTerms) {
        this.remainedNeededTerms = remainedNeededTerms;
    }
}
