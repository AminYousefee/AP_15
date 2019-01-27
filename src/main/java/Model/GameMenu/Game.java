package Model.GameMenu;

import Model.Farm;
import Model.GameMenu.Missions.Mission;
import controller.InputProcessor;
import controller.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {

    public static final Object obj = new Object();
    public static ArrayList<Game> loadedGames = new ArrayList<>(0);
    public ev.W w;
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
        System.out.println("Game = " + name);
        System.out.println("Money = " + this.getFarm().getCurrentMoney());
        System.out.println("Time Gone = " + this.getFarm().getTurnsWent());
        System.out.println(mission.goal);


    }

    public void turn() {
        /*System.out.println("Turn = " + this.getFarm().getTurnsWent());
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
        }*/
        if (mission.isSatisfied()){

        }else {
            farm.turn();
        }
    }


    public void show() {


        AnchorPane pane = new AnchorPane();
        Main.pane = pane;
        //pane.setPadding(new Insets(25, 25, 25, 25));
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

        Button btn = new Button("Menu");
        Button buyAnimalButton = new Button("Buy Animal");
        AnchorPane.setTopAnchor(buyAnimalButton, 50.0);
        Main.pane.getChildren().add(buyAnimalButton);
        buyAnimalButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Main.stopGame();
                Stage stage = new Stage();
                final Random rng = new Random();
                VBox content = new VBox(5);
                ScrollPane scroller = new ScrollPane(content);
                scroller.setFitToWidth(true);

                Button addButton = new Button("Add");
                addButton.setDisable(true);
                String[] strs = new String[]{"Turkey", "Sheep", "Cow", "Dog", "Cat"};
                for (int i = 0; i < 3; i++) {
                    AnchorPane anchorPane = new AnchorPane();
                    String style = String.format("-fx-background: rgb(%d, %d, %d);" +
                                    "-fx-background-color: -fx-background;",
                            rng.nextInt(256),
                            rng.nextInt(256),
                            rng.nextInt(256));
                    anchorPane.setStyle(style);
                    Label label = new Label(strs[i]);
                    AnchorPane.setLeftAnchor(label, 5.0);
                    AnchorPane.setTopAnchor(label, 5.0);
                    Button button = new Button("Buy");

                    button.setOnAction(new ev(i, strs));
                    AnchorPane.setRightAnchor(button, 5.0);
                    AnchorPane.setTopAnchor(button, 5.0);
                    AnchorPane.setBottomAnchor(button, 5.0);
                    anchorPane.getChildren().addAll(label, button);
                    content.getChildren().add(anchorPane);
                }

                Scene scene = new Scene(new BorderPane(scroller, null, null, addButton, null), 400, 400);
                stage.setScene(scene);
                stage.show();

            }
        });
        pane.getChildren().add(btn);
        //InputProcessor.game.show();
        /*Truck.show();
        Helicopter.show();
        Warehouse.show();*/
        Scene scene = new Scene(pane, 800, 600);
        Main.stage.setScene(scene);
        w = new ev.W();
        InputProcessor.game.turn();
        new Thread(w).start();

        //InputProcessor.process("turn 1");


        Text text = new Text();
        mission.setText(text);
        text.setFill(Color.YELLOW);
        text.setBlendMode(BlendMode.OVERLAY);
        AnchorPane.setLeftAnchor(text, 450.0);
        AnchorPane.setTopAnchor(text, 10.0);
        Main.pane.getChildren().add(text);
        farm.show();
    }
}

class ev implements EventHandler<ActionEvent> {
    int anInt;
    String[] strs;

    public ev(int i1, String[] strings) {
        anInt = i1;
        strs = strings;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        InputProcessor.process("buy " + strs[anInt]);

    }

    public static class W implements Runnable {

        public boolean flag;

        public boolean isFlag() {
            return flag;
        }

        public synchronized void setFlag(boolean flag) {
            this.flag = flag;
        }

        @Override
        public void run() {

            while (!flag) {
                Thread t = InputProcessor.game.getFarm().getMap().threads.poll();
                if (t != null) {
                    t.start();
                }
            }

        }

    }
}