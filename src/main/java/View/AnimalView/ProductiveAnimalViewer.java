package View.AnimalView;

public abstract class ProductiveAnimalViewer extends FarmAnimalViewer {
    public abstract void produceViewer();



    public static void FileNotFoundException() {
        System.out.println("FileNotFoundException / Productive Animal");
    }

    public static void JsonSyntaxException() {
        System.out.println("JsonSyntaxException / Productive Animal");
    }


}
