package com.example.kyoantrovice.crazymath;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener,Runnable {

    public static final String FONT_PATH = "fonts/UVNBaiSau_R.TTF";
    public static final int TIMER = 3;
    public static final String SCORE = "Score";

    private TextView mtvScore;
    private TextView mtvBest;
    private TextView mtvTimer;
    private TextView mtvQuestion;
    private Button[] mbtOptions;

    private int mBest;
    private int mScore;
    private int mTimer;
    private Handler mHandler;

    private Random mRandom;
    private int mNumber0;
    private int mNumber1;
    private int mAnswer;
    private int mRight;

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        findViewById();
        setTypeface();
        setGameStatus();

        mRandom = new Random();
        mHandler = new Handler();
        mScore = 0;
        generateQuestion();
    }

    private void setGameStatus() {
        String score = getString(R.string.score,mScore);
        mtvScore.setText(score);
        mSharedPreferences = getSharedPreferences(SCORE, Context.MODE_PRIVATE);
        mBest = mSharedPreferences.getInt(SCORE,0);
        String best = getString(R.string.best,mBest);
        mtvBest.setText(best);
    }

    private void generateQuestion() {
        mTimer = TIMER;
        mNumber0 = mRandom.nextInt(10);
        mNumber1 = mRandom.nextInt(10);
        mAnswer = mNumber0 + mNumber1;
        mRight = mRandom.nextInt(3);
        int[] options = new int[3];
        for(int index = 0;index < options.length; index++){
            if(index == mRight){
                options[index] = mAnswer;
            } else {
                int other = 1+ mRandom.nextInt(9);
                if (mRandom.nextBoolean()){
                    if(index % 2 == 0){
                        options[index] = mAnswer - other;
                    } else {
                        options[index] = mAnswer + other;
                    }
                } else {
                    if(index % 2 == 1){
                        options[index] = mAnswer - other;
                    } else {
                        options[index] = mAnswer + other;
                    }
                }
            }
        }

        String question = mNumber0 + " + " + mNumber1 + " = ?";
        mtvQuestion.setText(question);
        for (int index = 0; index < mbtOptions.length; index++) {
            mbtOptions[index].setText(options[index] + "");
        }

        int time = TIMER + 1;
        for(int index = 0;index < time; index++){
            mHandler.postDelayed(this, index * 1000);
        }
    }

    private void setTypeface() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), FONT_PATH);

        mtvScore.setTypeface(typeface);
        mtvBest.setTypeface(typeface);
        mtvTimer.setTypeface(typeface);
        mtvQuestion.setTypeface(typeface);
        for (int index = 0; index < mbtOptions.length; index++) {
            mbtOptions[index].setTypeface(typeface);
        }
    }

    private void findViewById() {
        mtvScore = (TextView) findViewById(R.id.tv_score);
        mtvBest = (TextView) findViewById(R.id.tv_best);
        mtvTimer = (TextView) findViewById(R.id.tv_timer);
        mtvQuestion = (TextView) findViewById(R.id.tv_question);
        mbtOptions = new Button[3];
        for (int index = 0; index < mbtOptions.length; index++) {
            mbtOptions[index] = (Button) findViewById(R.id.bt_option_0 + index);
            mbtOptions[index].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        mHandler.removeCallbacksAndMessages(null);
        int index = v.getId()- R.id.bt_option_0;
        if(index == mRight){
            mScore++;
            String score = getString(R.string.score,mScore);
            mtvScore.setText(score);
            if(mScore > mBest){
                mBest = mScore;
                String best = getString(R.string.best,mBest);
                mtvBest.setText(best);
            }
            generateQuestion();
        } else {
            goToGameOver();
        }
    }

    private void goToGameOver() {
        if(mScore == mBest){
            mSharedPreferences.edit().putInt(SCORE,mScore).commit();
        }
        Intent intent = new Intent(PlayActivity.this,OverActivity.class);
        intent.putExtra(SCORE,mScore);
        startActivity(intent);
        finish();
    }

    @Override
    public void run() {
        mtvTimer.setText(mTimer + "");
        mTimer--;
        if(mTimer == -1){
            goToGameOver();
        }
    }
}
