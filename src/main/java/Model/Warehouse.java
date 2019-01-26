package Model;

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
import java.util.List;
import java.util.Random;

public class Warehouse implements Upgradable {
    static String path = "/home/a/Projects/AP_Project/AP_15/static/Service/Depot/";
    int Level;
    private int capacity = this.getMaxCapacity();
    private ArrayList<Item> items = new ArrayList<>(0);


    public Warehouse(int level, int capacity, ArrayList<Item> items) {
        Level = level;
        this.capacity = capacity;
        this.items = items;
    }

    public Warehouse(int level, ArrayList<Item> items) {
        Level = level;
        this.items = items;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    //Finished
    @Override
    public boolean upgrade(Farm farm) {
        if (farm.getCurrentMoney() < getUpgradeCost()) {
            return false;
        }
        if (Level == 3) {
            System.out.println("Wow you still want a better wareHouse");
            return false;
        }
        farm.setCurrentMoney(farm.getCurrentMoney() - getUpgradeCost());
        Level += 1;
        return true;
    }


    @Override
    public int getUpgradeCost() {
        //todo
        return 0;
    }


    private int getMaxCapacity() {
        switch (Level) {
            case 0:
                return 50;
            case 1:
                return 150;
            case 2:
                return 300;
            case 3:
                return 600;
            default:
                return 0;
        }
    }

    //finished
    public List<Item> findSpecificItem(Item.ItemInfo itemType, int MaxNumberToFind) {
        ArrayList<Item> methodOutput = new ArrayList<>(0);
        int numberFound = 0;
        for (Item item : items) {
            if (item.getItemInfo().equals(itemType)) {
                methodOutput.add(item);
            }
            numberFound++;
            if (numberFound == MaxNumberToFind) {
                break;
            }
        }
        return methodOutput;


    }

    public void remove(Item toBeAddedItem) {
        items.remove(toBeAddedItem);
        capacity += toBeAddedItem.itemInfo.DepotSize;
    }

    public void addItem(Item item) {
        items.add(item);
        capacity -= item.itemInfo.DepotSize;
    }

    public void print() {
        System.out.println("WareHouse :");
        System.out.println("Capacity = " + capacity);
        for (Item item : items) {
            item.Print();
        }
    }


    private void viewDepotMenu() {


        {
            Main.stopGame();

            final Random rng = new Random();
            VBox content = new VBox(5);
            ScrollPane scroller = new ScrollPane(content);
            scroller.setFitToWidth(true);

            Button addButton = new Button("Add");
            Button backButton = new Button("Back");
            backButton.setOnAction(ev -> Main.continueSingleGame());
            addButton.setDisable(true);
            addButton.setOnAction(new EventHandler<ActionEvent>() {
                boolean flag;

                @Override
                public void handle(ActionEvent actionEvent) {
                    if (!flag) {
                        flag = true;
                        Stage stage = new Stage();
                        GridPane gridPane = new GridPane();
                        TextField itemName = new TextField("ItemName");
                        gridPane.add(itemName, 0, 0);
                        TextField Count = new TextField("Count");
                        gridPane.add(Count, 0, 1);
                        Button btn = new Button("Click");

                        backButton.setOnAction(event -> Main.continueSingleGame());
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
                Button button = new Button("Truck");
                button.setOnAction(evt -> {
                    content.getChildren().remove(anchorPane);
                    //this.getItems().remove(item);
                    InputProcessor.process("truck add " + item.getItemInfo().getItemName() + " 1");


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
        AnchorPane.setLeftAnchor(hBox, 300.0);


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
                viewDepotMenu();
            }
        });
    }
}
