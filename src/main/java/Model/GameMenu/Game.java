package Model.GameMenu;

import Model.Farm;
import Model.GameMenu.Missions.Mission;
import controller.InputProcessor;

import java.util.Scanner;

public class Game {


    private Mission mission;
    private Farm farm;



    public Farm getFarm() {
        return farm;
    }


    public void printInfo() {

    }

    public void turn() {
        if (mission.isSatisfied()){
            System.out.println("Mission Satisfied");
            System.out.println("Do you Want To Continue?");
            System.out.println("Enter y/n");
            Scanner scanner =new Scanner(System.in);
            String string = scanner.nextLine();
            if (string.equalsIgnoreCase("y")){
                System.exit(0);
            }else
            InputProcessor.GameNotSpecifiedMode();
        }
        else {
            farm.turn();
        }
    }

    private void startNewGame() {
        Scanner scanner =new Scanner(System.in);
        while (true){
            String str = scanner.nextLine();
            if (str.)
        }





        scanner.close();
    }
}
