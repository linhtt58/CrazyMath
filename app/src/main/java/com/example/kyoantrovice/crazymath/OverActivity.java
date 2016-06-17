package com.example.kyoantrovice.crazymath;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OverActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String FONT_PATH = "fonts/UVNBaiSau_R.TTF";

    private TextView mtvGameOver;
    private TextView mtvYourScore;
    private Button mbtTryAgain;
    private Button mbtHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over);

        findViewById();
        setTypeface();

        int score = getIntent().getExtras().getInt(PlayActivity.SCORE);
        String strScore = getString(R.string.your_score,score);
        mtvYourScore.setText(strScore);
    }

    private void setTypeface() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), FONT_PATH);

        mtvGameOver.setTypeface(typeface);
        mtvYourScore.setTypeface(typeface);
        mbtTryAgain.setTypeface(typeface);
        mbtHome.setTypeface(typeface);
    }

    private void findViewById() {
        mtvGameOver = (TextView) findViewById(R.id.tv_game_over);
        mtvYourScore = (TextView) findViewById(R.id.tv_your_score);
        mbtTryAgain = (Button) findViewById(R.id.bt_try);
        mbtHome = (Button) findViewById(R.id.bt_home);

        mbtTryAgain.setOnClickListener(this);
        mbtHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_try:
                clickTryAgain();
                break;
            case R.id.bt_home:
                clickHome();
                break;
            default:
                break;
        }
    }

    private void clickTryAgain() {
        startActivity(new Intent(OverActivity.this,PlayActivity.class));
        finish();
    }

    private void clickHome() {
        finish();
    }
}
