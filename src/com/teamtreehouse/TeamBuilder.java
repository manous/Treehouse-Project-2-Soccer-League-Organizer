package com.teamtreehouse;

/**
 * Created by oussou on 1/30/2017.
 */
import com.teamtreehouse.model.Player;
import com.teamtreehouse.model.Players;
import com.teamtreehouse.model.Team;

import java.io.IOException;
import java.util.*;

public class TeamBuilder{
    private Team team;
    public List<Player>loadPlayers=new ArrayList<Player>(Arrays.asList(Players.load()));
    private List<Team>teams;
    private Map<String,String>menu;
    public static final int Max_player=11;
    Scanner scan= new Scanner(System.in);

    //constructor
    public TeamBuilder(){
        teams= new ArrayList<Team>();

        menu= new LinkedHashMap<String,String>();
        menu.put("Create "," create a new team");
        menu.put("Add "," add players to a team");
        menu.put("Remove "," remove players from a team");
        menu.put("HR "," print height report");
        menu.put("ER "," print balance report");
        menu.put("Roster "," print roster");
        menu.put("Quit "," exit the program");
    }


    //display menu
    public String promptForChoice()throws IOException{
        System.out.println("********************************");
        for(Map.Entry<String,String>choice: menu.entrySet()){
            System.out.printf("%s-%s %n",
                    choice.getKey(),choice.getValue() );}
        System.out.println("********************************");
        String input = scan.next();
        return input.trim().toLowerCase();

    }


    //choose a an operation to be performed on the team
    public void chooseOption()throws IOException {
        String input="";
        do{
            try{
                input=promptForChoice();
                switch(input){
                    case "create":
                        Team newTeam=promptForTeamInfo();
                        createTeam(newTeam);
                        break;
                    case "add":
                        Team addToTeam= DisplayTeamList();
                        addPlayerToTeam(addToTeam);
                        break;
                    case "remove":
                        Team removeFromTeam= DisplayTeamList();
                        removePlayerFromTeam(removeFromTeam);
                        break;
                    case "hr":
                        heightReport();
                        break;
                    case "er":
                        balanceReport();
                        break;
                    case "roster":
                        roster();
                        break;
                    case "quit":
                        System.out.println("Thanks! See ya");
                        break;
                    default:
                        System.out.printf("non valid choice: '%s'.Try again.%n",input);
                        break;
                }

            }catch(IOException ioe){
                System.out.println("invalid input");
                throw new IOException();
            }


        }while(!input.equals("quit"));
    }


    //prompt for team and coach names
    private Team promptForTeamInfo()throws IOException{
        System.out.printf("Please enter the team name: ");
        String teamName= scan.next();
        System.out.printf("Please enter the coach name: ");
        String coach= scan.next();
        return new Team(teamName,coach);
    }


    //display teams'list
    private Team DisplayTeamList()throws IOException{
        if(teams.size()==0){
            System.out.println("the team list is empty");
            System.exit(1);
        }
        System.out.println("available teams:");
        int index =promptForTeamIndex(teams);
        return teams.get(index);
    }


    //add player to a team
    private void addPlayerToTeam(Team team)throws IOException{
        Set<Player>playersPool= new TreeSet<>(loadPlayers);
        Collections.sort(loadPlayers);
        int index = promptForPlayerIndex(playersPool);
        if(team.mPlayers.contains(loadPlayers.get(index))){
            System.out.printf("The player is already in the team: %s %n ",team);
        }
        if(team.mPlayers.size() <Max_player){
            team.mPlayers.add(loadPlayers.get(index));
            loadPlayers.remove(loadPlayers.get(index));
            System.out.printf("The player is added to the team %s%n",team);}
        else{
            System.out.println("The team is full. Sorry you can't add more than 11 players per team");
        }
    }


    //remove a player from a team
    private void removePlayerFromTeam(Team team)throws IOException{
        if(team.mPlayers.isEmpty()){
            System.out.println("the team is empty");
            System.exit(1);
        }
        List<Player>convertSet=new ArrayList<>(team.mPlayers);
        Collections.sort(convertSet);
        int index = promptForPlayerIndex(team.mPlayers);
        if(team.mPlayers.contains(convertSet.get(index))){
            team.mPlayers.remove(convertSet.get(index));
            loadPlayers.add(convertSet.get(index));
            System.out.printf("The player is removed from the team %s%n",team);
        }else{
            System.out.printf("The player is not in the team: %s %n ",team);
        }
    }

    //create a team
    public void createTeam(Team team){
        int remainPlayers=loadPlayers.size()-(teams.size()*team.mPlayers.size());
        if(remainPlayers>=Max_player){
            teams.add(team);
            System.out.printf("team created%n");
        }else{
            System.out.println("Sorry!You can't create more teams than there are available players");
        }
    }

    //prompt the user to choose a team
    private int promptForTeamIndex(List<Team>choices)throws IOException{
        boolean isAcceptable=false;
        int count=1;
        int selected=0;
        Collections.sort(choices);
        for(Team choice:choices){
            System.out.printf("%d.) %s%n",count,choice);
            count++;
        }

        do{
            try{
                String makeChoice=scan.next();
                selected=Integer.parseInt(makeChoice.trim());
                if(selected>choices.size()){
                    throw new IndexOutOfBoundsException("your choice doesn't match any team");
                }
                isAcceptable=true;
            }catch(IndexOutOfBoundsException e){
                System.out.printf("Oops! the team not in the list. Please try again. %n");
            }catch(IllegalArgumentException iae){
                System.out.printf("invalid input. Please try again. %n");
            }}while(!isAcceptable);
        return selected-1;

    }

    //prompt the user to choose a player
    private int promptForPlayerIndex(Set<Player>selection)throws IOException{
        boolean isAcceptable=false;
        int selected=0;
        int count=1;
        for(Player choice:selection){
            System.out.printf("%d.) %s%n",count,choice);
            count++;
        }
        do{
            try{
                String makeChoice=scan.next();
                selected=Integer.parseInt(makeChoice.trim());
                if(selected>selection.size()){
                    throw new IndexOutOfBoundsException("your choice doesn't match any player");
                }
                isAcceptable=true;
            }catch(IndexOutOfBoundsException e){
                System.out.printf("Oops! the player not in the list. Please try again. %n");
            }catch(IllegalArgumentException iae){
                System.out.printf("invalid input. Please try again. %n");
            }}while(!isAcceptable);
        //String makeChoice=scan.next();
        //int selected=Integer.parseInt(makeChoice.trim());
        return selected-1;

    }


    // print the height report
    private void heightReport(){
        for(Team team:teams){
            if(team.mPlayers.isEmpty()){
                System.out.println("the team is empty");
                System.exit(1);
            }
            System.out.printf("%s:%n",team);
            for(Map.Entry<String,List<Player>>display:team.heightRep().entrySet()){
                System.out.printf("%s - %s %n",
                        display.getKey(),
                        display.getValue());
            }
        }
    }


    //print the balance report
    private void balanceReport(){
        for(Team team:teams){
            if(team.mPlayers.isEmpty()){
                System.out.println("the team is empty");
                System.exit(1);
            }
            System.out.printf("%s:%n",team);
            for(Map.Entry<String,List<Player>>display:team.ExperienceRep().entrySet()){
                System.out.printf("%s - %s %n",
                        display.getKey(),
                        display.getValue());
            }

        }
    }

    //select team and  display player
    public void roster()throws IOException{
        if(teams.size()==0){
            System.out.println("the team list is empty");
            System.exit(1);
        }
        System.out.println("available teams:");
        int index =promptForTeamIndex(teams);
        teams.get(index).printRoster();
    }

}
