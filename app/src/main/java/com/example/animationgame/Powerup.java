package com.example.animationgame;

import android.content.res.Resources;
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

    public Powerup (int x, int y)
    {
        Rect = new RectF();
    }
}
