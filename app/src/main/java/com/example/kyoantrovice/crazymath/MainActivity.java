package com.example.kyoantrovice.crazymath;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String FONT_PATH = "fonts/UVNBaiSau_R.TTF";

    private TextView mtvGameName;
    private TextView mtvBestScore;
    private Button mbtPlay;
    private Button mbtMore;

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById();
        setTypeface();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSharedPreferences = getSharedPreferences(PlayActivity.SCORE, Context.MODE_PRIVATE);
        int bestScore = mSharedPreferences.getInt(PlayActivity.SCORE,0);
        String best = getString(R.string.best_score,bestScore);
        mtvBestScore.setText(best);
    }

    private void setTypeface() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), FONT_PATH);

        mtvGameName.setTypeface(typeface);
        mtvBestScore.setTypeface(typeface);
        mbtPlay.setTypeface(typeface);
        mbtMore.setTypeface(typeface);
    }

    private void findViewById() {
        mtvGameName = (TextView) findViewById(R.id.tv_game_name);
        mtvBestScore = (TextView) findViewById(R.id.tv_best_score);
        mbtPlay = (Button) findViewById(R.id.bt_play);
        mbtMore = (Button) findViewById(R.id.bt_more);

        mbtPlay.setOnClickListener(this);
        mbtMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_play:
                clickPlay();
                break;
            case R.id.bt_more:
                clickMore();
                break;
            default:
                break;
        }
    }

    private void clickPlay() {
        startActivity(new Intent(MainActivity.this, PlayActivity.class));
    }

    private void clickMore() {

    }
}
