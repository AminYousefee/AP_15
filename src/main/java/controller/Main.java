package controller;

import Model.Factories.Factory;
import Model.Farm;
import Model.GameMenu.Game;
import Model.GameMenu.Missions.Mission;
import Model.Item;
import Model.Map;
import Model.Positions.MapPosition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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
    static Scene stopedScent;

    static {
        File currentDir = new File("./static");
        generateImages(currentDir);
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
        farm1.factories[0].getFactoryType().setOutputItem(Item.getInstance("Horn").getItemInfo());
        Game game = new Game("Empty", mission1, farm1);
        game.getFarm().getWarehouse().addItem(Item.getInstance("Egg"));
        game.getFarm().getWarehouse().addItem(Item.getInstance("Horn"));
        game.getFarm().getWarehouse().addItem(Item.getInstance("Horn"));
        for (int i = 0; i < Map.Num_Of_CELLS_IN_COLOUM; i++) {
            for (int j = 0; j < Map.Num_Of_CELLS_IN_ROW; j++) {
                //game.getFarm().getMap().getCellByPosition(new MapPosition(i, j)).getGrass().setNum(100);
            }
        }
        Game.loadedGames.add(game);
    }

/*    public static stopGame() {
        stopedScent = stage.getScene();
        for (Node node : pane.getChildren()) {
            Timeline timeline;
            timeline.stop();
            Animation animation

        }
        pane.getChildren()
    }*/


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
                                InputProcessor.setSpeed(num);
                                //singleGameStarted(num);
                                InSingleMenu();

                            } else {
                                textField.setText("");
                            }
                        } catch (Exception e) {
                            textField.setText("");
                        }
                    });
                    gridPane.add(textField, 0, 1);
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
        hostButton.setOnAction(event -> {
            GridPane pane1 = new GridPane();
            TextField tx = new TextField();
            tx.setText("Port");
            pane1.add(tx,0,0);
            tx.setOnAction(event1 -> {
                Server server =Server.getInstance();
                GridPane gridPane1 = new GridPane();
                TextField username = new TextField("Username");
                TextField realname = new TextField("Real Name");
                Button enter = new Button("Enter");
                enter.setOnAction(event2 -> {
                    server.serverClient = new Server.ServerClient(username.getCharacters().toString(),realname.getCharacters().toString(),0,0);
                    server.clients.put(server.serverClient.getUsername(), server.serverClient);
                    server.port = Integer.parseInt(tx.getCharacters().toString());
                    new Thread(server).start();
                    server.show();

                });
                gridPane1.add(username,0,0);
                gridPane1.add(realname,0,1);
                gridPane1.add(enter,0,2);
                stage.setScene(new Scene(gridPane1));
            });
            stage.setScene(new Scene(pane1));
        });
        Button clientButton = new Button("Client");
        clientButton.setOnAction(event -> {
            Cli.ClientMenu();
        });
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

    private static void generateImages(File dir) {

        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                generateImages(file);
            } else {

            }
        }
    }

    public synchronized static void continueSingleGame() {
        InputProcessor.game.show();
        synchronized (InputProcessor.game.w) {
            InputProcessor.game.w.setFlag(false);
            InputProcessor.game.w.notify();
        }

    }

    public static void singleGameStarted(int num) {
        //InputProcessor.process("run empty");
        //InputProcessor.setSpeed(num);

        /*stage.show();*/


    }

    public static void InSingleMenu() {
        GridPane gridPane = new GridPane();
        Button loadButton = new Button("Load");
        Button saveButton = new Button("Save");
        Button LoadCustom = new Button("Load Custom");
        Button newGame = new Button("New Game");
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(loadButton, 0, 0);
        gridPane.add(saveButton, 0, 1);
        gridPane.add(LoadCustom, 0, 2);
        gridPane.add(newGame, 0, 3);
        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = new Stage();
                GridPane gridPane1 = new GridPane();
                TextField textField = new TextField();
                textField.setText("Address");
                gridPane1.add(textField, 0, 0);
                textField.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        InputProcessor.loadGame("load game " + textField.getCharacters().toString());
                        InputProcessor.game.show();
                    }
                });
                loadButton.setDisable(true);
                saveButton.setDisable(true);
                LoadCustom.setDisable(true);
                newGame.setDisable(true);
                stage.setScene(new Scene(gridPane1));
                stage.show();
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent windowEvent) {
                        loadButton.setDisable(false);
                        saveButton.setDisable(false);
                        LoadCustom.setDisable(false);
                        newGame.setDisable(false);
                    }
                });

            }
        });


        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = new Stage();
                GridPane gridPane1 = new GridPane();
                TextField textField = new TextField();
                textField.setText("Address");
                gridPane1.add(textField, 0, 0);
                textField.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        InputProcessor.saveGame("save game " + textField.getCharacters().toString());
                        InputProcessor.game.show();
                    }
                });
                loadButton.setDisable(true);
                saveButton.setDisable(true);
                LoadCustom.setDisable(true);
                newGame.setDisable(true);
                stage.setScene(new Scene(gridPane1));
                stage.show();
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent windowEvent) {
                        loadButton.setDisable(false);
                        saveButton.setDisable(false);
                        LoadCustom.setDisable(false);
                        newGame.setDisable(false);
                    }
                });

            }
        });

        LoadCustom.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = new Stage();
                GridPane gridPane1 = new GridPane();
                TextField textField = new TextField();
                textField.setText("Address");
                gridPane1.add(textField, 0, 0);
                textField.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        InputProcessor.loadCustom("load custom " + textField.getCharacters().toString());
                        InputProcessor.game.show();
                    }
                });
                loadButton.setDisable(true);
                saveButton.setDisable(true);
                LoadCustom.setDisable(true);
                newGame.setDisable(true);
                stage.setScene(new Scene(gridPane1));
                stage.show();
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent windowEvent) {
                        loadButton.setDisable(false);
                        saveButton.setDisable(false);
                        LoadCustom.setDisable(false);
                        newGame.setDisable(false);
                    }
                });

            }
        });


        newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = new Stage();
                GridPane gridPane1 = new GridPane();
                TextField textField = new TextField();
                textField.setText("GameName");
                gridPane1.add(textField, 0, 0);
                textField.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        InputProcessor.runNewGame("run " + textField.getCharacters().toString());
                        InputProcessor.game.show();
                        stage.close();
                    }
                });
                loadButton.setDisable(true);
                saveButton.setDisable(true);
                LoadCustom.setDisable(true);
                newGame.setDisable(true);
                stage.setScene(new Scene(gridPane1));
                stage.show();
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent windowEvent) {
                        loadButton.setDisable(false);
                        saveButton.setDisable(false);
                        LoadCustom.setDisable(false);
                        newGame.setDisable(false);
                    }
                });

            }
        });
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);


    }

    public synchronized static void stopGame() {
        InputProcessor.game.w.setFlag(true);

    }

    public static void gameEnded() {
        //todo gameEndedEnded
    }

    public static void InGameMenu(boolean flag) {
        Stage stage = Main.stage;
        GridPane gridPane = new GridPane();
        Button continueButton = new Button("Continue");
        continueButton.setOnAction(actionEvent -> {
            continueSingleGame();
        });
        if (flag) {
            continueButton.setDisable(true);
        }
        gridPane.add(continueButton, 0, 0);
        Button SingleGame = new Button("SinglePlayer");
        SingleGame.setOnAction(new EventHandler<ActionEvent>() {
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
                                InputProcessor.setSpeed(num);
                                //singleGameStarted(num);
                                InSingleMenu();

                            } else {
                                textField.setText("");
                            }
                        } catch (Exception e) {
                            textField.setText("");
                        }
                    });
                    gridPane.add(textField, 0, 1);
                    flag = true;
                    stage.setScene(new Scene(gridPane));
                    stage.show();
                    stage.setOnCloseRequest(windowEvent -> flag = false);

                } else {

                }
            }
        });
        gridPane.add(SingleGame, 0, 1);


        Button btn2 = new Button("Multiplayer");
        btn2.setOnAction(actionEvent -> {
            multiplayerGame();
        });
        gridPane.add(btn2, 1, 1);

        Button btn3 = new Button("Exit");
        btn3.setOnAction(actionEvent -> {
            System.exit(0);
        });
        gridPane.add(btn3, 2, 1);

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

        Scene scene = new Scene(gridPane, image.getWidth(), image.getHeight());
        stage.setScene(scene);


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
        stage.setOnCloseRequest(windowEvent -> System.exit(0));
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
}















