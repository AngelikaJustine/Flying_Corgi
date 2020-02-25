package com.example.flyingcorgi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnStart;
    TextView title, rule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        title = findViewById(R.id.title);
        rule = findViewById(R.id.rule);

        Typeface SolwayBold = Typeface.createFromAsset(getAssets(), "fonts/SolwayBold.ttf");
        Typeface SolwayMedium = Typeface.createFromAsset(getAssets(), "fonts/SolwayMedium.ttf");

        btnStart.setTypeface(SolwayBold);
        title.setTypeface(SolwayBold);
        rule.setTypeface(SolwayMedium);
    }

    public void btnClicked(View view){
        Intent intent = new Intent(this, GameView.class);
        startActivity(intent);
    }

}
