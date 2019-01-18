package Model.Positions;

public class NonMapPosition extends Position {
    public static NonMapPosition ourInstance = new NonMapPosition();
    public static NonMapPosition getInstance() {
        return ourInstance;
    }

    private NonMapPosition() {
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NonMapPosition)){
            return false;
        }else {
            return true;
        }
    }
}
