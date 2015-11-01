package uk.ac.standrews.cs.cs5041.draw;

import android.graphics.Canvas;

public interface Shape {
    void draw(Canvas canvas);
    void move(float x, float y);
}
