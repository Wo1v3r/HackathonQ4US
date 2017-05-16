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

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String SCORE = "QuizActivity.score";
    public int counter;
    private Timer timer;

    @BindView(R.id.questionTextView) TextView questionTextView;
    @BindView(R.id.questionNumberView) TextView questionNumberView;
    @BindView(R.id.trueOrFalseText) TextView trueOrFalseText;
    @BindView(R.id.correctAnswersText) TextView correctAnswersText;

    private Game game;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent intent = getIntent();
        ButterKnife.bind(this);
        game = new Game();


    }

    public void showAnswerResults(boolean correct){
        if (correct) {


            trueOrFalseText.setText("נכון!");
        }
        else {


            trueOrFalseText.setText("לא נכון!");
        }

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
                }
            }.start();
    }


    public void yesClick(View view){
        showAnswerResults(game.checkAnswer(1));
    }

    public void noClick(View view){
        showAnswerResults(game.checkAnswer(0));
    }

    public void loadNextQuestion(){

        if (game.getCurrentQuestionNum() >= game.getTotalquestions()) endGame();// end game
        else {
            questionTextView.setText(game.getNextQuestion());
            questionNumberView.setText(game.getCurrentQuestionNum() + "/" + game.getTotalquestions());

        }
    }



    public void endGame(){
        Intent intent = new Intent(this,EndActivity.class);
        intent.putExtra(SCORE,game.getCorrectAnswers());
        startActivity(intent);
    }
}
