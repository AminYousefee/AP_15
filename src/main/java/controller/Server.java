package controller;

import Model.Item;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server implements Runnable {
    private static Server ourInstance = new Server();
    ServerClient serverClient;
    HashMap<String, Client> clients = new HashMap<>();
    Listener listener;
    int port;
    Market market = new Market();
    ChatRoom chatRoom = new ChatRoom(true);
    LeaderBoard leaderBoard = new LeaderBoard(true);

    {
        //clients.put(serverClient.getUsername(), serverClient);
    }

    public static Server getInstance() {
        return ourInstance;
    }

    @Override
    public void run() {
        listener = new Listener(port, this);
        new Thread(listener).start();
    }


    public void show() {
        Stage stage = Main.stage;
        GridPane gp = new GridPane();
        Button chatroom = new Button("chatroom");
        Button leaderboard = new Button("leaderboard");
        Button market = new Button("market");
        Button people = new Button("people");
        gp.add(chatroom, 0, 0);
        gp.add(leaderboard, 0, 1);
        gp.add(market, 0, 2);
        gp.add(people, 0, 3);
        chatroom.setOnAction(event -> {
            Server.getInstance().chatRoom.show();
        });
        leaderboard.setOnAction(event -> {
            Stage stage1 = new Stage();
            stage1.setTitle("Leaderboard");
            GridPane gridPane = new GridPane();
            Button level = new Button("Level");
            Button money = new Button("Money");
            level.setOnAction(event1 -> {
                Server.getInstance().leaderBoard.update("Level", stage1);

            });
            gridPane.addRow(2,
                    level, money);
            money.setOnAction(event1 -> {
                Server.getInstance().leaderBoard.update("Money", stage1);
            });
            stage1.setScene(new Scene(gridPane));
            stage1.show();
        });
        market.setOnAction(event -> Server.getInstance().market.show());
        //people.setOnAction(event -> Server.getInstance().showPeople());
        stage.setScene(new Scene(gp));

    }

    public void sendMessage() {


    }

    public void couldntStartListening() {
        Stage stage = new Stage();
        GridPane pane = new GridPane();
        TextField textField = new TextField("Port");
        pane.add(textField, 0, 0);
        textField.setOnAction(event -> {
            try {
                port = Integer.parseInt(textField.getCharacters().toString());
                listener.port = port;
                new Thread(listener).start();
                stage.close();
            } catch (Exception e) {
                textField.setText("");


            }
        });


    }

    public void serverMenu(Stage stage) {
        GridPane gp = new GridPane();
        TextField tf = new TextField("Port");
        tf.setOnAction(event -> {
            try {
                Server.getInstance().port = Integer.parseInt(tf.getCharacters().toString());
                new Thread(Server.getInstance()).run();
            } catch (Exception ignored) {
                tf.setText("");
            }
        });
        gp.add(tf, 0, 0);
        stage.setScene(new Scene(gp));
    }

    static class Client {
        String username;
        String realName;
        int level;
        int currentMoney;
        ClientServerRelation clientServerRelation;
        ArrayList<Client> allies = new ArrayList<>();
        private int numOfTeamGames;
        private int numOfDeals;

        public Client(String username, String realName, int level, int currentMoney) {
            this.username = username;
            this.realName = realName;
            this.level = level;
            this.currentMoney = currentMoney;
        }

        public Client(StartMessage startMessage, ClientServerRelation clientServerRelation) {
            username = startMessage.getClientName();
            level = startMessage.getLevel();
            currentMoney = startMessage.getCurrentMoney();
            this.clientServerRelation = clientServerRelation;
        }


        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getCurrentMoney() {
            return currentMoney;
        }

        public void setCurrentMoney(int currentMoney) {
            this.currentMoney = currentMoney;
        }

        public ClientServerRelation getClientServerRelation() {
            return clientServerRelation;
        }

        public void setClientServerRelation(ClientServerRelation clientServerRelation) {
            this.clientServerRelation = clientServerRelation;
        }


        public void sendMessage(Message msg) {
            clientServerRelation.msgSender.sendMessage(msg);
        }


        public int getNumOfTeamGames() {
            return numOfTeamGames;
        }

        public void setNumOfTeamGames(int numOfTeamGames) {
            this.numOfTeamGames = numOfTeamGames;
        }

        public int getNumOfDeals() {
            return numOfDeals;
        }

        public void setNumOfDeals(int numOfDeals) {
            this.numOfDeals = numOfDeals;
        }
    }

    static class ServerClient extends Client {

        public ServerClient(String username, String realName, int level, int currentMoney) {
            super(username, realName, level, currentMoney);
        }

        @Override
        public void sendMessage(Message msg) {
            //Interesting isn't it
        }
    }

}


class Listener implements Runnable {
    int port;
    Server server;


    public Listener(int port, Server server) {
        this.port = port;
        this.server = server;
    }

    @Override
    public void run() {
        while (true) {
            ServerSocket serverSocket = null;
            Socket socket = null;
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("Socket Started");
                System.out.println(port);
                while (true) {
                    socket = serverSocket.accept();
                    ClientServerRelation clientServerRelation = new ClientServerRelation(socket);
                    new Thread(clientServerRelation).start();
                }
            } catch (IOException e) {
                server.couldntStartListening();
            }


        }

    }
}


class ClientServerRelation implements Runnable {
    Socket socket;
    MessageReceiever msgReciever;
    MessageSender msgSender;

    public ClientServerRelation(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        msgSender = new MessageSender(this);
        msgReciever = new MessageReceiever(this);
        msgSender.sendMessage(new ClientAddedToServer(Server.getInstance().serverClient.username, Server.getInstance().serverClient.username, Server.getInstance().serverClient.realName, Server.getInstance().serverClient.currentMoney, Server.getInstance().serverClient.level));

        Thread t = new Thread(msgReciever);
        t.setDaemon(true);
        t.start();
        t = new Thread(msgSender);
        t.start();


    }


}


class MessageSender implements Runnable {
    ConcurrentLinkedQueue<Message> toBeSentMessages = new ConcurrentLinkedQueue<>();
    ClientServerRelation clientServerRelation;

    public MessageSender(ClientServerRelation clientServerRelation) {
        this.clientServerRelation = clientServerRelation;
    }

