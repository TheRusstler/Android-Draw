package uk.ac.standrews.cs.cs5041.draw;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Circle implements Shape {
    public float cx, cy, radius;
    public Paint paint;

    public Circle(float cx, float cy, float radius, Paint paint) {
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;
        this.paint = paint;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(cx, cy, radius, paint);
    }

    @Override
    public void move(float x, float y) {
        cx = x;
        cy = y;
    }

    @Override
    public void scale(double factor) {
        radius = (float)(radius * factor);
    }

    @Override
    public Shape deepCopy() {
        return new Circle(cx, cy, radius, paint);
    }

    public void setRadius(float x, float y) {
        float diffX, diffY;
        diffX = Math.abs(x-cx);
        diffY = Math.abs(y-cy);
        this.radius = (float)Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
    }
}
