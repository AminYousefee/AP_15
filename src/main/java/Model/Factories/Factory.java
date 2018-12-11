package Model.Factories;

public class Factory {


    public boolean turn(){
        return true;
    }

    private int remainedItems;



    public boolean process() {

        return true;

    }

    private boolean isFinished() {
        return false;
    }

    public int getRemainedItems() {
        return remainedItems;
    }

    public void setRemainedItems(int remainedItems) {
        this.remainedItems = remainedItems;
    }
}
