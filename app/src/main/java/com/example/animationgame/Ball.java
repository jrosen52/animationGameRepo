package com.example.animationgame;

import android.graphics.RectF;
import java.util.Random;

public class Ball {
    private RectF Rect;
    private float xVelocity;
    private float yVelocity;
    private float Width;
    private float Height;

    public RectF getRect(){
        return Rect;
    }

    public Ball(int screenX, int screenY){

        // Make the mBall size relative to the screen resolution
        Width = screenX / 100;
        Height = Width;

    /*
        Start the ball travelling straight up
        at a quarter of the screen height per second
    */
        yVelocity = screenY / 4;
        xVelocity = yVelocity;

        // Initialize the Rect that represents the mBall
        Rect = new RectF();

    }

    public void update(long fps){
        Rect.left = Rect.left + (xVelocity / fps);
        Rect.top = Rect.top + (yVelocity / fps);
        Rect.right = Rect.left + Width;
        Rect.bottom = Rect.top - Height;
    }

    // Reverse the vertical heading
    public void reverseYVelocity(){
        yVelocity = -yVelocity;
    }

    // Reverse the horizontal heading
    public void reverseXVelocity(){
        xVelocity = -xVelocity;
    }

    public void setRandomXVelocity(){

        // Generate a random number either 0 or 1
        Random generator = new Random();
        int answer = generator.nextInt(2);

        if(answer == 0){
            reverseXVelocity();
        }
    }

    // Speed up by 10%
// A score of over 20 is quite difficult
// Reduce or increase 10 to make this easier or harder
    public void increaseVelocity(){
        xVelocity = xVelocity + xVelocity / 10;
        yVelocity = yVelocity + yVelocity / 10;
    }

    public void clearObstacleY(float y){
        Rect.bottom = y;
        Rect.top = y - Height;
    }

    public void clearObstacleX(float x){
        Rect.left = x;
        Rect.right = x + Width;
    }

    public void reset(int x, int y){
        Rect.left = x / 2;
        Rect.top = y - 20;
        Rect.right = x / 2 + Width;
        Rect.bottom = y - 20 - Height;
    }



}
