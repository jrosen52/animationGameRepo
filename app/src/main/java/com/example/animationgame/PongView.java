package com.example.animationgame;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

public class PongView extends SurfaceView implements Runnable
{

    Thread mGameThread = null;
    SurfaceHolder mOurHolder;

    volatile boolean mPlaying;

    boolean mPaused = true;

    Canvas mCanvas;
    Paint mPaint;
    Paint mPaint2;

    long mFPS;

    int mScreenX;
    int mScreenY;

    int randX;
    int randY;

    int randX2;
    int randY2;

    int green = Color.argb(255, 120, 197, 87);
    int blue = Color.argb(255, 93, 142, 240);
    int red = Color.argb(255, 240, 65, 93);
    int yellow = Color.argb(255, 228, 235, 42);
    int orange = Color.argb(255, 250, 193, 58);
    int purple = Color.argb(255, 225, 58, 250);

    int i = 0;

    int colors[] = {Color.argb(255, 120, 197, 87),Color.argb(255, 93, 142, 240),
            Color.argb(255, 240, 65, 93), Color.argb(255, 228, 235, 42),
            Color.argb(255, 250, 193, 58), Color.argb(255, 225, 58, 250)};

    Bar mBar;

    Ball mBall;
    Ball mBall2;

    boolean extraBallActive = false;
    boolean gate1 = false;
    boolean gate2 = false;
    boolean gate3 = false;
    boolean EB = false;

    boolean extraLifeActive = false;
    boolean gate4 = false;
    boolean gate5 = false;

    Powerup extraBall;
    Powerup extraLife;

    SoundPool sp;
    int beep1ID = -1;
    int beep2ID = -1;
    int beep3ID = -1;
    int loseLifeID = -1;

    int mScore = 0;

    int mLives = 3;

    public void setupAndRestart(){

        mBall.reset(mScreenX, mScreenY);

        if(mLives == 0) {
            mScore = 0;
            mLives = 3;
        }

    }

    public void update() {

        mBar.update(mFPS);
        mBall.update(mFPS);

        if(mScore == 3 && gate3 == false)
        {
            extraBallActive = true;
            gate3 = true;
        }

        if(mScore == 5 && gate5 == false)
        {
            extraLifeActive = true;
            gate5 = true;
        }

        if(RectF.intersects(mBar.getRect(), mBall.getRect())) {
            mBall.setRandomXVelocity();
            mBall.reverseYVelocity();
            mBall.clearObstacleY(mBar.getRect().top - 2);

            mScore++;
            i = new Random().nextInt(6);
            mBall.increaseVelocity();

            sp.play(beep1ID, 1, 1, 0, 0, 1);
        }

        if(mBall.getRect().bottom > mScreenY){
            mBall.reverseYVelocity();
            mBall.clearObstacleY(mScreenY - 2);

            mLives--;
            i = new Random().nextInt(6);
            sp.play(loseLifeID, 1, 1, 0, 0, 1);

            if(mLives == 0){
                mPaused = true;
                setupAndRestart();
            }
        }

        if(mBall.getRect().top < 0){
            mBall.reverseYVelocity();
            mBall.clearObstacleY(12);
            i = new Random().nextInt(6);
            sp.play(beep2ID, 1, 1, 0, 0, 1);
        }

        if(mBall.getRect().left < 0){
            mBall.reverseXVelocity();
            mBall.clearObstacleX(2);
            i = new Random().nextInt(6);
            sp.play(beep3ID, 1, 1, 0, 0, 1);
        }

        if(mBall.getRect().right > mScreenX){
            mBall.reverseXVelocity();
            mBall.clearObstacleX(mScreenX - 22);
            i = new Random().nextInt(6);
            sp.play(beep3ID, 1, 1, 0, 0, 1);
        }

        if(extraBallActive == true && gate1 == true)
        {
            if(RectF.intersects(mBall.getRect(), extraBall.getRect()))
            {
                if(gate2 == false)
                {
                    mBall2 = new Ball(mScreenX, mScreenY);
                    extraBallActive = false;
                    EB = true;
                    gate2 = true;
                }
            }
        }

        if(extraLifeActive == true && gate4 == true)
        {
            if(RectF.intersects(extraLife.getRect(), mBall.getRect()))
            {
                mLives++;
                extraLifeActive = false;
            }
        }

        if(EB == true)
        {
            mBall2.update(mFPS);

            if(RectF.intersects(mBar.getRect(), mBall2.getRect())) {
                mBall2.setRandomXVelocity();
                mBall2.reverseYVelocity();
                mBall2.clearObstacleY(mBar.getRect().top - 2);

                mScore++;
                mBall2.increaseVelocity();

                sp.play(beep1ID, 1, 1, 0, 0, 1);
            }

            if(mBall2.getRect().top < 0){
                mBall2.reverseYVelocity();
                mBall2.clearObstacleY(12);
                sp.play(beep2ID, 1, 1, 0, 0, 1);
            }

            if(mBall2.getRect().left < 0){
                mBall2.reverseXVelocity();
                mBall2.clearObstacleX(2);
                sp.play(beep3ID, 1, 1, 0, 0, 1);
            }

            if(mBall2.getRect().right > mScreenX){
                mBall2.reverseXVelocity();
                mBall2.clearObstacleX(mScreenX - 22);
                sp.play(beep3ID, 1, 1, 0, 0, 1);
            }

            if(mBall.getRect().bottom > mScreenY){
                EB = false;
            }
        }
    }


