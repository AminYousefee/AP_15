package Model.Animals;

import Model.Cell;
import Model.Item;
import Model.Map;
import Model.Positions.MapPosition;
import Model.Positions.NonMapPosition;
import controller.InputProcessor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;

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

    public void Print() {
        System.out.println(itemInfo.getItemName() + ":");
        System.out.println("Fullness  = " + fullness);
    }

    @Override
    public boolean move(ListIterator<Item> itemIterator) {
        if (getPosition() instanceof NonMapPosition) {
            return false;
            //it doesn't move in this way
        }
        Cell cell;
        if (fullness < 3.0 / 10 * ((ProductiveAnimal.ProductiveAnimalInfo) this.itemInfo).HungryValue && ((cell = map.findNearestCellWithGrass(map.getCellByPosition((MapPosition) this.getPosition()))) != null)) {


            if (cell != null) {
                MapPosition goalItemPosition = cell.getMapPosition();
                MapPosition CurrentPosition = (MapPosition) this.getPosition();
                if (goalItemPosition.equals(CurrentPosition)) {
                    //nothing here
                    return false;
                } else {
                    double deltaX = goalItemPosition.getX() - ((MapPosition) this.getPosition()).getX();
                    double deltaY = goalItemPosition.getY() - ((MapPosition) this.getPosition()).getY();
                    if (((deltaX * deltaX) + (deltaY * deltaY)) < (this.getSpeed() * this.getSpeed())) {
                        return moveToPosition(goalItemPosition,itemIterator);

                    } else {
                        double amplifier = Math.sqrt((this.getSpeed() * this.getSpeed()) / ((deltaX * deltaX) + (deltaY * deltaY)));
                        int x = (int) (amplifier * deltaX + ((MapPosition) getPosition()).getX());
                        int y = (int) (amplifier * deltaY + ((MapPosition) getPosition()).getY());
                        MapPosition position = new MapPosition(x, y);
                        return moveToPosition(position,itemIterator);
                    }
                }

            }
        } else {

            return super.move(itemIterator);

        }
        return false;
        //maybe there is some bug behind I thought it should have worked well

    }

    protected void addFullness() {
        fullness += ((ProductiveAnimalInfo) this.itemInfo).OneTimeBite;
    }


    public boolean eat(Iterator<Item> itemIterator) {
        if (fullness <= 0) {
            itemIterator.remove();
            System.out.println(itemInfo.getItemName()+" in cell " + ((MapPosition) this.getPosition()).getX() + " " + ((MapPosition) this.getPosition()).getY() + " died.");

            return true;//well this bad smell I should do it
        }
        if (getPosition() instanceof NonMapPosition) {
            return false;
            //do nothing in this case
        }

        Cell cell = map.getCellByPosition((MapPosition) getPosition());
        if (cell.getGrass().getNum() > 0 && fullness < 7.0 / 10 * ((ProductiveAnimal.ProductiveAnimalInfo) this.itemInfo).HungryValue) {
            cell.getGrass().getEaten();
            this.addFullness();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean turn(ListIterator<Item> itemIterator) {
        if (map.getCellByPosition((MapPosition) this.Position).getItems().contains(this)) {


            this.produce();
            this.fullness -= ((ProductiveAnimalInfo) this.itemInfo).HungrySpeed;

            if (!eat(itemIterator)) {
                move(itemIterator);
            }
            return false;
        }else {
            //it is dead so shouldn't do anything
            return false;
        }

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
