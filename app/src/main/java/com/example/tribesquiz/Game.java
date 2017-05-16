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

        questionsList.add("האלוהים שהמוסלמים עובדים זהים ליהודים ולנוצרים");
        answersList[1] = 1;

        questionsList.add("המוסלמים מתפללים לירח");
        answersList[2] = 0;

        questionsList.add("ביהדות יש 4 ימי ראש השנה");
        answersList[3] = 1;

        questionsList.add("חילוני הוא אדם אשר לא מאמין באלוהים");
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
