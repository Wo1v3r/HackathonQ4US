package com.example.tribesquiz;

import android.provider.ContactsContract;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jonathan on 15/05/2017.
 * A Game class which is initiated after two players logged in
 */

public class Game {
    private static final int TOTALQUESTIONS = 5;
    private int currentQuestionNum;
    private String currentQuestion;
    private int correctAnswers;
    private ArrayList<String> questionsList = new ArrayList<String>();
    private int[] answersList = new int[5];
    private ArrayList<Database.Question> questions;
    private  Database db;

    /**
     * Game Constructor
     */
    public Game(){
        //Gets the questions from the database
        currentQuestionNum = 1;

        db = new Database();
        questions = db.getQuestions();
        int index = 0;
        for (Database.Question question : questions) {
            questionsList.add(question.getQuestionString());
            answersList[index++] = question.getAnswer();
        }
    }

    /**
     *
     * @return
     *           The next question to be displayed
     *
     **/
    public String getNextQuestion(){
        currentQuestionNum++;
        if (currentQuestionNum > questionsList.size()) return "";

        return questionsList.get(currentQuestionNum-1);
    }

    /**
     * Checks if the current answer is correct
     * @param ans
     *  User's guess
     * @return
     *  Whether answer is correct or not
     */
    public boolean checkAnswer(int ans){
        if (answersList[currentQuestionNum -1] == ans) {
            correctAnswers++;
            return true;
        }
        return false;
    }

    /**
     * Current question number getter
     * @return
     *  current question number
     */
    public int getCurrentQuestionNum(){
        return currentQuestionNum;
    }

    /**
     *
     * @return
     *  The total number of questions
     */
    public int getTotalquestions(){
        return TOTALQUESTIONS;
    }

    /**
     *
     * @return
     *  The amount of correct answers (For display at the end of the game)
     */
    public int getCorrectAnswers(){
        return correctAnswers;
    }
}
