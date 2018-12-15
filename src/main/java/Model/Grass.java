package Model;

public class Grass {
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        if (this.quantity <= 0) {
            this.quantity = 0;
        }
    }

    public Grass() {
        this.quantity = 100;
    }
}
