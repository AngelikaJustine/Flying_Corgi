package com.example.flyingcorgi;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import stanford.androidlib.graphics.GCanvas;
import stanford.androidlib.graphics.GColor;
import stanford.androidlib.graphics.GLabel;
import stanford.androidlib.graphics.GSprite;
import stanford.androidlib.util.RandomGenerator;

public class Corgi extends GCanvas {

    int SCALING_FACTOR = 10;
    int frames = 0;
    int score = 0;

    int vx = -12;
    int vy = -10;
    int index = 0;

    boolean gameOver = false;

    GSprite wall1, wall2, wall3, wall4, wall5, wall6, wall7, wall8 ;
    GSprite corgi;
    GLabel lblScore;

    private ArrayList<Bitmap> CorgiPosition = new ArrayList<>();

    public Corgi(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init() {
        super.init();

        setBackgroundColor(GColor.makeColor(105,204,224));

//        setBackgroundColor(getResources().getColor(R.color.blue));

        createWall();

        CorgiPosition.add(bmpScaling(R.drawable.kiri,SCALING_FACTOR));
        CorgiPosition.add(bmpScaling(R.drawable.kanan,SCALING_FACTOR));

        corgi = new GSprite(CorgiPosition,getWidth()/2,getHeight()- 200);
        corgi.setLoopBitmaps(false);
        corgi.setCurrentBitmapIndex(index);
        corgi.setVelocityY(vy);
        corgi.setVelocityX(vx);
        corgi.setCollisionMargin(30);
        add(corgi);
        animate(20);

        lblScore = new GLabel("SCORE: 0");
        lblScore.setColor(GColor.BLACK);
        lblScore.setFont(Typeface.MONOSPACE, 50f);
        lblScore.setX(wall1.getWidth() + 30);
        lblScore.setY(30);
        add(lblScore);


    }

    private Bitmap bmpScaling(int id, int factor){
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), id);
        bmp = Bitmap.createScaledBitmap(bmp, bmp.getWidth()/factor,
                bmp.getHeight()/factor, true);
        return bmp;
    }

    @Override
    public void onAnimateTick() {
        super.onAnimateTick();
        frames++;

        corgi.setCurrentBitmapIndex(index);

        if(corgi.getY() <= getHeight()/5 * 3){
            corgi.setVelocityY(0);
        }

        if(frames % 20 == 0){
            score++;
            lblScore.setText("Score: " + score);
        }

        if(frames % 100 == 0){
            GSprite chicken = new GSprite(bmpScaling(R.drawable.bone, 18));
            chicken.setY(-20);
            float x = RandomGenerator.getInstance().nextFloat(wall1.getWidth() + 50, getWidth()- wall1.getWidth()-80);
            chicken.setX(x);
            chicken.setVelocityY(12);
            chicken.setCollisionMargin(10);
            add(chicken);
//            bone.add(bone);
        }

//        if(frames % 30 == 0){
//            GSprite brick2 = new GSprite(bmpScaling(R.drawable.brick2, 40));
//            brick2.setY(-20);
//            float x = RandomGenerator.getInstance().nextFloat(wall1.getWidth() + 50, getWidth()- wall1.getWidth()-80);
//            brick2.setX(x);
//            brick2.setVelocityY(12);
//            brick2.setCollisionMargin(40);
//            add(brick2);
//            brick2.setDebug(true);
////            bone.add(bone);
//        }

    }

    public boolean onTouch(View v, MotionEvent event) {
        if(index == 0){
            vx = -vx;
            corgi.setVelocityX(vx);
            index = 1;
        }
        else {
            vx = -vx;
            corgi.setVelocityX(vx);
            index = 0;
        }

        return super.onTouch(v, event);
    }

    private void createWall() {
        Bitmap wall;
        wall = bmpScaling(R.drawable.wall, 18);

        wall1 = new GSprite(wall);
        wall1.setX(0);
        add(wall1);

        wall2 = new GSprite(wall);
        wall2.setX(0);
        wall2.setY(wall1.getHeight());
        add(wall2);

        wall3 = new GSprite(wall);
        wall3.setX(0);
        wall3.setY(wall2.getHeight()*2);
        add(wall3);

        wall4 = new GSprite(wall);
        wall4.setX(0);
        wall4.setY(wall2.getHeight()*3);
        add(wall4);

        wall5 = new GSprite(wall);
        wall5.setX(getWidth()-wall5.getWidth());
        add(wall5);

        wall6 = new GSprite(wall);
        wall6.setX(getWidth()-wall5.getWidth());
        wall6.setY(wall1.getHeight());
        add(wall6);

        wall7 = new GSprite(wall);
        wall7.setX(getWidth()-wall5.getWidth());
        wall7.setY(wall1.getHeight()*2);
        add(wall7);

        wall8 = new GSprite(wall);
        wall8.setX(getWidth()-wall5.getWidth());
        wall8.setY(wall1.getHeight()*3);
        add(wall8);
    }

}
