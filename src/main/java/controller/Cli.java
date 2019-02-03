package controller;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Cli {
    static Cli ourInstance ;
    public String ServerIp;
    public int ServerPort;
    public String name;
    public int CurrentMoney;
    public int Level;
    public Socket socket;
    ClientServerRelation clientServerRelation;
    public MessageSender messageSender;
    ChatRoom chatRoom = new ChatRoom(false);
    Market market;
    MessageReceiever messageReceiever;
    HashMap<String, Client> knownClientHashMap = new HashMap<>();
    ArrayList<Client> allies = new ArrayList<>();
    Client me;
    CliLeaderboard leaderBoard= new CliLeaderboard();


    public Cli(String serverIp, int serverPort, String name, int currentMoney, int level) {
        ServerIp = serverIp;
        ServerPort = serverPort;
        this.name = name;
        CurrentMoney = currentMoney;
        Level = level;
        me = new Client(name,name,level,currentMoney);
        boolean flag = false;

        if (this.connectToServer()) {
            flag = true;
        } else {
            this.couldntConnect();
        }

        if (flag) {
            clientServerRelation = new ClientServerRelation(socket);
            messageReceiever = new MessageReceiever(clientServerRelation);
            messageSender = new MessageSender(clientServerRelation);
            messageSender.sendMessage(new StartMessage(me.username,me.name,me.Level,me.Money));
            this.knownClientHashMap.put(me.username,me);
            new Thread(messageReceiever).start();
            new Thread(messageSender).start();
        }
    }

    public static void  getAnotherClientName() {
        Stage stage = new Stage();
        GridPane gp  = new GridPane();
        stage.setTitle("New Username Needed");
        TextField tf = new TextField("username");
        gp.add(tf,0,0);
        tf.setOnAction(event -> {
            Cli.getInstance().knownClientHashMap.remove(Cli.getInstance().me.username);
            Cli.getInstance().me.username = tf.getCharacters().toString();
            Cli.getInstance().name = tf.getCharacters().toString();
            Cli.getInstance().messageSender.sendMessage(new StartMessage(Cli.getInstance().me.username,Cli.getInstance().me.name,Cli.getInstance().me.Level,Cli.getInstance().me.Money));
            Cli.getInstance().knownClientHashMap.put(Cli.getInstance().me.username,Cli.getInstance().me);
        });

    }



    private void couldntConnect() {
        ClientMenu();


    }

    public static void ClientMenu(){
        GridPane gridPane = new GridPane();
        TextField ip = new TextField("Ip");
        TextField port = new TextField("Port");
        TextField name = new TextField("Name");
        Button enter = new Button("Enter");
        enter.setOnAction(event -> {
            try {
                Cli.ourInstance =new Cli(ip.getCharacters().toString(),Integer.parseInt(port.getCharacters().toString()),name.getCharacters().toString(),0,0);
                Cli.getInstance().show(Main.stage);

            }catch (Exception ignored){
                ip.setText("");
                port.setText("");
            }

        });
        gridPane.add(ip,0,0);
        gridPane.add(name,0,1);
        gridPane.add(port,0,2);
        gridPane.add(enter,0,3);

        Main.stage.setScene(new Scene(gridPane));

    }

    private void show(Stage stage) {

        GridPane  gp =new GridPane();
        Button chatroom = new Button("Chat Room");
        Button leaderboard = new Button("LeaderBoard");
        Button people = new Button("People");
        Button market = new Button("Market");
        chatroom.setOnAction(event -> Cli.getInstance().chatRoom.show());
        leaderboard.setOnAction(event -> {
            Stage stage1 = new Stage();
            GridPane gridPane = new GridPane();
            Button level = new Button("level");
            level.setOnAction(event1 -> {
                this.leaderBoard.update("Level",stage1);

            });
            Button money = new Button("money");
            money.setOnAction(event1 -> {
                this.leaderBoard.update("Money",stage1);
            });
            gridPane.addRow(2,level,money);
            stage1.setScene(new Scene(gridPane));
            stage1.show();
        });
        gp.addRow(3,chatroom,leaderboard,people,market);
        stage.setScene(new Scene(gp));
    }

    public static Cli getInstance() {
        return ourInstance;
    }

    private boolean connectToServer() {
        try {
            socket = new Socket(ServerIp, ServerPort);



            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    static class Client {
        String username;
        String name;
        int Level;
        int Money;
        ArrayList<Client> allies = new ArrayList<>();
        private int numOfTeamGames;
        private int numOfDeals;


        public Client(String username, String name, int level, int money) {
            this.username = username;
            this.name = name;
            Level = level;
            Money = money;
        }

        public void addAllie(String requeter) {
            allies.add(Cli.getInstance().knownClientHashMap.get(requeter));


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


}