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
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main extends Application {
    public static GridPane gridPane;
    public static HashMap<String, Image> loadedImages;

    static {
        File currentDir = new File("./static");
        generateImages(currentDir);
    }

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

    static Scene getMainMenuScene(int width, int height) {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(20, 20, 20, 20));

        Button btn1 = new Button("Single Game");
        btn1.setOnAction(actionEvent -> {
            //todo
        });
        pane.add(btn1, 0, 1);

        Button btn2 = new Button("Multiplayer");
        btn2.setOnAction(actionEvent -> {
            //todo
        });
        pane.add(btn2, 1, 1);

        Button btn3 = new Button("Exit");
        btn3.setOnAction(actionEvent -> {
            //todo
        });
        pane.add(btn3, 2, 1);

        String backpath = new String("./static/back.png")
        FileInputStream input = new FileInputStream(backpath);
        Image image = new Image(input);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);

        pane.setBackground(background);

        Scene scene = new Scene(pane, width, height);
        return scene;
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

    private static void generateImages(File dir) {

        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                generateImages(file);
            } else {

            }
        }
    }


    /*static Scene singleGameScene(int width, int height) {
        GridPane pane = new GridPane();
        String backpath = new String("./static/back.png")
        FileInputStream input = new FileInputStream(backpath);
        Image image = new Image(input);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);




    }*/

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


        String path = new String("./static/back.png");
        FileInputStream input = new FileInputStream(path);
        Image image = new Image(input);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);


        pane.setBackground(background);

        btn.setOnAction(actionEvent -> {
            gameStarts(stage);
        });
        Main.gridPane = pane;


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

}


