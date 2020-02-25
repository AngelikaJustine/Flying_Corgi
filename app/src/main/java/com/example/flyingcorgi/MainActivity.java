package com.example.flyingcorgi;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnStart;
    TextView title, title2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        title = findViewById(R.id.title);
        title2 = findViewById(R.id.title2);

        Typeface SolwayBold = Typeface.createFromAsset(getAssets(), "fonts/SolwayBold.ttf");
        btnStart.setTypeface(SolwayBold);
        title.setTypeface(SolwayBold);
        title2.setTypeface(SolwayBold);
    }
}
