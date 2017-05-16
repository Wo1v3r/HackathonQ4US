package com.example.tribesquiz;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Quiz activity which runs a quiz for the players
 */
public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String SCORE = "QuizActivity.score";
    public int counter;
    private Timer timer;

    @BindView(R.id.questionTextView) TextView questionTextView;
    @BindView(R.id.questionNumberView) TextView questionNumberView;
    @BindView(R.id.trueOrFalseText) TextView trueOrFalseText;
    @BindView(R.id.correctAnswersText) TextView correctAnswersText;
    @BindView(R.id.yesButton) Button yesButton;
    @BindView(R.id.noButton) Button noButton;


    private Game game;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setTitle("Q4US");

        Intent intent = getIntent();
        ButterKnife.bind(this);
        game = new Game();


    }

    /**
     * Updates views with the results based on the answer
     * @param correct
     */
    public void showAnswerResults(boolean correct){
        if (correct) {


            trueOrFalseText.setText("נכון!");
        }
        else {


            trueOrFalseText.setText("לא נכון!");
        }

            yesButton.setEnabled(false);
            noButton.setEnabled(false);
            counter = 0;
            timer =  new Timer();
            new CountDownTimer(2200, 1000){
                public void onTick(long millisUntilFinished){
                    counter++;
                }
                public  void onFinish(){
                    trueOrFalseText.setText("");
                    correctAnswersText.setText("תשובות נכונות "+game.getCorrectAnswers());
                    loadNextQuestion();
                    yesButton.setEnabled(true);
                    noButton.setEnabled(true);
                }
            }.start();
    }

    /**
     * Click handler for the YES Button
     * @param view
     */
    public void yesClick(View view){
        showAnswerResults(game.checkAnswer(1));
    }

    /**
     * Click handler for the NO Button
     * @param view
     */
    public void noClick(View view){
        showAnswerResults(game.checkAnswer(0));
    }

    /**
     * Loads the next question and displays it on the view
     */
    public void loadNextQuestion(){

        if (game.getCurrentQuestionNum() >= game.getTotalquestions()) endGame();// end game
        else {
            questionTextView.setText(game.getNextQuestion());
            questionNumberView.setText(game.getCurrentQuestionNum() + "/" + game.getTotalquestions());

        }
    }


    /**
     * Ends the game and creates an intent to start the EndActivity
     */
    public void endGame(){
        Intent intent = new Intent(this,EndActivity.class);
        intent.putExtra(SCORE,game.getCorrectAnswers());
        startActivity(intent);
    }
}