    public void sendMessage(Message msg) {
        toBeSentMessages.add(msg);
    }

    public void updater() {


        Message t = new UpdateMessage(Cli.getInstance().me.username, InputProcessor.game.mission.level, InputProcessor.game.getFarm().getCurrentMoney());
        Cli.getInstance().messageSender.sendMessage(t);
        new Thread(() -> {
            InputProcessor.game.getFarm().getMap().threads.add(new Thread(this::updater));
        });

    }


    @Override
    public synchronized void run() {
        if (InputProcessor.game != null) {
            updater();
        }

        while (true) {
            Message msg = toBeSentMessages.peek();
            if (msg != null) {
                try {
                    OutputStream outputStream = clientServerRelation.socket.getOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                    objectOutputStream.writeObject(msg);
                    toBeSentMessages.remove(msg);
                } catch (IOException e) {
                    //messageSendingError();
                }
            }
        }
    }


}


class MessageReceiever implements Runnable {
    ClientServerRelation clientServerRelation;

    public MessageReceiever(ClientServerRelation clientServerRelation) {
        this.clientServerRelation = clientServerRelation;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        ObjectInputStream ois = null;
        try {
            inputStream = clientServerRelation.socket.getInputStream();
            while (true) {
                ois = new ObjectInputStream(inputStream);
                Message mesasge = null;

                mesasge = (Message) ois.readObject();
                if (mesasge instanceof StartMessage) {
                    ((StartMessage) mesasge).clientServerRelation = this.clientServerRelation;
                }


                mesasge.process();

            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}

abstract class Message implements Serializable {
    String sender;

    public Message(String sender) {
        this.sender = sender;
    }

    public abstract void process();
}


class StartMessage extends Message implements Serializable {
    ClientServerRelation clientServerRelation;
    String clientName;
    int Level;
    int CurrentMoney;


    public StartMessage(String sender, String clientName, int level, int currentMoney) {
        super(sender);
        this.clientName = clientName;
        Level = level;
        CurrentMoney = currentMoney;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public int getCurrentMoney() {
        return CurrentMoney;
    }

    public void setCurrentMoney(int currentMoney) {
        CurrentMoney = currentMoney;
    }

    public void process() {
        if (Server.getInstance().clients.containsKey(clientName)) {
            clientServerRelation.msgSender.sendMessage(new ClientNameUsedMessage(Server.getInstance().serverClient.username));
        } else {
            Server.Client cl = new Server.Client(this, clientServerRelation);
            Server.getInstance().clients.put(clientName, cl);
            Message msg = new ClientAddedToServer(Server.getInstance().serverClient.username, clientName, clientName, CurrentMoney, Level);
            for (Map.Entry<String, Server.Client> entry : Server.getInstance().clients.entrySet()) {
                if (!entry.getKey().equals(clientName)) {
                    entry.getValue().sendMessage(msg);
                }
            }
            Server.getInstance().chatRoom.update(cl);
        }
    }


}

class ClientNameUsedMessage extends Message {

    public ClientNameUsedMessage(String client) {
        super(client);
    }

    @Override
    public void process() {
        Cli cli = Cli.getInstance();

        Cli.getAnotherClientName();


    }
}

class LeaderBoard {
    VBox content;
    String sortType;

    boolean isServer;
    boolean isShown;

    public LeaderBoard(boolean isServer) {
        this.isServer = isServer;
    }

    public void update(String sortType, Stage stage) {
        this.sortType = sortType;


        if (sortType.equals("Level")) {
            ArrayList<Server.Client> list = new ArrayList<>(Server.getInstance().clients.values());
            list.sort(Comparator.comparingInt(Server.Client::getCurrentMoney));
            show(list, stage);


        } else {
            ArrayList<Server.Client> list = new ArrayList<>(Server.getInstance().clients.values());
            list.sort(Comparator.comparingInt(Server.Client::getLevel));
            show(list, stage);

        }

    }


    public void show(ArrayList<Server.Client> clients, Stage primaryStage) {
        final Random rng = new Random();
        primaryStage.setOnCloseRequest(windowEvent -> content = null);
        content = new VBox(5);
        ScrollPane scroller = new ScrollPane(content);
        scroller.setFitToWidth(true);
        Button addButton = new Button("Add");
        isShown = true;
        new Thread(() -> this.reSort(content));
        primaryStage.setOnCloseRequest(windowEvent -> isShown = false);
        for (Server.Client client : clients) {
            AnchorPane anchorPane = new AnchorPane();
            String style = String.format("-fx-background: rgb(%d, %d, %d);" +
                            "-fx-background-color: -fx-background;",
                    rng.nextInt(256),
                    rng.nextInt(256),
                    rng.nextInt(256));
            anchorPane.setStyle(style);
            anchorPane.setUserData(client);
            Label label = new Label(client.username);
            AnchorPane.setLeftAnchor(label, 5.0);
            AnchorPane.setTopAnchor(label, 5.0);
            Button button = new Button("Profile");
            Label nameLabel = new Label(client.getUsername());


            Label teamGames = new Label("Games = " + client.getNumOfTeamGames());
            Label Deals = new Label("Deals = " + client.getNumOfDeals());
            Button pv = new Button("Message");
            AnchorPane.setTopAnchor(pv, 30.0);
            AnchorPane.setRightAnchor(pv, 5.0);
            pv.setOnAction(new EventHandler<ActionEvent>() {
                boolean flag;
                Stage stage;

                @Override
                public void handle(ActionEvent actionEvent) {
                    if (flag == false) {
                        stage = new Stage();
                        stage.setOnCloseRequest(windowEvent -> flag = false);
                        stage.setOnShown(windowEvent -> flag = true);
                        stage.setTitle("Message To " + client.username);
                        GridPane gridPane = new GridPane();
                        TextField textField = new TextField();
                        textField.setOnAction(actionEvent1 -> {
                            if (isServer) {
                                Message msg = new PvMessageToClient(Server.getInstance().serverClient.username, Server.getInstance().serverClient.username, textField.getCharacters().toString());
                                Server.getInstance().clients.get(client.username).sendMessage(msg);
                            } else {
                                Message msg = new PvMessageToServer(Cli.getInstance().me.username, textField.getCharacters().toString(), client.username);
                                Cli.getInstance().messageSender.sendMessage(msg);

                            }


                            //dsfhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhiiiiiiiiiiiiiii
                            ///
                            //
                            //
                            //
                            //
                            //

                            stage.close();
                            flag = false;
                        });
                        gridPane.add(textField, 0, 0);
                        Scene scene = new Scene(gridPane);
                        stage.setScene(scene);
                        stage.show();
                    }
                }
            });

            AnchorPane.setTopAnchor(teamGames, 30.0);
            AnchorPane.setTopAnchor(Deals, 30.0);
            AnchorPane.setTopAnchor(nameLabel, 30.0);
            AnchorPane.setLeftAnchor(nameLabel, 5.0);
            AnchorPane.setLeftAnchor(teamGames, 105.0);
            AnchorPane.setLeftAnchor(Deals, 205.0);
            button.setOnAction(new EventHandler<ActionEvent>() {
                boolean flag;

                @Override
                public void handle(ActionEvent actionEvent) {
                    if (flag == false) {
                        anchorPane.getChildren().addAll(nameLabel, teamGames, Deals, pv);
                        flag = true;
                    } else {
                        anchorPane.getChildren().removeAll(nameLabel, teamGames, Deals, pv);
                        flag = false;
                    }
                }
            });
            AnchorPane.setRightAnchor(button, 5.0);
            AnchorPane.setTopAnchor(button, 5.0);


            anchorPane.getChildren().addAll(label, button);
            content.getChildren().add(anchorPane);
        }
        Scene scene = new Scene(new BorderPane(scroller, null, null, addButton, null), 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void reSort(VBox content) {
        Object object = new Object();

        while (isShown) {
            if (sortType.equals("Level")) {

                content.getChildren().sort((client, t1) -> {
                    if (((Server.Client) client.getUserData()).getLevel() == ((Server.Client) t1.getUserData()).getLevel()) {
                        return 0;
                    } else if (((Server.Client) client.getUserData()).getLevel() > ((Server.Client) t1.getUserData()).getLevel()) {
                        return 1;
                    } else {
                        return -1;
                    }

                });
            } else {

                content.getChildren().sort((client, t1) -> {
                    if (((Server.Client) client.getUserData()).getCurrentMoney() == ((Server.Client) t1.getUserData()).getCurrentMoney()) {
                        return 0;
                    } else if (((Server.Client) client.getUserData()).getCurrentMoney() > ((Server.Client) t1.getUserData()).getCurrentMoney()) {
                        return 1;
                    } else {
                        return -1;
                    }

                });


                synchronized (object) {
                    try {
                        object.wait(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

        }


    }


}

abstract class ChatMessage extends Message {
    String message;

    public ChatMessage(String sender, String message) {
        super(sender);
        this.message = message;
    }

}


class ChatRoom {
    public boolean isShown;
    boolean inServer;
    ArrayList<ChatMessage> mesasges = new ArrayList<>(0);
    VBox content;

    public ChatRoom(boolean inServer) {
        this.inServer = inServer;
    }

    public void ChatRoom() {

    }

    public void ContentMaker() {
        final Random rng = new Random();
        content = new VBox(5);
        for (ChatMessage message : mesasges) {
            AnchorPane anchorPane = new AnchorPane();
            String style = String.format("-fx-background: rgb(%d, %d, %d);" +
                            "-fx-background-color: -fx-background;",
                    rng.nextInt(256),
                    rng.nextInt(256),
                    rng.nextInt(256));
            anchorPane.setStyle(style);
            Label label = new Label(message.sender);
            AnchorPane.setLeftAnchor(label, 5.0);
            AnchorPane.setTopAnchor(label, 5.0);
            Label label1 = new Label(message.message);
            AnchorPane.setTopAnchor(label1, 30.0);
            AnchorPane.setRightAnchor(label1, 5.0);

            /*button.setOnAction(evt -> content.getChildren().remove(anchorPane));
            AnchorPane.setRightAnchor(button, 5.0);
            AnchorPane.setTopAnchor(button, 5.0);
            AnchorPane.setBottomAnchor(button, 5.0);*/
            anchorPane.getChildren().addAll(label, label1);
            content.getChildren().add(anchorPane);
        }

    }

    public void show() {
        Stage stage = new Stage();


        final Random rng = new Random();
        if (content == null) {
            ContentMaker();
        }
        ScrollPane scroller = new ScrollPane(content);
        scroller.setFitToWidth(true);

        Button addButton = new Button("Add");
        isShown = true;
        addButton.setOnAction(event -> {
            Stage stage1 = new Stage();
            GridPane gp = new GridPane();
            TextField textField = new TextField("msg");
            gp.add(textField, 0, 0);
            stage1.setScene(new Scene(gp));
            stage1.show();
            textField.setOnAction(event1 -> {


                String sender;
                if (inServer) {
                    sender = Server.getInstance().serverClient.username;
                    ChatMsgToClients chatMsgToClients = new ChatMsgToClients(Server.getInstance().serverClient.username, textField.getCharacters().toString(), Server.getInstance().serverClient.username);
                    for (Map.Entry<String, Server.Client> entry : Server.getInstance().clients.entrySet()) {
                        entry.getValue().sendMessage(chatMsgToClients);
                        mesasges.add(chatMsgToClients);
                    }
                } else {
                    sender = Cli.getInstance().me.username;
                    ChatMessageToServer chatMessageToServer = new ChatMessageToServer(Cli.getInstance().me.username, textField.getCharacters().toString());
                    Cli.getInstance().messageSender.sendMessage(chatMessageToServer);
                    mesasges.add(chatMessageToServer);

                }
                AnchorPane anchorPane = new AnchorPane();
                String style = String.format("-fx-background: rgb(%d, %d, %d);" +
                                "-fx-background-color: -fx-background;",
                        rng.nextInt(256),
                        rng.nextInt(256),
                        rng.nextInt(256));
                anchorPane.setStyle(style);
                Label label = new Label(sender);
                AnchorPane.setLeftAnchor(label, 5.0);
                AnchorPane.setTopAnchor(label, 5.0);
                Label label1 = new Label(textField.getCharacters().toString());
                AnchorPane.setTopAnchor(label1, 30.0);
                AnchorPane.setRightAnchor(label1, 5.0);

            /*button.setOnAction(evt -> content.getChildren().remove(anchorPane));
            AnchorPane.setRightAnchor(button, 5.0);
            AnchorPane.setTopAnchor(button, 5.0);
            AnchorPane.setBottomAnchor(button, 5.0);*/
                anchorPane.getChildren().addAll(label, label1);
                content.getChildren().add(anchorPane);
                stage1.close();

            });

        });
        for (ChatMessage message : mesasges) {
            AnchorPane anchorPane = new AnchorPane();
            String style = String.format("-fx-background: rgb(%d, %d, %d);" +
                            "-fx-background-color: -fx-background;",
                    rng.nextInt(256),
                    rng.nextInt(256),
                    rng.nextInt(256));
            anchorPane.setStyle(style);
            Label label = new Label(message.sender);
            AnchorPane.setLeftAnchor(label, 5.0);
            AnchorPane.setTopAnchor(label, 5.0);
            Label label1 = new Label(message.message);
            AnchorPane.setTopAnchor(label1, 30.0);
            AnchorPane.setRightAnchor(label1, 5.0);

            /*button.setOnAction(evt -> content.getChildren().remove(anchorPane));
            AnchorPane.setRightAnchor(button, 5.0);
            AnchorPane.setTopAnchor(button, 5.0);
            AnchorPane.setBottomAnchor(button, 5.0);
            anchorPane.getChildren().addAll(label, button);*/
            content.getChildren().add(anchorPane);
        }

        Scene scene = new Scene(new BorderPane(scroller, null, null, addButton, null), 400, 400);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(windowEvent -> isShown = false);


    }

    public void updateContent(ChatMessage chatMessage) {
        final Random rng = new Random();


        AnchorPane anchorPane = new AnchorPane();
        String style = String.format("-fx-background: rgb(%d, %d, %d);" +
                        "-fx-background-color: -fx-background;",
                rng.nextInt(256),
                rng.nextInt(256),
                rng.nextInt(256));
        anchorPane.setStyle(style);
        Label label = new Label(chatMessage.sender);
        AnchorPane.setLeftAnchor(label, 5.0);
        AnchorPane.setTopAnchor(label, 5.0);
        Label label1 = new Label(chatMessage.message);
        AnchorPane.setTopAnchor(label1, 30.0);
        AnchorPane.setRightAnchor(label1, 5.0);
        content.getChildren().add(anchorPane);

    }


    public void addMessage(ChatMessage message) {
        final Random rng = new Random();

        mesasges.add(message);
        AnchorPane anchorPane = new AnchorPane();
        String style = String.format("-fx-background: rgb(%d, %d, %d);" +
                        "-fx-background-color: -fx-background;",
                rng.nextInt(256),
                rng.nextInt(256),
                rng.nextInt(256));
        anchorPane.setStyle(style);
        Label label = new Label(message.sender);
        AnchorPane.setLeftAnchor(label, 5.0);
        AnchorPane.setTopAnchor(label, 5.0);
        Label label1 = new Label(message.message);
        AnchorPane.setTopAnchor(label1, 30.0);
        AnchorPane.setRightAnchor(label1, 5.0);
        anchorPane.getChildren().addAll(label, label1);
        if (content != null) {
            Platform.runLater(() -> content.getChildren().add(anchorPane));

        }

      /*  for (Server.Client client : Server.getInstance().clients) {
            client.sendMessage(message);
        }
*/

    }

    public void update(Server.Client cl) {
        for (Message message : mesasges) {
            cl.sendMessage(message);
        }
    }
}

class Market {
    private static Market ourInstance = new Market();
    ArrayList<SellMessage> sellMessages = new ArrayList<>();
    VBox content;
    boolean isServer;


    public static Market getInstance() {
        return ourInstance;
    }

    public void buyItem(String seller, String itemName) {
        Iterator<SellMessage> sellMessageIterator = sellMessages.iterator();
        while (sellMessageIterator.hasNext()) {
            SellMessage sellMessage = sellMessageIterator.next();
            if (sellMessage.item.getItemInfo().getItemName().equals(itemName) && sellMessage.seller.equals(seller)) {
                sellMessageIterator.remove();
                break;
            }
        }
        //Server.getInstance().clients.get(msg.sender).sendMessage(new BuyMessageToServer(msg.getItem()));
    }

    private void removeMessage(SellMessage msg) {
        sellMessages.remove(this);
    }

    public void addItemToSell(SellMessage msg) {
        sellMessages.add(msg);
    }

    public void show() {
        Stage stage = new Stage();


        final Random rng = new Random();
        if (content == null) {
            this.ContentMaking();
        }
        ScrollPane scroller = new ScrollPane(content);
        scroller.setFitToWidth(true);

        Button addButton = new Button("Add");
        if (InputProcessor.game == null) {
            addButton.setDisable(true);
        }
        addButton.setOnAction(event -> {
            Stage stage1 = new Stage();
            GridPane gridPane = new GridPane();
            TextField item = new TextField("ItemName");
            List<Item> items;
            if (InputProcessor.game == null) {
                items = new ArrayList<>();
            } else {

                items = InputProcessor.game.getFarm().getWarehouse().getItems();
            }
            //just for debugging now
            items = new ArrayList<>();
            items.add(Item.getInstance("Egg"));
            items.add(Item.getInstance("Egg"));
            items.add(Item.getInstance("Egg"));
            items.add(Item.getInstance("Egg"));


            Item flag = null;
            for (Item item1 : items) {
                if (item1.getItemInfo().getItemName().equalsIgnoreCase(item.getCharacters().toString())) {
                    flag = item1;
                }
            }

            if (flag != null) {
                //items.remove(flag);
                SellMessage sl = new SellMessageToServer(Cli.getInstance().me.username, flag);
                Cli.getInstance().messageSender.sendMessage(sl);
                AnchorPane anchorPane = new AnchorPane();
                String style = String.format("-fx-background: rgb(%d, %d, %d);" +
                                "-fx-background-color: -fx-background;",
                        rng.nextInt(256),
                        rng.nextInt(256),
                        rng.nextInt(256));
                anchorPane.setStyle(style);
                Label label = new Label(sl.getItem().getItemInfo().getItemName());
                AnchorPane.setLeftAnchor(label, 5.0);
                AnchorPane.setTopAnchor(label, 5.0);
                Label label1 = new Label("Seller = " + sl.seller);
                AnchorPane.setTopAnchor(label1, 30.0);
                AnchorPane.setRightAnchor(label1, 5.0);

            /*button.setOnAction(evt -> content.getChildren().remove(anchorPane));
            AnchorPane.setRightAnchor(button, 5.0);
            AnchorPane.setTopAnchor(button, 5.0);
            AnchorPane.setBottomAnchor(button, 5.0);
            anchorPane.getChildren().addAll(label, button);*/
                content.getChildren().add(anchorPane);

            } else {
                item.setText("");

            }
        });


        Scene scene = new Scene(new BorderPane(scroller, null, null, addButton, null), 400, 400);
        stage.setScene(scene);
        stage.show();


    }

    private void ContentMaking() {
        ArrayList<SellMessage> sellMessages = new ArrayList<>();
        sellMessages.add(new SellMessageToServer("Asghar",Item.getInstance("Egg")));
        sellMessages.add(new SellMessageToServer("Asghar",Item.getInstance("Egg")));
        sellMessages.add(new SellMessageToServer("Asghar",Item.getInstance("Horn")));
        Random rng = new Random();
        content = new VBox(5);
        for (SellMessage message : sellMessages) {
            AnchorPane anchorPane = new AnchorPane();
            String style = String.format("-fx-background: rgb(%d, %d, %d);" +
                            "-fx-background-color: -fx-background;",
                    rng.nextInt(256),
                    rng.nextInt(256),
                    rng.nextInt(256));
            anchorPane.setStyle(style);
            Label label = new Label(message.getItem().getItemInfo().getItemName());
            AnchorPane.setLeftAnchor(label, 5.0);
            AnchorPane.setTopAnchor(label, 5.0);
            Label label1 = new Label("Seller = " + message.seller);
            AnchorPane.setTopAnchor(label1, 30.0);
            AnchorPane.setRightAnchor(label1, 95.0);
            anchorPane.getChildren().addAll(label,label1);
            Button button = new Button("Buy");

            if (isServer){
                button.setOnAction(evt -> content.getChildren().remove(anchorPane));
                BoughtMessageToClients boughtMessageToClients = new BoughtMessageToClients(Server.getInstance().serverClient.username,message.item.getItemInfo().getItemName(),Server.getInstance().serverClient.username,message.seller);
                for (Map.Entry<String, Server.Client> entry : Server.getInstance().clients.entrySet()){
                    entry.getValue().sendMessage(boughtMessageToClients);
                }
                content.getChildren().remove(anchorPane);
            }else {
                button.setOnAction(event -> {
                    Cli.getInstance().messageSender.sendMessage(new BuyMessageToServer(Cli.getInstance().me.username, message.seller, message.item, Cli.getInstance().me.username));
                    sellMessages.remove(message);
                    content.getChildren().remove(anchorPane);
                });
            }
            AnchorPane.setRightAnchor(button, 5.0);
            AnchorPane.setTopAnchor(button, 5.0);
            AnchorPane.setBottomAnchor(button, 5.0);
            anchorPane.getChildren().addAll( button);
            content.getChildren().add(anchorPane);
        }

    }
}


class PvMessageToServer extends Message {
    String msgString;
    String receiver;

    public PvMessageToServer(String sender, String msgString, String receiver) {
        super(sender);
        this.msgString = msgString;
        this.receiver = receiver;
    }

    @Override
    public void process() {
        Server.getInstance().clients.get(receiver).sendMessage(new PvMessageToClient(Server.getInstance().serverClient.username, sender, msgString));

    }

}

class BoughtMessageToClients extends Message {
    String itemName;
    String Buyer;
    String seller;

    public BoughtMessageToClients(String sender, String itemName, String buyer, String seller) {
        super(sender);
        this.itemName = itemName;
        Buyer = buyer;
        this.seller = seller;
    }


    @Override
    public void process() {
        if (Cli.getInstance().me.username.equals(seller)) {
            InputProcessor.game.getFarm().setCurrentMoney(InputProcessor.game.getFarm().getCurrentMoney() + Item.getInstance(itemName).getItemInfo().getSaleCost());
            InputProcessor.game.getFarm().getWarehouse().removeItem(itemName);
        } else {
            Cli.getInstance().market.buyItem(this.seller, this.itemName);
        }
    }
}

class PvMessageToClient extends Message implements Serializable {
    String sender;
    String msgString;

    public PvMessageToClient(String sender, String sender1, String msgString) {
        super(sender);
        this.sender = sender1;
        this.msgString = msgString;
    }

    @Override
    public void process() {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            stage.setTitle("Message From " + sender);
            GridPane gridPane = new GridPane();
            gridPane.add(new Label(msgString), 0, 0);
            stage.setScene(new Scene(gridPane));
            stage.show();
        });

    }
}

abstract class SellMessage extends Message {
    String seller;
    Item item;

    public SellMessage(String sender, String seller, Item item) {
        super(sender);
        this.seller = seller;
        this.item = item;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}

class SellMessageToServer extends SellMessage {


    public SellMessageToServer(String sender, Item item) {
        super(sender, sender, item);
    }


    public Item getItem() {
        return item;
    }


    @Override
    public void process() {
        Market.getInstance().addItemToSell(this);

    }
}

class BuyMessageToServer extends Message {
    String seller;
    Item item;
    String buyer;

    public BuyMessageToServer(String sender, String seller, Item item, String buyer) {
        super(sender);
        this.seller = seller;
        this.item = item;
        this.buyer = buyer;
    }

    @Override
    public void process() {
        ArrayList<SellMessage> sellMessages = Server.getInstance().market.sellMessages;
        Iterator<SellMessage> iterator = sellMessages.iterator();
        while (iterator.hasNext()) {
            SellMessage sellMessage = iterator.next();
            if (sellMessage.seller.equals(this.seller) && sellMessage.item.getItemInfo().getItemName().equals(this.item.getItemInfo().getItemName())) {
                iterator.remove();
                break;
            }
        }
        for (Map.Entry<String, Server.Client> entry : Server.getInstance().clients.entrySet()) {
            entry.getValue().sendMessage(new BoughtMessageToClients(Server.getInstance().serverClient.username, seller, item.getItemInfo().getItemName(), buyer));
        }
    }
}


class ChatMessageToServer extends ChatMessage implements Serializable {

    public ChatMessageToServer(String client, String msg) {
        super(client, msg);
    }

    @Override
    public void process() {
        System.out.println("Client  = " + sender);
        System.out.println("msg = " + message);
        Server.getInstance().chatRoom.addMessage(this);
        for (Map.Entry<String, Server.Client> entry : Server.getInstance().clients.entrySet()) {
            entry.getValue().sendMessage(new ChatMsgToClients(Server.getInstance().serverClient.username, message, sender));
        }

    }
}

class ChatMsgToClients extends ChatMessage implements Serializable {
    String Owner;

    public ChatMsgToClients(String sender, String message, String owner) {
        super(sender, message);
        Owner = owner;
    }

    @Override
    public void process() {
        System.out.println("I recieved it");
        Cli.getInstance().chatRoom.addMessage(this);
        //Cli.getInstance().chatRoom.updateContent(this);

    }
}


class SellMessageToClient extends SellMessage {

    public SellMessageToClient(String sender, String seller, Item item) {
        super(sender, seller, item);
    }

    @Override
    public void process() {
        Cli.getInstance().market.sellMessages.add(this);
        Random rng = new Random();
        AnchorPane anchorPane = new AnchorPane();
        String style = String.format("-fx-background: rgb(%d, %d, %d);" +
                        "-fx-background-color: -fx-background;",
                rng.nextInt(256),
                rng.nextInt(256),
                rng.nextInt(256));
        anchorPane.setStyle(style);
        Label label = new Label(this.getItem().getItemInfo().getItemName());
        AnchorPane.setLeftAnchor(label, 5.0);
        AnchorPane.setTopAnchor(label, 5.0);
        Label label1 = new Label("Seller = " + this.seller);
        AnchorPane.setTopAnchor(label1, 30.0);
        AnchorPane.setRightAnchor(label1, 5.0);

    }
}


class GiftToServer extends Message {
    ArrayList<Item> items;

    public GiftToServer(String sender, ArrayList<Item> items) {
        super(sender);
        this.items = items;
    }

    @Override
    public void process() {
        for (Item item : items) {
            Market.getInstance().addItemToSell(new SellMessageToServer(Server.getInstance().serverClient.username, item));

        }
    }
}

class ClientAddedToServer extends Message implements Serializable {
    String Addedclient;
    String realName;
    int money;
    int level;

    public ClientAddedToServer(String sender, String addedclient, String realName, int money, int level) {
        super(sender);
        Addedclient = addedclient;
        this.realName = realName;
        this.money = money;
        this.level = level;
    }

    @Override
    public void process() {
        Cli.getInstance().knownClientHashMap.put(Addedclient, new Cli.Client(Addedclient, realName, level, money));
        if (Cli.getInstance().leaderBoard.isShown) {
            final Random rng = new Random();
            VBox content = Cli.getInstance().leaderBoard.content;
            if (content != null) {
                AnchorPane anchorPane = new AnchorPane();
                String style = String.format("-fx-background: rgb(%d, %d, %d);" +
                                "-fx-background-color: -fx-background;",
                        rng.nextInt(256),
                        rng.nextInt(256),
                        rng.nextInt(256));
                anchorPane.setStyle(style);
                anchorPane.setUserData(Cli.getInstance().knownClientHashMap.get(Addedclient));
                Label label = new Label(Addedclient);
                AnchorPane.setLeftAnchor(label, 5.0);
                AnchorPane.setTopAnchor(label, 5.0);
                Button button = new Button("Profile");
                Label nameLabel = new Label(Cli.getInstance().knownClientHashMap.get(Addedclient).username);


                Label teamGames = new Label("Games = " + Cli.getInstance().knownClientHashMap.get(Addedclient).getNumOfTeamGames());
                Label Deals = new Label("Deals = " + Cli.getInstance().knownClientHashMap.get(Addedclient).getNumOfDeals());
                Button pv = new Button("Message");
                AnchorPane.setTopAnchor(pv, 30.0);
                AnchorPane.setRightAnchor(pv, 5.0);
                pv.setOnAction(new EventHandler<ActionEvent>() {
                    boolean flag;
                    Stage stage;

                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if (!flag) {
                            stage = new Stage();
                            stage.setOnCloseRequest(windowEvent -> flag = false);
                            stage.setOnShown(windowEvent -> flag = true);
                            stage.setTitle("Message To " + Cli.getInstance().knownClientHashMap.get(Addedclient).username);
                            GridPane gridPane = new GridPane();
                            TextField textField = new TextField();
                            textField.setOnAction(actionEvent1 -> {
                                Message msg = new PvMessageToServer(Cli.getInstance().me.username, textField.getCharacters().toString(), Cli.getInstance().knownClientHashMap.get(Addedclient).username);
                                Cli.getInstance().messageSender.sendMessage(msg);

                                stage.close();
                                flag = false;
                            });
                            gridPane.add(textField, 0, 0);
                            Scene scene = new Scene(gridPane);
                            stage.setScene(scene);
                            stage.show();
                        }
                    }
                });

                AnchorPane.setTopAnchor(teamGames, 30.0);
                AnchorPane.setTopAnchor(Deals, 30.0);
                AnchorPane.setTopAnchor(nameLabel, 30.0);
                AnchorPane.setLeftAnchor(nameLabel, 5.0);
                AnchorPane.setLeftAnchor(teamGames, 105.0);
                AnchorPane.setLeftAnchor(Deals, 205.0);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    boolean flag;

                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if (flag == false) {
                            anchorPane.getChildren().addAll(nameLabel, teamGames, Deals, pv);
                            flag = true;
                        } else {
                            anchorPane.getChildren().removeAll(nameLabel, teamGames, Deals, pv);
                            flag = false;
                        }
                    }
                });
                AnchorPane.setRightAnchor(button, 5.0);
                AnchorPane.setTopAnchor(button, 5.0);


                anchorPane.getChildren().addAll(label, button);
                content.getChildren().add(anchorPane);
            }
        }
    }
}

class ClientRemoved extends Message {

    public ClientRemoved(String client) {
        super(client);
    }

    @Override
    public void process() {
        Cli.getInstance().knownClientHashMap.remove(sender);
    }

}


class FriendShipRequestToServer extends Message {
    String targetClient;

    public FriendShipRequestToServer(String sender, String targetClient) {
        super(sender);
        this.targetClient = targetClient;
    }

    @Override
    public void process() {
        Server.getInstance().clients.get(targetClient).sendMessage(new FriendShipRequestToClient(Server.getInstance().serverClient.username, sender, targetClient));

    }
}

class FriendShipRequestToClient extends Message {
    String requeter;
    String targetClient;

    public FriendShipRequestToClient(String sender, String requeter, String targetClient) {
        super(sender);
        this.requeter = requeter;
        this.targetClient = targetClient;
    }

    @Override
    public void process() {
        Stage stage = new Stage();
        GridPane gp = new GridPane();
        Button accept = new Button("Accept");
        Cli.Client client = Cli.getInstance().knownClientHashMap.get(requeter);
        Label label = new Label(client.username + "\n Level = " + client.Level + "\n Money = " + client.Money);
        gp.add(label, 0, 0);
        Button decline = new Button("Decline");
        accept.setOnAction(event -> {
            Cli.getInstance().messageSender.sendMessage(new FriendShipAnswerToServer(requeter, targetClient, true));
            stage.close();
            Cli.getInstance().me.addAllie(requeter);
        });
        decline.setOnAction(event -> {
            Cli.getInstance().messageSender.sendMessage(new FriendShipAnswerToServer(requeter, targetClient, false));

            stage.close();
        });
        gp.add(accept, 0, 1);
        gp.add(decline, 0, 2);
        stage.show();
        stage.setOnCloseRequest(windowEvent -> {
            Cli.getInstance().messageSender.sendMessage(new FriendShipAnswerToServer(requeter, targetClient, false));
        });


    }
}


class FriendShipAnswerToServer extends Message {
    String asker;
    boolean acceptance;


    public FriendShipAnswerToServer(String sender, String asker, boolean acceptance) {
        super(sender);
        this.asker = asker;
        this.acceptance = acceptance;
    }

    @Override
    public void process() {
        if (acceptance) {
            Server.getInstance().clients.get(sender).allies.add(Server.getInstance().clients.get(asker));
            Server.getInstance().clients.get(asker).allies.add(Server.getInstance().clients.get(asker));
            for (Map.Entry<String, Server.Client> entry : Server.getInstance().clients.entrySet()) {
                if (!entry.getKey().equals(sender)) {
                    entry.getValue().sendMessage(new AlliesAdded(Server.getInstance().serverClient.username, sender, asker));
                }
            }
        } else {
            Server.getInstance().clients.get(asker).sendMessage(new FriendShipRejected(Server.getInstance().serverClient.username, sender));
        }

    }


}
/*

class FriendShipAnswerToClient extends Message {
    String asker;
    boolean acceptance;

    @Override
    public void process() {
        if (acceptance) {
            Cli.getInstance().allies.add(Cli.getInstance().knownClientHashMap.get(client));
        }
    }
}
*/

class AlliesAdded extends Message {
    String friend1;
    String friend2;

    public AlliesAdded(String sender, String friend1, String friend2) {
        super(sender);
        this.friend1 = friend1;
        this.friend2 = friend2;
    }

    @Override
    public void process() {
        Cli.getInstance().knownClientHashMap.get(friend1).allies.add(Cli.getInstance().knownClientHashMap.get(friend2));
        Cli.getInstance().knownClientHashMap.get(friend2).allies.add(Cli.getInstance().knownClientHashMap.get(friend1));

    }
}

class AlliesRemovedToServer extends Message {

    String theOther;

    public AlliesRemovedToServer(String sender, String theOther) {
        super(sender);
        this.theOther = theOther;
    }

    @Override
    public void process() {
        Server.getInstance().clients.get(sender).allies.remove(Server.getInstance().clients.get(theOther));
        Server.getInstance().clients.get(theOther).allies.remove(Server.getInstance().clients.get(sender));
        for (Map.Entry<String, Server.Client> entry : Server.getInstance().clients.entrySet()) {
            if (!entry.getKey().equals(sender)) {
                entry.getValue().sendMessage(new AlliesRemoved(Server.getInstance().serverClient.username, sender, theOther));
            }
        }

    }
}
/*

class AlliesRemovedToClient extends Message {
    String theNewEnemy;

    @Override
    public void process() {
        Server.getInstance().clients.get(theNewEnemy).allies.remove(Cli.getInstance().me);
        Cli.getInstance().me.allies.remove(Server.getInstance().clients.get(theNewEnemy));


    }
}
*/

class AlliesRemoved extends Message {
    String friend2;
    String friend1;

    public AlliesRemoved(String sender, String friend2, String friend1) {
        super(sender);
        this.friend2 = friend2;
        this.friend1 = friend1;
    }

    @Override
    public void process() {
        Cli.getInstance().knownClientHashMap.get(friend1).allies.remove(Cli.getInstance().knownClientHashMap.get(friend2));
        Cli.getInstance().knownClientHashMap.get(friend2).allies.remove(Cli.getInstance().knownClientHashMap.get(friend1));


    }
}


class Persistance {
    String ChatRoomDataPath;
    String ClientsDataPath;
    String MarketDataPath;


    public void chatRoomMessageAdded(ChatMessage chatMessage) {

    }


}


class TeamGame {

    ChatRoom gameChatRoom;
    TeamGameMission mission;

}


class TeamGameMission {


}

/*class Main extends Application {
    static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {


    }
}*/


class FriendShipRejected extends Message {
    String rejecter;

    public FriendShipRejected(String sender, String rejecter) {
        super(sender);
        this.rejecter = rejecter;
    }

    @Override
    public void process() {
        Stage stage = new Stage();
        GridPane gp = new GridPane();
        gp.add(new Label("Your FriendShip Reguest To " + rejecter + "Was Rejected"), 0, 0);
        stage.setScene(new Scene(gp));
        stage.show();

    }
}


/*class ServerChatRoom extends ChatRoom {

    @Override
    public void addMessage(ChatMessage mesasge) {
        super.addMessage(mesasge);
        for (Map.Entry<String, Server.Client> entry : Server.getInstance().clients.entrySet()) {
            entry.getValue().sendMessage(mesasge);
        }
    }

}*/
class UpdateMessage extends Message {
    int level;
    int money;

    public UpdateMessage(String sender, int level, int money) {
        super(sender);
        this.level = level;
        this.money = money;
    }

    @Override
    public void process() {
        Server.Client client = Server.getInstance().clients.get(sender);
        client.level = level;
        client.currentMoney = money;
    }

}


class CliLeaderboard {
    boolean isShown;
    boolean isServer = false;
    VBox content;
    private String sortType;

    public void update(String sortType, Stage stage) {
        this.sortType = sortType;

        if (sortType.equals("Level")) {
            ArrayList<Cli.Client> list = new ArrayList<>(Cli.getInstance().knownClientHashMap.values());
            list.sort(Comparator.comparingInt(client -> client.Money));
            show(list, stage);


        } else {
            ArrayList<Cli.Client> list = new ArrayList<>(Cli.getInstance().knownClientHashMap.values());
            list.sort(Comparator.comparingInt(client -> client.Level));
            show(list, stage);

        }

    }


    public void show(ArrayList<Cli.Client> clients, Stage primaryStage) {
        final Random rng = new Random();
        primaryStage.setOnCloseRequest(windowEvent -> content = null);
        content = new VBox(5);
        ScrollPane scroller = new ScrollPane(content);
        scroller.setFitToWidth(true);
        Button addButton = new Button("Add");
        isShown = true;
        new Thread(() -> this.reSort(content));
        primaryStage.setOnCloseRequest(windowEvent -> isShown = false);
        for (Cli.Client client : clients) {
            AnchorPane anchorPane = new AnchorPane();
            String style = String.format("-fx-background: rgb(%d, %d, %d);" +
                            "-fx-background-color: -fx-background;",
                    rng.nextInt(256),
                    rng.nextInt(256),
                    rng.nextInt(256));
            anchorPane.setStyle(style);
            anchorPane.setUserData(client);
            Label label = new Label(client.username);
            AnchorPane.setLeftAnchor(label, 5.0);
            AnchorPane.setTopAnchor(label, 5.0);
            Button button = new Button("Profile");
            Label nameLabel = new Label(client.username);


            Label teamGames = new Label("Games = " + client.getNumOfTeamGames());
            Label Deals = new Label("Deals = " + client.getNumOfDeals());
            Button pv = new Button("Message");
            AnchorPane.setTopAnchor(pv, 30.0);
            AnchorPane.setRightAnchor(pv, 5.0);
            pv.setOnAction(new EventHandler<ActionEvent>() {
                boolean flag;
                Stage stage;

                @Override
                public void handle(ActionEvent actionEvent) {
                    if (!flag) {
                        stage = new Stage();
                        stage.setOnCloseRequest(windowEvent -> flag = false);
                        stage.setOnShown(windowEvent -> flag = true);
                        stage.setTitle("Message To " + client.username);
                        GridPane gridPane = new GridPane();
                        TextField textField = new TextField();
                        textField.setOnAction(actionEvent1 -> {
                            if (isServer) {
                                Message msg = new PvMessageToClient(Server.getInstance().serverClient.username, Server.getInstance().serverClient.username, textField.getCharacters().toString());
                                Server.getInstance().clients.get(client.username).sendMessage(msg);
                            } else {
                                Message msg = new PvMessageToServer(Cli.getInstance().me.username, textField.getCharacters().toString(), client.username);
                                Cli.getInstance().messageSender.sendMessage(msg);

                            }


                            //dsfhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhiiiiiiiiiiiiiii
                            ///
                            //
                            //
                            //
                            //
                            //

                            stage.close();
                            flag = false;
                        });
                        gridPane.add(textField, 0, 0);
                        Scene scene = new Scene(gridPane);
                        stage.setScene(scene);
                        stage.show();
                    }
                }
            });

            AnchorPane.setTopAnchor(teamGames, 30.0);
            AnchorPane.setTopAnchor(Deals, 30.0);
            AnchorPane.setTopAnchor(nameLabel, 30.0);
            AnchorPane.setLeftAnchor(nameLabel, 5.0);
            AnchorPane.setLeftAnchor(teamGames, 105.0);
            AnchorPane.setLeftAnchor(Deals, 205.0);
            button.setOnAction(new EventHandler<ActionEvent>() {
                boolean flag;

                @Override
                public void handle(ActionEvent actionEvent) {
                    if (!flag) {
                        anchorPane.getChildren().addAll(nameLabel, teamGames, Deals, pv);
                        flag = true;
                    } else {
                        anchorPane.getChildren().removeAll(nameLabel, teamGames, Deals, pv);
                        flag = false;
                    }
                }
            });
            AnchorPane.setRightAnchor(button, 5.0);
            AnchorPane.setTopAnchor(button, 5.0);


            anchorPane.getChildren().addAll(label, button);
            content.getChildren().add(anchorPane);
        }
        Scene scene = new Scene(new BorderPane(scroller, null, null, addButton, null), 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    private void reSort(VBox content) {
        Object object = new Object();

        while (isShown) {
            if (sortType.equals("Level")) {

                content.getChildren().sort((client, t1) -> {
                    if (((Cli.Client) client.getUserData()).Level == ((Cli.Client) t1.getUserData()).Level) {
                        return 0;
                    } else if (((Cli.Client) client.getUserData()).Level > ((Cli.Client) t1.getUserData()).Level) {
                        return 1;
                    } else {
                        return -1;
                    }

                });
            } else {

                content.getChildren().sort((client, t1) -> {
                    if (((Cli.Client) client.getUserData()).Money == ((Cli.Client) t1.getUserData()).Money) {
                        return 0;
                    } else if (((Cli.Client) client.getUserData()).Money > ((Cli.Client) t1.getUserData()).Money) {
                        return 1;
                    } else {
                        return -1;
                    }

                });


                synchronized (object) {
                    try {
                        object.wait(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

        }


    }


}
/*
class BuyMessageToClients extends Message{
    Item item;
    String buyer;

}*/
