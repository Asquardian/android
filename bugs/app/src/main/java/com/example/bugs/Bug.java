package com.example.bugs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Bug{
    public int xPos, yPos, type;
    public boolean alive;
    public GameView gameView;

    public Bug(GameView gameView, Bitmap bitmap, int type) {
        this.gameView = gameView;
        this.bugSprite = bitmap;
        this.type = type;
        alive = true;
        spawn();
    }
    Bitmap bugSprite;
    public int killing(){
        alive = false;
        return type * 2;
    }

    public void Move(){
            Random moving = new Random();
            boolean up = moving.nextBoolean();
            if (up) {
                xPos+=5;
            } else {
                    yPos+=5;
            }
    }
    public void spawn(){
        alive = true;
        Random spawnRandom = new Random();
        xPos = spawnRandom.nextInt(1000);
        yPos = spawnRandom.nextInt(580);
    }
    public void onDraw(Canvas c)
    {
        if(alive)
            c.drawBitmap(bugSprite, xPos, yPos, null);
    }
}
