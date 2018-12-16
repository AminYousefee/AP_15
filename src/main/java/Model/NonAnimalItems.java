package Model;

import View.NonAnimalItemView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class NonAnimalItems extends Model.Item {
    public static final String NonAnimalItemsConfigFilePath = "./NonAnimalItemsConfigFile.json";
    public static ArrayList<NonAnimalItemInfo> nonAnimalItemInfos = new ArrayList<>(0);


    //Finished
    public NonAnimalItems(NonAnimalItemInfo info) {
        itemInfo = info;
    }

    //Finished
    public static Item getInstance(String name) {
        for (NonAnimalItemInfo info : nonAnimalItemInfos) {
            if (info.ItemName.equals(name)) {
                return new NonAnimalItems(info);

            }
        }


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

            FileReader fileReader = new FileReader(NonAnimalItemsConfigFilePath);
            Scanner scanner = new Scanner(fileReader);

        } catch (FileNotFoundException e) {
            NonAnimalItemView.FileNotFoundException();
        } catch (JsonSyntaxException) {
            NonAnimalItemView.JsonSyntaxExceptionOccured();
        }

    }

    public void getCollected() {

    }

    public static class ProductType {

    }

    public static class NonAnimalItemInfo extends ItemInfo {


    }
}
