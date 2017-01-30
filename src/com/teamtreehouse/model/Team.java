package com.teamtreehouse.model;

/**
 * Created by oussou on 1/30/2017.
 */
import java.io.Serializable;
import java.util.*;

public class Team  implements Comparable<Team>, Serializable{
    private static final long serialVersionUID = 1L;
    private String mTeamName;
    private String mCoach;
    public  Set<Player>mPlayers;


    //add constructor and initialize variables
    public Team(String teamName,String coach){
        mTeamName=teamName;
        mCoach=coach;
        mPlayers = new TreeSet<Player>();
    }

    public String getTeamName(){
        return mTeamName;
    }
    public String getCoach(){
        return mCoach;
    }

    //add player in different height's ranges
    public Map<String,List<Player>> heightRep(){
        Map<String,List<Player>>heightRep=new TreeMap<>();
        List<Player>playRep=new ArrayList<>();
        playRep.addAll(mPlayers);

        for(Player play:playRep){
            if(play.getHeightInInches()>=35 && play.getHeightInInches()<=40){
                List<Player>range1=heightRep.get("35-40");
                if(range1==null){
                    range1=new ArrayList<>();
                    heightRep.put("35-40",range1);
                }
                range1.add(play);

            }

            if(play.getHeightInInches()>=41 && play.getHeightInInches()<=46){
                List<Player>range2=heightRep.get("41-46");
                if(range2==null){
                    range2=new ArrayList<>();
                    heightRep.put("41-46",range2);
                }
                range2.add(play);
            }

            if(play.getHeightInInches()>=47 && play.getHeightInInches()<=50){
                List<Player>range3=heightRep.get("47-50");
                if(range3==null){
                    range3=new ArrayList<>();
                    heightRep.put("47-50",range3);
                }
                range3.add(play);
            }
        }
        return heightRep;
    }
    //categorize players by experience
    public Map<String,List<Player>>ExperienceRep(){
        int countExp=0;
        int countInexp=0;
        double percentExp=0;
        Map<String,List<Player>>ExperienceRep=new TreeMap<>();
        List<Player>playExp=new ArrayList<>();
        playExp.addAll(mPlayers);
        for(Player play:playExp){
            if(play.isPreviousExperience()){
                List<Player> exp=ExperienceRep.get("Experienced");
                if(exp==null){
                    exp = new ArrayList<>();
                    ExperienceRep.put("Experienced",exp);
                }
                exp.add(play);
                countExp++;
            }else{
                List<Player> inexp=ExperienceRep.get("Inexperienced");
                if(inexp==null){
                    inexp=new ArrayList<>();
                    ExperienceRep.put("Inexperienced",inexp);
                }
                inexp.add(play);
                countInexp++;
            }
        }

        percentExp=(countExp/(double)(countExp+countInexp))*100;
        System.out.printf("Number of experienced players: %s%n",countExp);
        System.out.printf("Number of inexperienced players: %s%n",countInexp);
        System.out.printf("The percent of experienced players in the team: %.2f%%%n%n",percentExp);
        return ExperienceRep;


    }

    //print the list of players in a team
    public void printRoster(){
        for(Player printplayer: mPlayers){
            System.out.printf("%s %s%n",printplayer.getLastName(),printplayer.getFirstName());
        }
    }

    @Override
    public int compareTo(Team other) {
        if(equals(other)){
            return 0;
        }

        return mTeamName.compareTo(other.mTeamName);
    }
    @Override
    public String toString(){
        return String.format("%s coach: %s",mTeamName,mCoach);
    }
}
