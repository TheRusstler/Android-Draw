package uk.ac.standrews.cs.cs5041.draw;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Line implements Shape {
    public float x1, y1, x2, y2;
    public Paint paint;
    private double rotation = 0;

    public Line(float x1, float y1, float x2, float y2, double rotation, Paint paint)
    {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.rotation = rotation;
        this.paint = paint;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(x1, y1, x2, y2, paint);
    }

    @Override
    public void move(float x, float y) {
        float centreX, centreY, diffX, diffY;
        centreX = getCentreX();
        centreY = getCentreY();
        diffX = x-centreX;
        diffY = y-centreY;
        x1 += diffX;
        x2 += diffX;
        y1 += diffY;
        y2 += diffY;
    }

    @Override
    public void scale(double factor) {
        float centreX, centreY, offsetX, offsetY;
        centreX = getCentreX();
        centreY = getCentreY();
        offsetX = centreX - x1;
        offsetY = centreY - y1;
        x1 = (float) (centreX - (offsetX * factor));
        x2 = (float) (centreX + (offsetX * factor));
        y1 = (float) (centreY - (offsetY * factor));
        y2 = (float) (centreY + (offsetY * factor));
    }

    @Override
    public void rotate(float startX, float startY, float endX, float endY) {
        float centreX, centreY;
        double startAngle, endAngle;

        centreX = getCentreX();
        centreY = getCentreY();

        startAngle =  Math.atan2(centreY - endY, endX-centreX) * (180/Math.PI);
        endAngle = Math.atan2(centreY-startY, startX-centreX) * (180/Math.PI);

        rotation = endAngle - startAngle;
    }

    @Override
    public Shape deepCopy() {
        return new Line(x1, y1, x2, y2, rotation, paint);
    }


    public float getCentreX() {
        return x1 + ((x2 - x1) / 2);
    }
    public float getCentreY() {
        return y1 + ((y2 - y1) / 2);
    }

    public double getRotation() {
        return rotation;
    }
}
