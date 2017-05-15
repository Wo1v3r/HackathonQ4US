package com.example.tribesquiz;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jonathan on 15/05/2017.
 */

public class Game {
    private static final int TOTALQUESTIONS = 5;
    private int currentQuestionNum;
    private String currentQuestion;
    private int correctAnswers;
    private ArrayList<String> questionsList = new ArrayList<String>();
    private int[] answersList = new int[5];


    public Game(){
        currentQuestionNum = 1;
        questionsList.add("האם פלאפל הוא מאכל יהודי");
        answersList[0] = 0;

        questionsList.add("האם ג'חנון הוא מאכל תימני");
        answersList[1] = 1;

        questionsList.add("האם ערבים חייבים לחבוש כאפייה");
        answersList[2] = 0;

        questionsList.add("האם ביהדות מתפללים פעם אחת ביום");
        answersList[3] = 0;

        questionsList.add("האם ירושלים היא עיר קדושה רק לנצרות");
        answersList[4] = 0;

    }

    public String getNextQuestion(){
        currentQuestionNum++;
        if (currentQuestionNum > questionsList.size()) return "";

        return questionsList.get(currentQuestionNum-1);
    }

    public boolean checkAnswer(int ans){
        if (answersList[currentQuestionNum -1] == ans) {
            correctAnswers++;
            return true;
        }
        return false;
    }
    public int getCurrentQuestionNum(){
        return currentQuestionNum;
    }

    public int getTotalquestions(){
        return TOTALQUESTIONS;
    }

    public int getCorrectAnswers(){
        return correctAnswers;
    }
}
