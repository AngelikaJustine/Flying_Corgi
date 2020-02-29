package com.example.flyingcorgi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class RuleView extends AppCompatActivity {

    TextView title, rule, barell, ball, chicken, donut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule_view);

        title = findViewById(R.id.title);
        rule = findViewById(R.id.rule);
        barell = findViewById(R.id.barell);
        ball = findViewById(R.id.ball);
        chicken = findViewById(R.id.chicken);
        donut = findViewById(R.id.donut);
        
        Typeface SolwayBold = Typeface.createFromAsset(getAssets(), "fonts/SolwayBold.ttf");
        Typeface SolwayMedium = Typeface.createFromAsset(getAssets(), "fonts/SolwayMedium.ttf");
        Typeface SolwayRegular = Typeface.createFromAsset(getAssets(), "fonts/SolwayRegular.ttf");
        
        title.setTypeface(SolwayBold);
        rule.setTypeface(SolwayMedium);
        barell.setTypeface(SolwayRegular);
        ball.setTypeface(SolwayRegular);
        chicken.setTypeface(SolwayRegular);
        donut.setTypeface(SolwayRegular);
        

    }
}
