package Model.GameMenu.Missions;

import Model.Item;
import controller.InputProcessor;

import java.util.ArrayList;
import java.util.HashSet;

public class Mission {
    public static final String MissionsConfigFilePath = "./MissionsConfigFile.json";
    //private static HashSet<Mission> missions = new HashSet<>(0);
    public Goal goal;

   /* static {
        try {
            Gson gson = new Gson();
            FileReader fileReader = new FileReader(MissionsConfigFilePath);
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNext()) {
                String string = scanner.nextLine();
                missions.add(gson.fromJson(string, Mission.class));
            }
        } catch (FileNotFoundException e) {
            MissionView.FileNotFoundException();
        } catch (JsonSyntaxException e) {
            MissionView.JsonSyntaxException();
        }


    }

 */


    public Mission(Goal goal) {
        this.goal = goal;
    }

    /*public static Mission findMission(int ID) {
        for (Mission mission : missions) {
            if (mission.getID() == ID) {
                return mission;
            }
        }
        return null;
    }*/



    public boolean isSatisfied() {
        for (Goal.EE ee : goal.ees) {
            if (!ee.isSatisfied()) {
                return false;
            }
        }
        return true;
    }


    public static class Goal {
        ArrayList<EE> ees;

        public Goal(ArrayList<EE> ees) {
            this.ees = ees;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(0);
            for (EE ee : ees) {
                stringBuilder.append(ee.toString() + "\n");
            }
            return stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
        }

        public static class EE {
            public Item.ItemInfo itemInfo;
            public Integer neededNumber;

            public EE(Item.ItemInfo itemInfo, Integer neededNumber) {
                this.itemInfo = itemInfo;
                this.neededNumber = neededNumber;
            }

            @Override
            public String toString() {
                return itemInfo.getItemName() + ", needed number = " + neededNumber + ", current number = " + InputProcessor.game.getFarm().findNumberOfItemX(itemInfo);
            }

            public boolean isSatisfied() {
                if (InputProcessor.game.getFarm().findNumberOfItemX(itemInfo) < neededNumber) {
                    return false;
                } else {
                    return true;
                }
            }
        }


    }
}
