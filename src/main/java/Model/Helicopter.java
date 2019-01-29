package Model;

import Model.GameMenu.Game;
import controller.InputProcessor;
import controller.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class Helicopter extends Vehicle {
    public static ArrayList<Item.ItemInfo> buyableItems = new ArrayList<>(0);
    static String path = "/home/a/Projects/AP_Project/AP_15/static/Service/Helicopter/";

    public Helicopter(int level, int capacity, int remainingTurns, Farm farm, int price, ArrayList<Item> items, Integer farmMoney) {
        super(level, capacity, remainingTurns, farm, price, items, farmMoney);
    }

    public Helicopter(int level, int remainingTurns, Farm farm, int price, ArrayList<Item> items, Integer farmMoney) {
        super(level, remainingTurns, farm, price, items, farmMoney);
    }

    public static Item.ItemInfo findItem(String itemName) {
        for (Item.ItemInfo itemInfo : buyableItems) {
            if (itemName.equalsIgnoreCase(itemInfo.getItemName())) {
                return itemInfo;
            }

        }
        return null;
    }

    private void viewHelicopterMenu() {


        {

            final Random rng = new Random();
            VBox content = new VBox(5);
            ScrollPane scroller = new ScrollPane(content);
            scroller.setFitToWidth(true);

            Button addButton = new Button("Add");
            Button backButton = new Button("Back");
            backButton.setOnAction(ev->Main.continueSingleGame());
            addButton.setOnAction(new EventHandler<ActionEvent>() {
                boolean flag;

                @Override
                public void handle(ActionEvent actionEvent) {
                    if (!flag) {
                        Main.stopGame();
                        flag = true;
                        Stage stage = new Stage();
                        GridPane gridPane = new GridPane();
                        TextField itemName = new TextField("ItemName");
                        gridPane.add(itemName, 0, 0);
                        TextField Count = new TextField("Count");
                        gridPane.add(Count, 0, 1);
                        Button btn = new Button("Click");
                        gridPane.add(btn, 0, 2);
                        Label label = new Label();
                        btn.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                boolean f = InputProcessor.addToVehicle2("helicopter add " + itemName.getCharacters().toString() + " " + Count.getCharacters().toString());
                                if (f) {
                                    stage.close();
                                    flag = false;
                                } else {
                                    label.setText("Couldn't");
                                }

                            }
                        });
                        stage.setOnCloseRequest(ein -> flag = false);

                        gridPane.add(label, 0, 3);
                        Scene scene = new Scene(gridPane);
                        stage.setScene(scene);
                        stage.show();
                    }

                }
            });
            for (Item item : this.getItems()) {
                AnchorPane anchorPane = new AnchorPane();
                String style = String.format("-fx-background: rgb(%d, %d, %d);" +
                                "-fx-background-color: -fx-background;",
                        rng.nextInt(256),
                        rng.nextInt(256),
                        rng.nextInt(256));
                anchorPane.setStyle(style);
                Label label = new Label(item.itemInfo.ItemName);
                AnchorPane.setLeftAnchor(label, 5.0);
                AnchorPane.setTopAnchor(label, 5.0);
                Button button = new Button("Remove");
                button.setOnAction(evt -> {
                    content.getChildren().remove(anchorPane);
                    /*this.getItems().remove(item);*/


                });
                AnchorPane.setRightAnchor(button, 5.0);
                AnchorPane.setTopAnchor(button, 5.0);
                AnchorPane.setBottomAnchor(button, 5.0);
                anchorPane.getChildren().addAll(label, button);
                content.getChildren().add(anchorPane);
            }
            VBox vBox = new VBox();
            vBox.getChildren().add(0, addButton);
            vBox.getChildren().add(1, backButton);
            Scene scene = new Scene(new BorderPane(scroller, null, null, vBox, null), 400, 400);
            Main.stage.setScene(scene);
            Main.stage.show();
        }


    }

    public void show() {
        Image image = null;
        try {
            image = new Image(new FileInputStream(path + "01" + ".png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView(image);
        //imageView.setOnM
        imageView.setX(0);
        imageView.setY(0);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(imageView);
        AnchorPane.setTopAnchor(hBox, 450.0);
        AnchorPane.setLeftAnchor(hBox, 150.0);


        Main.pane.getChildren().add(hBox);
        /*imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                imageView.setFitHeight(30);
                imageView.setFitWidth(30);
            }
        });

        imageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                imageView.setFitWidth(imageView.getImage().getRequestedWidth());
                imageView.setFitHeight(imageView.getImage().getRequestedHeight());
            }
        });*/
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                viewHelicopterMenu();
            }
        });
    }

    public void printHelicopter() {
    }

    @Override
    public boolean upgrade(Farm farm) {
        if (farm.getCurrentMoney() < getUpgradeCost()) {
            return false;
        }
        if (getLevel() == 3) {
            System.out.println("Unable to do update on helicopter as it's updated to level 3");
            return false;
        }
        farm.setCurrentMoney(farm.getCurrentMoney() - getUpgradeCost());
        Level += 1;
        return true;
    }

    @Override
    public int getUpgradeCost() {
        return 0;
    }

    public void turn() {
        synchronized (Game.obj) {
            if (getRemainingTurns() > 1) {
                setRemainingTurns(getRemainingTurns() - 1);
            } else if (getRemainingTurns() == 1) {
                setRemainingTurns(getRemainingTurns() - 1);
                for (Item item : items) {
                    farm.getMap().addItemInRandom(item);
                }
                items.clear();

                setPrice(0);
            } else {
                // do nothing
            }
        }
        InputProcessor.game.getFarm().getMap().threads.add(new Thread(() -> turn()));
    }

    public void addItem(Item item) {
        items.add(item);
    }

    @Override
    public boolean go() {
        if (this.isInTravel()) {
            System.out.println("Helicopter in Travel");
            return false;
        } else {
            this.goTravel();
            return true;
        }
    }

    @Override
    protected int getTravelTurns() {
        return 12 - 3 * getLevel();
    }

    public boolean goTravel() {
        RemainingTurns = getTravelTurns();
        Price = getPrice();
        setFarmMoney(getFarmMoney() - Price);

        return true;

    }

    public int getScatteringRadius() {
        switch (getLevel()) {
            case 0:
                return 120;
            case 1:
                return 100;
            case 2:
                return 60;
            case 3:
                return 20;
            default:
                return 0;
        }
    }


}
