package View;

public  abstract class ItemView implements Viewable{
    String name;
    public abstract void  showCreation();
    public abstract void showAnihilation();
    public abstract void showExistense();
    public abstract void showToWarehouse();


}
