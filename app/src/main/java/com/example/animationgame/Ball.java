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

        Width = screenX / 100;
        Height = Width;

        yVelocity = screenY / 4;
        xVelocity = yVelocity;

        Rect = new RectF();

    }

    public void update(long fps){
        Rect.left = Rect.left + (xVelocity / fps);
        Rect.top = Rect.top + (yVelocity / fps);
        Rect.right = Rect.left + Width;
        Rect.bottom = Rect.top - Height;
    }

    public void reverseYVelocity(){
        yVelocity = -yVelocity;
    }

    public void reverseXVelocity(){
        xVelocity = -xVelocity;
    }

    public void setRandomXVelocity(){

        Random generator = new Random();
        int answer = generator.nextInt(2);

        if(answer == 0){
            reverseXVelocity();
        }
    }

    public void increaseVelocity(){
        xVelocity = xVelocity + xVelocity / 20;
        yVelocity = yVelocity + yVelocity / 20;
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
