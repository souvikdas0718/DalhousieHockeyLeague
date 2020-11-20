package dhl.businessLogic.GameSimulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameSimulation {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        List<Integer> l = selectPlayersForAShift(list);
    }
    public static void test(){
        int shifts = 40;
        int players = 20;
        int goalies = 2;
        int forwards = 9;
        int defensemen = 9;


        for (int i=0; i<shifts; i++){

            int shotsInAShift =1;
            if(i%2==0)
            {
                shotsInAShift =2;
            }
        }
    }

    public static List<Integer> selectPlayersForAShift(List<Integer> oldPlayers){
        List<Integer> newPlayers = new ArrayList<Integer>();

        for(int i=0;i<oldPlayers.size();i++) {
            newPlayers.add(gen(oldPlayers));
        }

        for (int j = 0; j < newPlayers.size(); j++) {
            newPlayers.add(gen(newPlayers));
        }

        return newPlayers;
    }

    public static Integer gen(List<Integer> oldPlayers){
        Integer newRandomNumber = generateR();
        for (int i=0; i<oldPlayers.size(); i++) {
            if (newRandomNumber == oldPlayers.get(i)) {
                gen(oldPlayers);
            }
        }
        return newRandomNumber;
    }

    public static Integer generateR() {
        Random random = new Random();
        Integer randomNumber = random.nextInt(10 - 1 + 1) + 1;
        return randomNumber;
    }
    public static int generateRandomNumber(Integer oldNumber){
        Random random = new Random();
        Integer randomNumber = random.nextInt(10 - 1 + 1) + 1;

        if(oldNumber == randomNumber){
            generateRandomNumber(oldNumber);
        }
        Integer i = randomNumber;
        return randomNumber;
    }
}
