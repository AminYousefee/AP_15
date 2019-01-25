package Model;

import View.VehicleView.TruckView;
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

public class Truck extends Vehicle {
    static String path = "/home/a/Projects/AP_Project/AP_15/static/Service/Truck/";

    public Truck(int level, int capacity, int remainingTurns, Farm farm, int price, ArrayList<Item> items, Integer farmMoney) {
        super(level, capacity, remainingTurns, farm, price, items, farmMoney);
    }

    public Truck(int level, int remainingTurns, Farm farm, int price, ArrayList<Item> items, Integer farmMoney) {
        super(level, remainingTurns, farm, price, items, farmMoney);
    }

    private void viewTruckMenu() {


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
                        flag = true;
                        Main.stopGame();
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
                                boolean f = InputProcessor.addToVehicle2("truck add " + itemName.getCharacters().toString() + " " + Count.getCharacters().toString());
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
                    this.getItems().remove(item);


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
        AnchorPane.setTopAnchor(hBox, 500.0);
        AnchorPane.setLeftAnchor(hBox, 450.0);


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
                viewTruckMenu();
            }
        });
    }

    private int getMaxCapacity() {
        //todo
        return getLevel() * 50;
    }

    public void turn() {
        if (RemainingTurns > 1) {
            RemainingTurns -= 1;
        } else if (RemainingTurns == 1) {
            RemainingTurns -= 1;
            FarmMoney = FarmMoney + Price;
            Price = 0;
        } else {
            // do nothing
        }
    }

    public void printTruck() {
        TruckView.PrintTruck(this);


    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public int getTravelTurns() {
        //todo
        return 20 - 5 * getLevel();
    }

    @Override
    public boolean upgrade(Farm farm) {
        if (farm.getCurrentMoney() < getUpgradeCost()) {
            return false;
        }
        if (getLevel() == 3) {
            System.out.println("Unable to do update on truck as it's updated to level 3");
            return false;
        }
        farm.setCurrentMoney(farm.getCurrentMoney() - getUpgradeCost());

        Level += 1;
        return true;

    }

    @Override
    public int getUpgradeCost() {
        return 0;
        //todo
    }

    public void addItem(Item item) {
        //if ()
        items.add(item);
    }

    @Override
    public boolean go() {
        if (this.isInTravel()) {
            System.out.println("Truck in Travel");
            return false;
        } else {
            this.goTravel();
            return true;
        }
    }

    public boolean goTravel() {
        RemainingTurns = getTravelTurns();
        Price = getPrice();

        return true;

    }

    public int getBoxNumbers() {
        return 2 + getLevel();
    }

}
