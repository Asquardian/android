package com.example.bugs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView
{
    /**Объект класса GameLoopThread*/
    private GameThread mThread, bThread;
    private Bug[] bugs;
    public int score;
    public int shotX;
    public int shotY;

    private SoundPool sound1;
    private SoundPool sound2;
    private int sExplosion;
    private int sExplosion1;
    /**Переменная запускающая поток рисования*/
    private boolean running = false;

    //-------------Start of GameThread--------------------------------------------------\\

    public class GameThread extends Thread
    {
        /**Объект класса*/
        private GameView view;
        private int number;

        /**Конструктор класса*/
        public GameThread(GameView view, int number)
        {
            this.view = view;
            this.number = number;
        }

        /**Задание состояния потока*/
        public void setRunning(boolean run)
        {
            running = run;
        }

        /** Действия, выполняемые в потоке */
        public void run()
        {
            while (running)
            {
                Canvas canvas = null;
                try
                {
                    canvas = view.getHolder().lockCanvas();
                    synchronized (view.getHolder())
                    {
                        bugs[number].Move();

                        if(bugs[number].xPos > 2280 || bugs[number].yPos >1080){
                            score -= 5;
                            bugs[number].alive = false;
                            return;
                        }
                        onDraw(canvas);
                    }
                }
                catch (Exception e) { }
                finally
                {
                    if (canvas != null)
                    {
                        view.getHolder().unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

    //-------------End of GameThread--------------------------------------------------\\

    public GameView(Context context)
    {
        super(context);
        sound1 = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        sound2 = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        sExplosion = sound1.load(context, R.raw.hat, 1);
        sExplosion1 = sound2.load(context, R.raw.bump, 1);
        int score = 0;
        Random random = new Random();
        Bitmap bugSprite;
        int type;
        bugs = new Bug[2];
        for(int i = 0; i < 2; i++){
        switch(random.nextInt(2)){
            case 0:
                type = 5;
                bugSprite = BitmapFactory.decodeResource(getResources(), R.drawable.bug1);
                break;
            case 1:
                type = 2;
                bugSprite = BitmapFactory.decodeResource(getResources(), R.drawable.bug2);
                break;
            case 2:
                type = 3;
                bugSprite = BitmapFactory.decodeResource(getResources(), R.drawable.bug3);
                break;
            default:
                type = 5;
                bugSprite = BitmapFactory.decodeResource(getResources(), R.drawable.bug1);
        }
            bugs[i] = new Bug(this, bugSprite, type);
        }

        mThread = new GameThread(this, 0);
        bThread = new GameThread(this, 1);
        /*Рисуем все наши объекты и все все все*/
        getHolder().addCallback(new SurfaceHolder.Callback()
        {
            /*** Уничтожение области рисования */
            public void surfaceDestroyed(SurfaceHolder holder)
            {
                boolean retry = true;
                mThread.setRunning(false);
                bThread.setRunning(false);
                while (retry)
                {
                    try
                    {
                        // ожидание завершение потока
                        mThread.join();
                        bThread.join();
                        retry = false;
                    }
                    catch (InterruptedException e) { }
                }
            }

            /** Создание области рисования */
            public void surfaceCreated(SurfaceHolder holder)
            {
                mThread.setRunning(true);
                mThread.start();
                bThread.setRunning(true);
                bThread.start();
            }

            /** Изменение области рисования */
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
            {
            }
        });
    }
    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        final int action = e.getAction();
        if(action == MotionEvent.ACTION_DOWN) {
            boolean getDamage = false;
            for (int i = 0; i < 2; i++) {
                shotX = (int) e.getX();
                shotY = (int) e.getY();
                if (bugs[i].xPos < shotX && bugs[i].yPos < shotY)
                    if (bugs[i].xPos + 200 > shotX && bugs[i].yPos + 200 > shotY) {
                        score += bugs[i].killing();
                        getDamage = true;
                        sound1.play(sExplosion, 1.0f, 1.0f, 0, 0, 1.5f);
                    }
            }
            if (!getDamage) {
                sound2.play(sExplosion1, 1.0f, 1.0f, 0, 0, 1.5f);
                score -= 5;
            }
        }
        return true;
    }
    public void ScoreBoard(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPaint(paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(80);
        canvas.drawText("\n\nscore " + Integer.toString(score), 100, 250, paint);
    }
@Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GREEN);
        int aliveToInt=0;
        for(int i = 0; i < 2; i++) {
            bugs[i].onDraw(canvas);
            if(!bugs[i].alive){
                aliveToInt++;
            }
        }
        if(aliveToInt == 2){
            ScoreBoard(canvas);
        }
    }
}