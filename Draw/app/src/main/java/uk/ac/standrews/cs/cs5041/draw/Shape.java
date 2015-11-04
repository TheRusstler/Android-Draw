package uk.ac.standrews.cs.cs5041.draw;

import android.graphics.Canvas;

public interface Shape {
    void draw(Canvas canvas);
    void move(float x, float y);
    void scale(double factor);
    void rotate(float startX, float startY, float endX, float endY);
    Shape deepCopy();
    double getRotation();
    float getCentreX();
    float getCentreY();
}
