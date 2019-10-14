package com.example.animationgame;

import android.graphics.RectF;

public class Bar {
    private RectF Rect;

    private float Length;
    private float Height;

    private float xCoord;

    private float yCoord;

    private float barSpeed;

    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;

    private int barMoving = STOPPED;

    private int screenX;
    private int screenY;

    public RectF getRect(){
        return Rect;
    }

    public void setMovementState(int state){
        barMoving = state;
    }

    public Bar(int x, int y){

        screenX = x;
        screenY = y;

        Length = screenX / 8;

        Height = screenY / 25;

        xCoord = screenX / 2;
        yCoord = screenY - 20;

        Rect = new RectF(xCoord, yCoord, xCoord + Length, yCoord + Height);

        barSpeed = screenX;
    }

    public void update(long fps){

        if(barMoving == LEFT){
            xCoord = xCoord - barSpeed / fps;
        }

        if(barMoving == RIGHT){
            xCoord = xCoord + barSpeed / fps;
        }

        if(Rect.left < 0){ xCoord = 0; } if(Rect.right > screenX){
            xCoord = screenX -
                    (Rect.right - Rect.left);
        }

        Rect.left = xCoord;
        Rect.right = xCoord + Length;
    }
}
