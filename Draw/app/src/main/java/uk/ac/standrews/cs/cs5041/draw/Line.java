package uk.ac.standrews.cs.cs5041.draw;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Line implements Shape {
    public float x1, y1, x2, y2;
    public Paint paint;

    public Line(float x1, float y1, float x2, float y2, Paint paint)
    {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.paint = paint;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(x1, y1, x2, y2, paint);
    }

    @Override
    public void move(float x, float y) {
        float centreX, centreY, diffX, diffY;
        centreX = x1+((x2-x1)/2);
        centreY = y1+((y2-y1)/2);
        diffX = x-centreX;
        diffY = y-centreY;
        x1 += diffX;
        x2 += diffX;
        y1 += diffY;
        y2 += diffY;
    }

    @Override
    public void scale(double factor) {
        x2 = (float)(x2 * factor);
        y2 = (float)(y2 * factor);
    }

    @Override
    public Shape deepCopy() {
        return null;
    }
}
