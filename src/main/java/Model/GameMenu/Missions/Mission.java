package Model.GameMenu.Missions;

import View.GameMenu.Missions.MissionView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Mission {
    public static final String MissionsConfigFilePath = "./MissionsConfigFile.json";
    private static HashSet<Mission> missions =new HashSet<>(0);

    static {
        try {
            Gson gson=new Gson();
            FileReader fileReader =new FileReader(MissionsConfigFilePath);
            Scanner scanner =new Scanner(fileReader);
            while (scanner.hasNext()){
                String string = scanner.nextLine();
                missions.add(gson.fromJson(string,Mission.class));
            }
        } catch (FileNotFoundException e) {
            MissionView.FileNotFoundException();
        }catch (JsonSyntaxException e){
            MissionView.JsonSyntaxException();
        }


    }

    String goal;
    int level; //for customs -1
    int ID;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isSatisfied(){
        return true;
    }




    public static boolean findMission(int ID){
        for (Mission mission:missions){
            if (mission.getID()==ID){
                return mission;
            }
        }
        return null;
    }
}
