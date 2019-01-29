package controller;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Cli {
    static Cli ourInstance;
    String ServerIp;
    int ServerPort;
    String name;
    int CurrentMoney;
    int Level;
    Socket socket;
    ClientServerRelation clientServerRelation;
    MessageSender messageSender;
    ChatRoom chatRoom;
    LeaderBoard leaderBoard;
    Market market;
    MessageReciever messageReciever;
    HashMap<String, Client> knownClientHashMap = new HashMap<>();
    ArrayList<Client> allies = new ArrayList<>();
    Client me;

    public Cli(String serverIp, int serverPort, String name, int currentMoney, int level) {
        ServerIp = serverIp;
        ServerPort = serverPort;
        this.name = name;
        CurrentMoney = currentMoney;
        Level = level;
        boolean flag = false;

        if (this.connectToServer()) {
            flag = true;
        } else {
            this.couldntConnect();
        }

        if (flag) {
            clientServerRelation = new ClientServerRelation(socket);
            messageReciever = new MessageReciever(clientServerRelation);
            messageSender = new MessageSender(clientServerRelation);
            new Thread(messageReciever).start();
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
            Cli.getInstance().me.username = tf.getCharacters().toString();
            Cli.getInstance().name = tf.getCharacters().toString();
            Cli.getInstance().messageSender.sendMessage(new StartMessage(Cli.getInstance().me.username,Cli.getInstance().me.name,Cli.getInstance().me.Level,Cli.getInstance().me.Money));

        });

    }

    private void couldntConnect() {
        ClientMenu();


    }

    public static void ClientMenu(){
        GridPane gridPane = new GridPane();
        TextField ip = new TextField("Ip");
        TextField port = new TextField("Port");
        Button enter = new Button("Enter");
        enter.setOnAction(event -> {
            try {
                Cli.getInstance().ServerIp =ip.getCharacters().toString();
                Cli.getInstance().ServerPort = Integer.parseInt(port.getCharacters().toString());

            }catch (Exception ignored){
                ip.setText("");
                port.setText("");
            }

        });
        gridPane.add(ip,0,0);
        gridPane.add(port,0,1);
        gridPane.add(enter,0,2);

        Main.stage.setScene(new Scene(gridPane));

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


        public Client(String username, String name, int level, int money) {
            this.username = username;
            this.name = name;
            Level = level;
            Money = money;
        }

        public void addAllie(String requeter) {
            allies.add(Cli.getInstance().knownClientHashMap.get(requeter));


        }
    }


}