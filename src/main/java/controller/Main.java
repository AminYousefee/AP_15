package controller;

import Model.*;
import Model.Factories.Factory;
import Model.GameMenu.Game;
import Model.GameMenu.Missions.Mission;
import Model.Map;
import Model.Positions.MapPosition;
import Model.Warehouse;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Main extends Application {
    public static AnchorPane pane;
    public static HashMap<String, Image> loadedImages;
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
    public static Stage stage;

    static {
        File currentDir = new File("./static");
        generateImages(currentDir);
    }

    public static void main(String[] args) {
        launch(args);

    }

    static Scene getMainMenuScene(int width, int height) {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(20, 20, 20, 20));

        Button btn1 = new Button("Single Game");
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            boolean flag;
            Stage stage;

            @Override
            public void handle(ActionEvent actionEvent) {
                if (flag == false) {
                    stage = new Stage();
                    GridPane gridPane = new GridPane();
                    gridPane.add(new Label("Input a number from 0 to 100"), 0, 0);
                    TextField textField = new TextField();
                    textField.setOnAction(actionEvent1 -> {
                        try {
                            int num = Integer.parseInt(textField.getCharacters().toString());
                            if (num >= 0 && num <= 100) {
                                stage.close();
                                flag = false;
                                singleGameStarted(num);
                            } else {
                                textField.setText("");
                            }
                        }catch (Exception e){
                            textField.setText("");
                        }
                    });
                    gridPane.add(textField,0,1);
                    flag = true;
                    stage.setScene(new Scene(gridPane));
                    stage.show();
                    stage.setOnCloseRequest(windowEvent -> flag = false);

                } else {

                }
            }
        });
        pane.add(btn1, 0, 1);

        Button btn2 = new Button("Multiplayer");
        btn2.setOnAction(actionEvent -> {
            multiplayerGame();
        });
        pane.add(btn2, 1, 1);

        Button btn3 = new Button("Exit");
        btn3.setOnAction(actionEvent -> {
            System.exit(0);
        });
        pane.add(btn3, 2, 1);

        String backpath = "./static/back.png";
        FileInputStream input = null;
        try {
            input = new FileInputStream(backpath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

    private static void multiplayerGame() {
        GridPane gridPane = new GridPane();
        Button hostButton = new Button("Host");
        Button clientButton = new Button("Client");
        Button backButton = new Button("Back");
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.setScene(getMainMenuScene(600, 400));
            }
        });


        gridPane.add(backButton, 0, 0);
        gridPane.add(hostButton, 0, 1);
        gridPane.add(clientButton, 0, 2);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        String backpath = "./static/back.png";
        FileInputStream input = null;
        try {
            input = new FileInputStream(backpath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(input);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);

        gridPane.setBackground(background);


        Scene scene = new Scene(gridPane, 600, 400);
        stage.setScene(scene);


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


*/

        //test 1

/*InputProcessor.game.getFarm().getMap().getCellByPosition(new MapPosition(2,2)).addItem(WildAnimal.getInstance("Lion"));
        InputProcessor.game.getFarm().getMap().getCellByPosition(new MapPosition(2,2)).addItem(WildAnimal.getInstance("bear"));
        InputProcessor.game.getFarm().getMap().getCellByPosition(new MapPosition(2,2)).addItem(ProductiveAnimal.getInstance("Turkey"));
        //game.getFarm().
        game.getFarm().getMap().getCellByPosition(new MapPosition(2,2)).turn();

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

    public static void continueSingleGame() {

    }

    static {
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
    }

    public void singlePlayerGame() {


        InputProcessor.GameNotSpecifiedMode();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String string = scanner.nextLine();
            InputProcessor.process(string);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setTitle("Farm Frenzy");
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 25, 25, 25));
        Button btn = new Button("PLAY ME!");
        pane.add(btn, 0, 0);


        String path = "./static/back.png";
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
        //Main.gridPane = pane;


        Scene scene = new Scene(pane, 600, 400);
        scene = getMainMenuScene(600, 400);
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
        String path = "./static/back.png";
        FileInputStream input = null;
        try {
            input = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(input);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);


        pane.setBackground(background);

        for (int i = 0; i < Map.Num_Of_CELLS_IN_ROW; i++) {
            for (int j = 0; j < Map.Num_Of_CELLS_IN_COLOUM; j++) {
                InputProcessor.game.getFarm().getMap().getCell(new MapPosition(i, j)).setGridPane(gridPane);
            }
        }


    }



    public static void singleGameStarted(int num){
        InputProcessor.process("run empty");
        InputProcessor.setSpeed(num);
        AnchorPane pane = new AnchorPane();
        Main.pane  =pane;
        //pane.setPadding(new Insets(25, 25, 25, 25));
        String path = new String("./static/back.png");
        FileInputStream input = null;
        try {
            input = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(input);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        pane.setBackground(background);



        pane.getChildren().add(new Button("fdsd"));
        InputProcessor.game.show();
        /*Truck.show();
        Helicopter.show();
        Warehouse.show();*/
        Scene scene= new Scene(pane,800,600);
        stage.setScene(scene);
        new Thread(() -> {
            if (System.currentTimeMillis()%(InputProcessor.getSpeed()*10)==0){
                InputProcessor.process("turn 1");
            }
        });
        /*stage.show();*/




    }







/*
    public static stopGame(){
        for (Node node :pane.getChildren() ) {
            Timeline timeline;
            timeline.stop();
            Animation animation

        }
        pane.getChildren()
    }*/

}


