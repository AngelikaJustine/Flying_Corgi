package com.example.flyingcorgi;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import stanford.androidlib.graphics.GCanvas;
import stanford.androidlib.graphics.GColor;
import stanford.androidlib.graphics.GLabel;
import stanford.androidlib.graphics.GRect;
import stanford.androidlib.graphics.GRectangle;
import stanford.androidlib.graphics.GSprite;
import stanford.androidlib.util.RandomGenerator;

public class Corgi extends GCanvas {

    int SCALING_FACTOR = 14;
    int frames = 0;
    int score = 0;

    int food = 0;
    int lives = 5;

    int vx = -12;
    int index = 0;

    int v_chicken = 12;
    int v_donut = 18;

    Random r = new Random();

    boolean gameOver = false;

    GSprite wall1, wall2, wall3, wall4, wall5, wall6, wall7, wall8 ;
    GSprite corgi;
    GLabel lblScore;
    GLabel lblFood;
    GLabel lblLives;

    private ArrayList<Bitmap> CorgiPosition = new ArrayList<>();
    private ArrayList<GSprite> chickens = new ArrayList<>();
    private ArrayList<GSprite> donuts = new ArrayList<>();
    private ArrayList<GSprite> barrells = new ArrayList<>();
    private ArrayList<GSprite> balls = new ArrayList<>();

    public Corgi(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init() {
        super.init();

        setBackgroundColor(GColor.makeColor(105,204,224));

        createWall();

        createlabel();

        CorgiPosition.add(bmpScaling(R.drawable.kiri,SCALING_FACTOR));
        CorgiPosition.add(bmpScaling(R.drawable.kanan,SCALING_FACTOR));

        corgi = new GSprite(CorgiPosition,getWidth()/2,getHeight()- 200);
        corgi.setLoopBitmaps(false);
        corgi.setCurrentBitmapIndex(index);
        corgi.setY(getHeight()/3*2);
        corgi.setVelocityX(vx);
        corgi.setCollisionMargin(20);
        add(corgi);

        animate(30);

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

        if (!gameOver) {
            frames++;

            // MENAMPILKAN ARAH POSISI CORGI
            corgi.setCurrentBitmapIndex(index);

            if (frames % 90 == 0) {
                GSprite chicken = new GSprite(bmpScaling(R.drawable.chicken, 12));
                chicken.setY(-20);
                float x = RandomGenerator.getInstance().nextFloat(wall1.getWidth() + 50, getWidth() - wall1.getWidth() - 120);
                chicken.setX(x);
                chicken.setVelocityY(v_chicken);
                chicken.setCollisionMargin(10);
                add(chicken);
                chickens.add(chicken);
            }

            // Donut memiliki kemungkinan untuk muncul tiap 10 detik
            // DIlakukan random boolean untuk menentukan apakah akan muncul atau tidak
            // memiliki kecepatan lebih dibanding chicken
            if (frames % 300 == 0) {
                if (r.nextBoolean()) {
                    GSprite donut = new GSprite(bmpScaling(R.drawable.donut, 10));
                    donut.setY(-10);
                    float x = RandomGenerator.getInstance().nextFloat(wall1.getWidth() + 50, getWidth() - wall1.getWidth() - 120);
                    donut.setX(x);
                    donut.setVelocityY(v_donut);
                    donut.setCollisionMargin(10);
                    add(donut);
                    donuts.add(donut);
                }
            }

            if (frames % 30 == 0) {
                GSprite ball = new GSprite(bmpScaling(R.drawable.ball, 10));
                ball.setY(-20);
                float x = RandomGenerator.getInstance().nextFloat(wall1.getWidth() + 50, getWidth() - wall1.getWidth() - 120);
                ball.setX(x);
                ball.setVelocityY(v_chicken);
                ball.setCollisionMargin(10);
                add(ball);
                balls.add(ball);
            }

            if (frames % 60 == 0) {
                GSprite barrell = new GSprite(bmpScaling(R.drawable.barrell, 6));
                barrell.setY(-20);
                float x = RandomGenerator.getInstance().nextFloat(wall1.getWidth() + 50, getWidth() - wall1.getWidth() - 120);
                barrell.setX(x);
                barrell.setVelocityY(v_chicken);
                barrell.setCollisionMargin(10);
                add(barrell);
                barrells.add(barrell);
            }

            // Mengubah Kecepatan chicken dan donut setiap 2 menit
            if (frames % 3600 == 0) {
                v_chicken = v_chicken + 2;
                v_donut = v_donut + 2;
            }

            //CEK TABRAKAN UNTUK WALL
            if(corgi.getX() <= wall1.getWidth() - 10) {
                corgi.setVelocityX(0);
            } else if(corgi.collidesWith(wall7)){
                corgi.setVelocityX(0);
            }

            //CEK TABRAKAN DENGAN AYAM
            for (GSprite chic : chickens){
                if (corgi.collidesWith(chic)){
                    chic.setVelocityY(100);
                    chic.setCollidable(false);
                    remove(chic);
                    food++;
                    score = score + 10;
                }
            }

            //CEK TABRAKAN DENGAN DONUT
            for (GSprite don : donuts){
                if (corgi.collidesWith(don)){
                    don.setVelocityY(100);
                    don.setCollidable(false);
                    remove(don);
                    if(lives < 5){//tambah nyawa
                        lives++;
                    }
                }
            }

            //CEK TABRAKAN DENGAN BALL
            for (GSprite bal : balls){
                if (corgi.collidesWith(bal)){
                    bal.setVelocityY(100);
                    bal.setCollidable(false);
                    remove(bal);
                    if(lives == 0){
                        gameOver = true;
                        bal.stop();
                        break;
                    } else {
                        lives--;
                        gameOver = false;
                    }
                }
            }

            //CEK TABRAKAN DENGAN BARREL
            for (GSprite barr : barrells){
                if (corgi.collidesWith(barr)){
                    barr.setVelocityY(100);
                    barr.setCollidable(false);
                    remove(barr);
                    if(lives == 0){
                        gameOver = true;
                        barr.stop();
                        break;
                    } else {
                        lives--;
                        gameOver = false;
                    }
                }
            }

            if (frames % 30 == 0) {
                score++;
                lblScore.setText("Score: " + score);
            }

            lblLives.setText("Lives: " + lives);

            if (food < 10) {
                lblFood.setText(food + " / 10");
                lblFood.setRightX(getWidth() - wall1.getWidth() - 30);
            }
            else {
                lblFood.setText("Mission Completes");
                lblFood.setFontSize(30f);
                lblFood.setRightX(getWidth() - wall1.getWidth() - 30);
            }

            if(lives == 0) {
                gameOver = true;
                gameOver();
            }

        }
        else {

            gameOver();

        }

    }

    public boolean onTouch(View v, MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            vx = -vx;
            corgi.setVelocityX(vx);
            if(vx < 0){
                index = 0;
            }
            else {
                index = 1;
            }
        } else {
            if (event.getAction() == MotionEvent.ACTION_UP){
//                DO NOTHING
            }
        }
        return super.onTouch(v, event);
    }

