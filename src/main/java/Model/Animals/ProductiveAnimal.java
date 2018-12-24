package Model.Animals;

import Model.Item;
import Model.Map;
import Model.Positions.MapPosition;
import controller.InputProcessor;

import java.util.HashSet;

public class ProductiveAnimal extends NonWildAnimal {
    public static final String ProductiveAnimalConfigFilePath = "./ProductiveAnimalConfigFile.json";
    public static HashSet<ProductiveAnimalInfo> productiveAnimalInfos = new HashSet<>(0);

    static {
        productiveAnimalInfos.add(new ProductiveAnimalInfo("Turkey", -1, 100, 50, 50, 20, 110, 10, 2, 3, 50));
        productiveAnimalInfos.add(new ProductiveAnimalInfo("Sheep", -1, 1000, 500, 55, 20, 110, 10, 2, 9, 150));
        productiveAnimalInfos.add(new ProductiveAnimalInfo("Cow", -1, 10000, 5000, 40, 20, 80, 10, 2, 18, 300));

    }

  /*  static {
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
    }*/

    public ProductiveAnimal(ProductiveAnimalInfo animalInfo, Map map) {
        super(animalInfo, map);
        fullness = ((ProductiveAnimal.ProductiveAnimalInfo) itemInfo).HungryValue;
    }

    public static ProductiveAnimal getInstance(String name) {
        for (ProductiveAnimalInfo productiveAnimalInfo : productiveAnimalInfos) {
            if (productiveAnimalInfo.getItemName().equalsIgnoreCase(name)) {
                return new ProductiveAnimal(productiveAnimalInfo, InputProcessor.game.getFarm().getMap());
            }
        }
        return null;

    }


    public boolean produce() {
        ProductiveAnimalInfo productiveAnimalInfo = (ProductiveAnimalInfo) this.itemInfo;
        int ProductionTime = productiveAnimalInfo.getProductionPeriod();
        if (getLifeTime() % ProductionTime == ProductionTime - 1) {
            map.getCellByPosition((MapPosition) this.getPosition()).addItem(Item.getInstance(productiveAnimalInfo.outputItem));
            return true;

        }
        return false;
    }


    public static class ProductiveAnimalInfo extends NonWildAnimalInfo {
        int ProductionPeriod;
        int HungryMovingSpeed;
        int OneTimeBite;
        int WaitTime;
        int HungrySpeed;
        int HungryValue;

        String outputItem;

        public ProductiveAnimalInfo(String itemName, int depotSize, int buyCost, int SaleCost, int speed, int productionPeriod, int hungryMovingSpeed, int oneTimeBite, int waitTime, int hungrySpeed, int hungryValue) {
            super(itemName, depotSize, buyCost, SaleCost, speed);
            ProductionPeriod = productionPeriod;
            HungryMovingSpeed = hungryMovingSpeed;
            OneTimeBite = oneTimeBite;
            WaitTime = waitTime;
            HungrySpeed = hungrySpeed;
            HungryValue = hungryValue;
        }

        public int getProductionPeriod() {
            return ProductionPeriod;
        }

        public void setProductionPeriod(int productionPeriod) {
            ProductionPeriod = productionPeriod;
        }
    }


}
