/**
 * Created by oussou on 1/30/2017.
 */
import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.TeamBuilder;
import java.io.IOException;
public class LeagueManager {

    public static void main(String[] args) {
        Player[] players = Players.load();
        System.out.printf("There are currently %d registered players.%n", players.length);
        // Your code here!
        TeamBuilder tm= new TeamBuilder();
        try{
            tm.chooseOption();
        }catch(IOException ioe){
            System.out.println("Ooops there is a problem with your input");
            ioe.printStackTrace();
        }
    }


}

