package Model.Animals;

import View.AnimalView.AnimalViewer;
import View.AnimalView.ProductiveAnimalViewer;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public abstract class ProductiveAnimal extends NonWildAnimal {
    public static final String ProductiveAnimalConfigFilePath = "./ProductiveAnimalConfigFile.json";
    public static HashSet<ProductiveAnimalInfo> productiveAnimalInfos = new HashSet<>(0);
    private static HashMap<Integer, AnimalInfo> PossibleProducts;

    static {
        try {
            Gson gson = new Gson();
            FileReader fileReader = new FileReader(ProductiveAnimalConfigFilePath);
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNext()) {
                String string = scanner.nextLine();
                productiveAnimalInfos.add(gson.fromJson(string, ProductiveAnimalInfo.class));
            }

        } catch (FileNotFoundException e) {
            ProductiveAnimalViewer.FileNotFoundException();
        } catch (JsonSyntaxException e) {
            ProductiveAnimalViewer.JsonSyntaxException();
        }
    }

    public static AnimalInfo findAnimalType(String name) {
        for (Map.Entry<Integer, AnimalInfo> entry : PossibleProducts.entrySet()) {
            if (entry.getValue().getName().equals(name)) {
                return entry.getValue();
            }
        }
        return null;

    }

    public abstract boolean eat();

    public abstract boolean produce();

    public abstract boolean die();

    public static class ProductiveAnimalInfo extends NonWildAnimalInfo {

        public ProductiveAnimalInfo(String name, int Volume, int price) {
            super(name, Volume, price);
        }
    }


}
