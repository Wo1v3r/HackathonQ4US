package com.example.tribesquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EndActivity extends AppCompatActivity {
    private int score;
    @BindView(R.id.resultsTextView) TextView resultsText;
    @BindView(R.id.rewardText) TextView rewardText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        Intent intent = getIntent();
        ButterKnife.bind(this);
        score = intent.getIntExtra("QuizActivity.score",0);
        resultsText.setText("עניתם על "+ score +" תשובות נכונה");

        int reward = calculateReward(score);

        if (reward != 0)
            rewardText.setText("זכיתם ב " +reward+" נקודות");
        else
            rewardText.setText("לא זכיתם בנקודות, נסו שוב מחר!");
    }

    public void returnToMain(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private int calculateReward(int score){
        if (score > 2 ) return score*10;

        return 0;
    }
}
