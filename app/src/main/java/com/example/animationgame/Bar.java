package com.example.animationgame;

import android.graphics.RectF;

public class Bar {
    // RectF is an object that holds four coordinates - just what we need
    private RectF Rect;

    // How long and high our mBat will be
    private float Length;
    private float Height;

    // X is the far left of the rectangle which forms our mBat
    private float xCoord;

    // Y is the top coordinate
    private float yCoord;

    // This will hold the pixels per second speed that
    // the mBat will move
    private float barSpeed;

    // Which ways can the mBat move
    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;

    // Is the mBat moving and in which direction
    private int barMoving = STOPPED;

    // The screen length and width in pixels
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

        // 1/8 screen width wide
        Length = screenX / 8;

        // 1/25 screen mHeight high
        Height = screenY / 25;

        // Start mBat in roughly the screen centre
        xCoord = screenX / 2;
        yCoord = screenY - 20;

        Rect = new RectF(xCoord, yCoord, xCoord + Length, yCoord + Height);

        // How fast is the mBat in pixels per second
        barSpeed = screenX;
        // Cover entire screen in 1 second
    }

    public void update(long fps){

        if(barMoving == LEFT){
            xCoord = xCoord - barSpeed / fps;
        }

        if(barMoving == RIGHT){
            xCoord = xCoord + barSpeed / fps;
        }

        // Make sure it's not leaving screen
        if(Rect.left < 0){ xCoord = 0; } if(Rect.right > screenX){
            xCoord = screenX -
                    // The width of the Bat
                    (Rect.right - Rect.left);
        }

        // Update the Bat graphics
        Rect.left = xCoord;
        Rect.right = xCoord + Length;
    }
}
