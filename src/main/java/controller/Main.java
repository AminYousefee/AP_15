package controller;

import Model.Factories.Factory;
import Model.Farm;
import Model.GameMenu.Game;
import Model.GameMenu.Missions.Mission;
import Model.Item;
import Model.Map;
import Model.Positions.MapPosition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {
    public static GridPane gridPane;

    public static void main(String[] args) {
        launch(args);


        ArrayList<Mission.Goal.EE> ees = new ArrayList<>(0);
        ees.add(new Mission.Goal.EE(Item.getInstance("Egg").getItemInfo(), 5));
        ees.add(new Mission.Goal.EE(Item.getInstance("Horn").getItemInfo(), 10));
        Mission mission1 = new Mission(new Mission.Goal(ees));
        Farm farm1 = new Farm();
        farm1.factories[0] = new Factory(Factory.findFactoryType("Egg Powder Plant"), new MapPosition(0, 0), null, 0);
        farm1.factories[0].getFactoryType().setInputItems(new ArrayList<>(0));
        farm1.factories[0].getFactoryType().getInputItems().add(new Factory.FactoryType.Isp(1, Item.getInstance("Egg").getItemInfo()));
        Game game = new Game("Empty", mission1, farm1);
        Game.loadedGames.add(game);

        InputProcessor.GameNotSpecifiedMode();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String string = scanner.nextLine();
            InputProcessor.process(string);
        }

    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Farm Frenzy");
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 25, 25, 25));
        Button btn = new Button("PLAY ME!");
        pane.add(btn, 0, 0);
        btn.setOnAction(actionEvent -> {
            gameStarts(stage);
        });
        Scene scene = new Scene(pane, 600, 400);
        stage.setScene(scene);
        stage.show();
    }


    private void gameStarts(Stage mainStage) {
        GridPane gridPane = new GridPane();
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(0);
        pane.setVgap(0);
        pane.setPadding(new Insets(25, 25, 25, 25));
        for (int i = 0; i < Map.Num_Of_CELLS_IN_ROW; i++) {
            for (int j = 0; j < Map.Num_Of_CELLS_IN_COLOUM; j++) {
                InputProcessor.game.getFarm().getMap().getCell(new MapPosition(i, j)).setGridPane(gridPane);
            }
        }


    }


/*
    public static void controller.Main(String[] args) {
        ArrayList<Mission.Goal.EE> ees = new ArrayList<>(0);
        ees.add(new Mission.Goal.EE(Item.getInstance("Egg").getItemInfo(), 5));
        ees.add(new Mission.Goal.EE(Item.getInstance("Horn").getItemInfo(), 10));
        Mission mission1 = new Mission(new Mission.Goal(ees));
        Farm farm1 = new Farm();
        Game game = new Game("Empty", mission1, farm1);
        InputProcessor.game =game;



        //test 1
        */
/*InputProcessor.game.getFarm().getMap().getCellByPosition(new MapPosition(2,2)).addItem(WildAnimal.getInstance("Lion"));
        InputProcessor.game.getFarm().getMap().getCellByPosition(new MapPosition(2,2)).addItem(WildAnimal.getInstance("bear"));
        InputProcessor.game.getFarm().getMap().getCellByPosition(new MapPosition(2,2)).addItem(ProductiveAnimal.getInstance("Turkey"));
        //game.getFarm().
        game.getFarm().getMap().getCellByPosition(new MapPosition(2,2)).turn();

*//*








    }
*/


}
