package Model;

import Model.Positions.MapPosition;
import Model.Positions.NonMapPosition;
import View.NonAnimalItemView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import controller.InputProcessor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;

public class NonAnimalItem extends Model.Item {
    public static final String NonAnimalItemsConfigFilePath = "NonAnimalItemsConfigFile.json";
    private static final int MaxLifeTimeInMap = 100;  //todo
    public static HashSet<NonAnimalItemInfo> nonAnimalItemInfos = new HashSet<>(0);

    static {
        UploadNonAnimalItemInfos();
    }


    //Finished
    public NonAnimalItem(NonAnimalItemInfo info) {
        itemInfo = info;
    }

    //Finished
    public static Item getInstance(String name) {
        for (NonAnimalItemInfo info : nonAnimalItemInfos) {
            if (info.ItemName.equals(name)) {
                return new NonAnimalItem(info);

            }
        }
        return null;


    }

    //Finished
    public static void UploadNonAnimalItemInfos() {
        try {
            FileReader fileReader = new FileReader(NonAnimalItemsConfigFilePath);
            Scanner scanner = new Scanner(fileReader);
            String Json = scanner.nextLine();
            Gson gson = InputProcessor.gson;
            Type collectionType = new TypeToken<HashSet<NonAnimalItemInfo>>() {
            }.getType();
            HashSet<NonAnimalItemInfo> II = (gson.fromJson(Json, collectionType));
            nonAnimalItemInfos = II;


        } catch (FileNotFoundException e) {
            NonAnimalItemView.FileNotFoundException();
        } catch (JsonSyntaxException e) {
            NonAnimalItemView.JsonSyntaxExceptionOccured();
        }

    }

    /*@Test
    public static void main(String[] args) {

        try {
            FileReader fileReader = new FileReader(NonAnimalItemsConfigFilePath);
            Scanner scanner = new Scanner(fileReader);
            String Json = scanner.nextLine();
            Gson gson = new Gson();
            Type collectionType = new TypeToken<HashSet<NonAnimalItemInfo>>() {
            }.getType();
            HashSet<NonAnimalItem> II = (gson.fromJson(Json, collectionType));
            System.out.println(II);
            //fail();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //assertTrue(true);
        }


    }*/
    @Override
    public void getCollected() {
        if (InputProcessor.game.getFarm().getWarehouse().getCapacity() > this.getItemInfo().getDepotSize()) {

            map.getCellByPosition((MapPosition) this.getPosition()).removeItem(this);
            this.setPosition(NonMapPosition.getInstance());
            InputProcessor.game.getFarm().getWarehouse().addItem(this);
        } else {
            System.out.println("Not Enough Space in WareHouse");
        }
        //this.setPosition(NonMapPosition.getInstance());
        //todo Now I am here

    }


    @Override
    public boolean turn(ListIterator<Item> itemIterator) {
        super.turn(itemIterator);
        if (Position instanceof MapPosition && lifeTime > MaxLifeTimeInMap) {
            itemIterator.remove();
        }
        return false;
    }

    public static class NonAnimalItemInfo extends ItemInfo {


        public NonAnimalItemInfo(String itemName, int depotSize, int buyCost, int SaleCost) {
            super(itemName, depotSize, buyCost, SaleCost);
        }


    }
/*
    public static void main(String[] args) {
        UploadNonAnimalItemInfos();
        System.out.println(nonAnimalItemInfos);
    }*/
}
