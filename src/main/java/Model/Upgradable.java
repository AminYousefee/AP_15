package Model;

public interface Upgradable {
    /*
    any thing extending this should have field Level


     */


    boolean upgrade(Integer CurrentMoney);

    int getUpgradeCost();

}
