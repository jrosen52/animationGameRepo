package com.example.animationgame;

import android.graphics.RectF;

public class Powerup
{
    private RectF Rect;
    private float Width;
    private float Height;

    public RectF getRect()
    {
        return Rect;
    }

    public Powerup (int x, int y, int screenX)
    {
        Width = screenX / 15;
        Height = Width;
        Rect = new RectF(x, y, x + Width, y - Height);
    }
}