    public void draw() {

        if (mOurHolder.getSurface().isValid()) {

            mCanvas = mOurHolder.lockCanvas();

            mCanvas.drawColor(colors[i]);

            mPaint.setColor(Color.argb(255, 255, 255, 255));
            mPaint2.setColor(Color.argb(255, 0, 0, 0));

            mCanvas.drawRect(mBar.getRect(), mPaint);

            mCanvas.drawRect(mBall.getRect(), mPaint);

            if(EB == true)
            {
                mCanvas.drawRect(mBall2.getRect(), mPaint2);
            }

            mPaint.setColor(Color.argb(255, 255, 255, 255));

            mPaint.setTextSize(40);
            mCanvas.drawText("Score: " + mScore + "   Lives: " + mLives, 10, 50, mPaint);

            if(extraBallActive == true)
            {
                if(gate1 == false)
                {
                    randX = new Random().nextInt(mScreenX);
                    randY = new Random().nextInt(mScreenY+ 50) + 50;
                    extraBall = new Powerup(randX, randY, mScreenX);
                }
                mCanvas.drawRect(extraBall.getRect(), mPaint2);
                mPaint.setTextSize(50);
                mCanvas.drawText("EB", randX+30, randY-40, mPaint);
                gate1 = true;
            }

            if(extraLifeActive == true)
            {
                if(gate4 == false)
                {
                    randX2 = new Random().nextInt(mScreenX);
                    randY2 = new Random().nextInt(mScreenY+ 50) + 50;
                    extraLife = new Powerup(randX2, randY2, mScreenX);
                }
                mCanvas.drawRect(extraLife.getRect(), mPaint);
                mPaint2.setTextSize(50);
                mCanvas.drawText("EL", randX2+30, randY2-40, mPaint2);
                gate4 = true;
            }

            mOurHolder.unlockCanvasAndPost(mCanvas);
        }

    }

    public void pause() {
        mPlaying = false;
        try {
            mGameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    public void resume() {
        mPlaying = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    @Override
    public void run() {
        while (mPlaying) {

            long startFrameTime = System.currentTimeMillis();

            if(!mPaused){
                update();
            }

            draw();

            long timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                mFPS = 1000 / timeThisFrame;
            }

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:

                mPaused = false;

                if(motionEvent.getX() > mScreenX / 2){
                    mBar.setMovementState(mBar.RIGHT);
                }
                else{
                    mBar.setMovementState(mBar.LEFT);
                }

                break;

            case MotionEvent.ACTION_UP:

                mBar.setMovementState(mBar.STOPPED);
                break;
        }
        return true;
    }


    public PongView(Context context, int x, int y) {

        super(context);

        mScreenX = x;
        mScreenY = y;

        mOurHolder = getHolder();
        mPaint = new Paint();
        mPaint2 = new Paint();

        mBar = new Bar(mScreenX, mScreenY);

        mBall = new Ball(mScreenX, mScreenY);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            sp = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else {
            sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }


        try{
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            descriptor = assetManager.openFd("beep1.ogg");
            beep1ID = sp.load(descriptor, 0);

            descriptor = assetManager.openFd("beep2.ogg");
            beep2ID = sp.load(descriptor, 0);

            descriptor = assetManager.openFd("beep3.ogg");
            beep3ID = sp.load(descriptor, 0);

            descriptor = assetManager.openFd("loseLife.ogg");
            loseLifeID = sp.load(descriptor, 0);

        }catch(IOException e){
            Log.e("error", "failed to load sound files");
        }

        setupAndRestart();

    }
}
