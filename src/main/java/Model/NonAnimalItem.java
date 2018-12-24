package Model;

import Model.Positions.MapPosition;
import Model.Positions.NonMapPosition;
import View.NonAnimalItemView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Scanner;

public class NonAnimalItem extends Model.Item {
    public static final String NonAnimalItemsConfigFilePath = "NonAnimalItemsConfigFile.json";
    private static final int MaxLifeTimeInMap = 10;  //todo
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
            Gson gson = new Gson();
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

    public void getCollected() {
        this.setPosition(NonMapPosition.getInstance());
        //todo Now I am here

    }

    @Override
    public void anihilate() {
        this.map.getCell((MapPosition) this.getPosition()).items.remove(this);
    }

    @Override
    public boolean turn() {
        super.turn();
        if (Position instanceof MapPosition && lifeTime > MaxLifeTimeInMap) {
            this.anihilate();
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
