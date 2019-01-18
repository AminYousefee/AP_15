package Model.GameMenu;

import Model.Farm;
import Model.GameMenu.Missions.Mission;
import controller.InputProcessor;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    public static ArrayList<Game> loadedGames = new ArrayList<>(0);
    String name;
    private Mission mission;
    private Farm farm;


    public Game(String name, Mission mission, Farm farm) {
        this.name = name;
        this.mission = mission;
        this.farm = farm;
    }

    public static Game findLoadedGame(String farmName) {
        for (Game game : loadedGames) {
            if (game.name.equalsIgnoreCase(farmName)) {
                return game;
            }
        }
        return null;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public void printInfo() {
        System.out.println("Game = "+name );
        System.out.println("Money = " + this.getFarm().getCurrentMoney());
        System.out.println("Time Gone = " + this.getFarm().getTurnsWent());
        System.out.println(mission.goal);


    }

    public void turn() {
        System.out.println("Turn = " +this.getFarm().getTurnsWent());
        if (mission.isSatisfied()) {
            System.out.println("Mission Satisfied");
            System.out.println("Do you Want To Continue?");
            System.out.println("Enter y/n");
            Scanner scanner = new Scanner(System.in);
            String string = scanner.nextLine();
            if (string.equalsIgnoreCase("n")) {
                System.exit(0);
            } else {
                InputProcessor.game = null;
                InputProcessor.GameNotSpecifiedMode();
            }
        } else {
            farm.turn();
        }
    }


}
