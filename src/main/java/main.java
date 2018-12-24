import Model.Farm;
import Model.GameMenu.Game;
import Model.GameMenu.Missions.Mission;
import Model.Item;
import controller.InputProcessor;

import java.util.ArrayList;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        ArrayList<Mission.Goal.EE> ees = new ArrayList<>(0);
        ees.add(new Mission.Goal.EE(Item.getInstance("Egg").getItemInfo(), 5));
        ees.add(new Mission.Goal.EE(Item.getInstance("Horn").getItemInfo(), 10));
        Mission mission1 = new Mission(new Mission.Goal(ees));
        Farm farm1 = new Farm();
        Game game = new Game("Empty", mission1, farm1);
        Game.loadedGames.add(game);

        InputProcessor.GameNotSpecifiedMode();
        Scanner scanner =new Scanner(System.in);
        while (true){
            String string = scanner.nextLine();
            InputProcessor.process(string);
        }
    }

}
