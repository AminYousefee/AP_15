package Model.Factories;

import Model.Item;

public class Process {
    private Item inputItems;
    private Item outputItems;
    private int remainedNeededTerms;


    public boolean turn() {
        setRemainedNeededTerms(getRemainedNeededTerms()-1);

        return isFinished();
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


    private boolean isFinished(){
        return false;
    }



}
