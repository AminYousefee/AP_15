package Model.Positions;

public class NonMapPosition extends Position {
    public static NonMapPosition ourInstance = new NonMapPosition();
    public static NonMapPosition getInstance() {
        return ourInstance;
    }
}
