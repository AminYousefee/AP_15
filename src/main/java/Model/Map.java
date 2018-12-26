package Model;

import Model.Animals.Animal;
import Model.Animals.WildAnimal;
import Model.Positions.MapPosition;
import Model.Positions.Position;
import View.Farmys.MapView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
    public static final int Num_Of_CELLS_IN_ROW = 5;
    public static final int Num_Of_CELLS_IN_COLOUM = 5;
    Integer lifeTime = 0;
    Model.Cell[][] cells = new Model.Cell[Num_Of_CELLS_IN_COLOUM][Num_Of_CELLS_IN_ROW];

    public Map(Integer lifeTime, Cell[][] cells) {
        this.lifeTime = lifeTime;
        this.cells = cells;
    }

    public Map() {
        for (int i = 0; i < Num_Of_CELLS_IN_ROW; i++) {
            for (int j = 0; j < Num_Of_CELLS_IN_COLOUM; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public ArrayList<Cell> getAjacentCells(Cell cell) {
        return null;
    }

    public boolean cage(Position position) {
        return true;
    }

    public void turn() {

        for (Cell[] cell : cells) {
            for (Cell cell1 : cell) {
                cell1.turn();
            }
        }
        lifeTime++;
        if (lifeTime % 200 == 199) {
            Random random = new Random();
            if (random.nextBoolean()) {

                this.addItemInRandom(WildAnimal.getInstance("Lion"));
                this.addItemInRandom(WildAnimal.getInstance("Lion"));
            } else {
                this.addItemInRandom(WildAnimal.getInstance("Bear"));
                this.addItemInRandom(WildAnimal.getInstance("bear"));
            }

        }


    }

    public void printMap() {
        MapView.PrintMap(this.cells);

    }

    public Item getCatCollectableItem() {
        ArrayList<Item> collectable = new ArrayList<>(0);
        for (Cell cellColumn[] : cells) {
            for (Cell cell : cellColumn) {
                Item res = cell.getCatCollectableItem();
                if (res != null) {
                    collectable.add(res);
                }
            }
        }
        Random random = new Random();
        int wow = random.nextInt() % collectable.size();
        return collectable.get(wow);
    }

    public Cell getCell(MapPosition position) {
        int x = position.getX();
        int y = position.getY();
        return cells[x][y];
    }

    public void addAnimal(Animal animal) {
        Random random = new Random();
        int a = Math.abs(random.nextInt());
        int b = Math.abs(random.nextInt());
        a = a % Map.Num_Of_CELLS_IN_ROW;
        b = b % Num_Of_CELLS_IN_ROW;
        animal.setPosition(cells[a][b].getMapPosition());
        cells[a][b].addItem(animal);

    }

    public Cell getCellByPosition(MapPosition mapPosition) {
        if (mapPosition == null) {
            return null;
        }
        return cells[mapPosition.getX()][mapPosition.getY()];
    }

    public Cell findNearestCellWithGrass(Cell targetCell) {
        double minDistance = 1000;
        MapPosition minDistanceMapPosition = null;

        for (Cell[] cells : this.cells) {
            for (Cell cell : cells) {
                if (cell.getGrass().num >= 1) {
                    double x = targetCell.getMapPosition().getX() - cell.getMapPosition().getX();
                    double y = targetCell.getMapPosition().getY() - cell.getMapPosition().getY();
                    double distance = x * x + y * y;
                    if (distance < minDistance) {
                        minDistance = distance;
                        minDistanceMapPosition = cell.getMapPosition();
                    }
                }

            }
        }
        return this.getCellByPosition(minDistanceMapPosition);

    }

    public Cell findNearestCellWithFoodForWildAnimal(Cell targetCell) {
        double minDistance = 1000;
        MapPosition minDistanceMapPosition = null;

        for (Cell[] cells : this.cells) {
            for (Cell cell : cells) {
                if (cell.containsNonWildAnimalOrItem()) {
                    double x = targetCell.getMapPosition().getX() - cell.getMapPosition().getX();
                    double y = targetCell.getMapPosition().getY() - cell.getMapPosition().getY();
                    double distance = x * x + y * y;
                    if (distance < minDistance) {
                        minDistance = distance;
                        minDistanceMapPosition = cell.getMapPosition();
                    }
                }

            }
        }
        return this.getCellByPosition(minDistanceMapPosition);
    }

    public Cell getNearestCellWithWildAnimal(Cell targetCell) {
        double minDistance = 1000;
        MapPosition minDistanceMapPosition = null;

        for (Cell[] cells : this.cells) {
            for (Cell cell : cells) {
                if (cell.containsWildAniaml()) {
                    double x = targetCell.getMapPosition().getX() - cell.getMapPosition().getX();
                    double y = targetCell.getMapPosition().getY() - cell.getMapPosition().getY();
                    double distance = x * x + y * y;
                    if (distance < minDistance) {
                        minDistance = distance;
                        minDistanceMapPosition = cell.getMapPosition();
                    }
                }

            }
        }
        return getCellByPosition(minDistanceMapPosition);
    }

    public Item getNearestCatCollectableItem(Cell targetCell) {
        double minDistance = 1000;
        MapPosition minDistanceMapPosition = null;

        for (Cell[] cells : this.cells) {
            for (Cell cell : cells) {
                if (cell.getCatCollectableItem() != null) {
                    double x = targetCell.getMapPosition().getX() - cell.getMapPosition().getX();
                    double y = targetCell.getMapPosition().getY() - cell.getMapPosition().getY();
                    double distance = x * x + y * y;
                    if (distance < minDistance) {
                        minDistance = distance;
                        minDistanceMapPosition = cell.getMapPosition();
                    }
                }

            }
        }
        return this.getCellByPosition(minDistanceMapPosition).getCatCollectableItem();
    }

    public List<Item> getItemOfSpecifiedType(String itemName) {
        ArrayList<Item> res = new ArrayList<>(0);
        for (Cell[] cells1 : cells) {
            for (Cell cell : cells1) {
                for (Item item : cell.getItems()) {
                    if (item.getItemInfo().getItemName().equalsIgnoreCase(itemName)) {
                        res.add(item);
                    }
                }
            }
        }
        return res;
    }

    public void addItemInRandom(Item item) {
        Random random = new Random();
        int x = Math.abs(random.nextInt()) % Map.Num_Of_CELLS_IN_COLOUM;
        int y = Math.abs(random.nextInt()) % Map.Num_Of_CELLS_IN_ROW;
        MapPosition mapPosition = new MapPosition(x, y);
        item.setPosition(mapPosition);
        this.cells[x][y].addItem(item);
    }
}