    private void gameOver() {
        corgi.setVelocityX(0);
        corgi.setVelocityY(25);

        if(corgi.getY() > getHeight() + 50){
            remove(corgi);
            corgi.stop();
        }

        for (GSprite chic : chickens){
            chic.stop();
        }

        for (GSprite don : donuts){
            don.stop();
        }

        for (GSprite bal : balls){
            bal.stop();
        }

        for (GSprite barr : barrells){
            barr.stop();
        }

        // Muncul POP UP PERSEGI
        GRect rect = new GRect();
        rect.setSize(getWidth()/5*4, getHeight()/4);
        rect.setLocation(getWidth()/2 - rect.getWidth()/2, getHeight()/2-rect.getHeight()/2);
        rect.setFillColor(GColor.BLACK);
        add(rect);

        String str;
        if(food < 10){
            str = "GAME OVER!";
        } else{
            str = "MISSION SUCCESS!";
        }

        GLabel txt = new GLabel(str);
        txt.setFont(Typeface.MONOSPACE, 70f);
        txt.setX(getWidth()/2 - txt.getWidth()/2);
        txt.setY(getHeight()/2 - txt.getHeight()/2);
        txt.setColor(GColor.YELLOW);
        add(txt);
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

    private void createlabel() {
        lblScore = new GLabel("SCORE: 0");
        lblScore.setColor(GColor.BLACK);
        lblScore.setFont(Typeface.MONOSPACE, 50f);
        lblScore.setX(wall1.getWidth() + 30);
        lblScore.setY(30);
        add(lblScore);

        lblFood = new GLabel("0 / 10");
        lblFood.setColor(GColor.BLACK);
        lblFood.setFont(Typeface.MONOSPACE, 50f);
        lblFood.setRightX(getWidth() - wall1.getWidth() - 30);
        lblFood.setY(30);
        add(lblFood);

        lblLives = new GLabel("Lives: 5");
        lblLives.setColor(GColor.BLACK);
        lblLives.setFont(Typeface.MONOSPACE, 50f);
        lblLives.setX(wall1.getWidth() + 30);
        lblLives.setY(100);
        add(lblLives);
    }

}
