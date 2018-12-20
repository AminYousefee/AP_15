import controller.InputProcessor;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        InputProcessor.GameNotSpecifiedMode();
        Scanner scanner =new Scanner(System.in);
        while (true){
            String string = scanner.nextLine();
            InputProcessor.process(string);
        }
    }

}
