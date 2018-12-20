package Model.Animals;

import Model.Item;
import Model.Positions.MapPosition;
import View.AnimalView.ProductiveAnimalViewer;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;

public class ProductiveAnimal extends NonWildAnimal {
    public static final String ProductiveAnimalConfigFilePath = "./ProductiveAnimalConfigFile.json";
    public static HashSet<ProductiveAnimalInfo> productiveAnimalInfos = new HashSet<>(0);

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

    public ProductiveAnimal(ProductiveAnimalInfo animalInfo) {
        super(animalInfo);
    }

    public static ProductiveAnimal getInstance(String name) {
        for (ProductiveAnimalInfo productiveAnimalInfo : productiveAnimalInfos) {
            if (productiveAnimalInfo.getItemName().equalsIgnoreCase(name)) {
                return new ProductiveAnimal(productiveAnimalInfo);
            }
        }
        return null;

    }


    public boolean produce() {
        ProductiveAnimalInfo productiveAnimalInfo = (ProductiveAnimalInfo) this.itemInfo;
        int ProductionTime = productiveAnimalInfo.getProductionTime();
        if (getLifeTime() % ProductionTime == ProductionTime - 1) {
            map.getCellByPosition((MapPosition) this.getPosition()).addItem(Item.getInstance(productiveAnimalInfo.outputItem));
            return true;

        }
        return false;
    }


    public static class ProductiveAnimalInfo extends NonWildAnimalInfo {
        int ProductionTime;

        String outputItem;

        public ProductiveAnimalInfo(String name, int Volume, int price) {
            super(name, Volume, price);
        }

        public int getProductionTime() {
            return ProductionTime;
        }

        public void setProductionTime(int productionTime) {
            ProductionTime = productionTime;
        }
    }


}
