package Model.Animals;

import Model.Cell;
import Model.Item;
import Model.Positions.Position;
import Model.Upgradable;
import java.util.Random;

public abstract class Animal extends Item implements Upgradable {

    public static final AnimalInfo Cat_Info;
    public static final AnimalInfo Dog_Info;
    private Cell cell;

    static {
        Cat_Info = new AnimalInfo();
        //todo make these better
        Dog_Info = new AnimalInfo();
    }

    private int fullness;
    private int level;

    public static AnimalInfo findAnimalType(String name) {
        if (name.equalsIgnoreCase("cat")) {
            return Cat_Info;
        } else if (name.equalsIgnoreCase("dog")) {
            return Dog_Info;
        } else {
            return ProductiveAnimal.findAnimalType(name);
        }


    }
    private void moveVertical(boolean up) {
        int y = this.cell.getPosition().getY();
        int x = this.cell.getPosition().getX();
        if (up) {
            this.cell.setPosition(new Position(x, y + 1));
        } else {
            this.cell.setPosition(new Position(x, y - 1));
        }
    }
    private void moveHorizontal(boolean right){
        int y = this.cell.getPosition().getY();
        int x = this.cell.getPosition().getX();
        if (right) {
            this.cell.setPosition(new Position(x + 1, y));
        } else {
            this.cell.setPosition(new Position(x - 1, y));
        }
    }

    private void goTo(int i, int j) {
        int horizontal = j - this.cell.getPosition().getX();
        int vertical = i - this.cell.getPosition().getY();
        boolean up, right;
        if (horizontal > 0) {
            right = true;
        }else {
            right = false;
        }
        for (int h = 0; h < Math.abs(horizontal); h++) {
            moveHorizontal(right);
        }
        if (vertical > 0) {
            up = true;
        }else{
            up = false;
        }
        for (int k = 0; k < Math.abs(vertical); k++) {
            moveVertical(up);
        }
    }

    private int[] nearestGrass() {
        int myX = this.cell.getPosition().getX();
        int myY = this.cell.getPosition().getY();

    }

    public void move(){
        if (this.fullness < 30) {
            int[] target = this.nearestGrass();
            int xTarget = target[0];
            int yTarget = target[1];
            this.goTo(xTarget,yTarget);
        } else {
            Random random = new Random();
            int direct = random.nextInt(5);
            switch (direct) {
                case 1:
                    this.moveVertical(true);
                    break;
                case 2:
                    this.moveVertical(false);
                    break;
                case 3:
                    this.moveHorizontal(true);
                    break;
                case 4:
                    this.moveHorizontal(false);
            }
        }
    }



    private boolean getSpeed() {
        return false;

    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    private boolean eat() {
        return false;
    }

    public int getFullness() {
        return fullness;
    }

    public void setFullness(int fullness) {
        this.fullness = fullness;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public static class AnimalInfo {
        int id;
        String name;
        int ProductId;


        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getProductId() {
            return ProductId;
        }
    }


}
