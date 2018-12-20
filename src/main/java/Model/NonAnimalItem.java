package Model;

import Model.Positions.MapPosition;
import Model.Positions.NonMapPosition;
import View.NonAnimalItemView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class NonAnimalItem extends Model.Item {
    public static final String NonAnimalItemsConfigFilePath = "./NonAnimalItemsConfigFile.json";
    private static final int MaxLifeTimeInMap = 10;  //todo
    public static ArrayList<NonAnimalItemInfo> nonAnimalItemInfos = new ArrayList<>(0);
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
            Gson gson = new Gson();
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNext()) {
                String json = scanner.nextLine();
                nonAnimalItemInfos.add(gson.fromJson(json, NonAnimalItemInfo.class));

            }



        } catch (FileNotFoundException e) {
            NonAnimalItemView.FileNotFoundException();
        } catch (JsonSyntaxException e) {
            NonAnimalItemView.JsonSyntaxExceptionOccured();
        }

    }

    public void getCollected() {
        this.setPosition(NonMapPosition.getInstance());
        //todo Now I am here

    }

    @Override
    public void anihilate() {
        this.map.getCell((MapPosition) this.getPosition()).items.remove(this);
    }


    public static class NonAnimalItemInfo extends ItemInfo {


        public NonAnimalItemInfo(String name, int volume, int price) {
            super(name, volume, price);
        }
    }


    @Override
    public void turn() {
        super.turn();
        if (Position instanceof MapPosition && lifeTime>MaxLifeTimeInMap){
            this.anihilate();
        }
    }





}
