package com.example.tribesquiz;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jonathan on 16/05/2017.
 *
 * A Mock Database for the application
 */

public class Database {
    ArrayList<Player> players = new ArrayList<Player>();
    ArrayList<Question> questions = new ArrayList<Question>();


    /**
     * Database constructor
     */
    public Database(){
        players.add(new Player("Mustafa","Ali",26,"Arad",Marriage.NOTMARRIED,0,"Islam","054851085","mustf@taf.co.il",150,"00:06:66:43:45:7F",new Date(2017,3,1)));
        players.add(new Player("Dana","Berger",37, "Herzelya",Marriage.SEPERATED,0,"Jewish","054346085","dana@han.co.il",57,"98:D3:31:20:B6:37",new Date(2017,3,2)));



        questions.add(new Question("האם פלאפל הוא מאכל יהודי",0));
        questions.add(new Question("האלוהים שהמוסלמים עובדים זהה ליהודים ולנוצרים",1));
        questions.add(new Question("המוסלמים מתפללים לירח",0));
        questions.add(new Question("ביהדות יש 4 ימי ראש השנה",1));
        questions.add(new Question("חילוני הוא אדם אשר לא מאמין באלוהים",0));

    }

    /**
     * Player inner class, represents a player in the database
     */
    public class Player{
        private String firstName;
        private String lastName;
        private int age;
        private String city;
        private Marriage married;
        private int numOfChildren;
        private String religion;
        private String phoneNumber;
        private String email;
        private int coins;
        private String macAddress;
        private Date lastPlayed;

        Player(String firstName,String lastName ,int age,  String city,
               Marriage married , int numOfChildren , String religion,
               String phoneNumber, String email , int coins, String macAddress, Date lastPlayed){

            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.city = city;
            this.married = married;
            this.numOfChildren = numOfChildren;
            this.religion = religion;
            this.phoneNumber = phoneNumber;
            this.email = email;
            this.coins = coins;
            this.macAddress = macAddress;
            this.lastPlayed = lastPlayed;
        }
    }

    /**
     * Question inner class
     * represents a question and its correct answer
     */
    public class Question{
        private String questionString;
        private int correctAnswer;

        public Question(String qString, int cAnswer){
            questionString = qString;
            correctAnswer = cAnswer;
        }

        public String getQuestionString(){
            return questionString;
        }

        public int getAnswer(){
            return correctAnswer;
        }
    }

    /**
     * A Marriage ENUM for use in player
     */
    public enum Marriage{
        MARRIED,SEPERATED,DIVORCED,NOTMARRIED;
    }


    /**
     *
     * @return
     *  All the questions in the database
     */
    public ArrayList<Database.Question> getQuestions(){
        return questions;
    }

    /**
     *
     * @return
     *  All the players in the database
     */
    public ArrayList<Database.Player> getPlayers(){
        return players;
    }

}

