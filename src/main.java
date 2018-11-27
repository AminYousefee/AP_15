import controller.InputProcessor;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        String temp =scanner.nextLine();
        while (!temp.equals("end")){
            InputProcessor inputProcessor =new InputProcessor();
            inputProcessor.process(temp);
            temp =scanner.nextLine();

        }
    }

}
