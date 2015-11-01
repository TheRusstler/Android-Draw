package uk.ac.standrews.cs.cs5041.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class DrawingPane extends View {

    private DrawingMode mode = DrawingMode.None;
    private CurrentShape currentShape;
    private ArrayList<Shape> shapes = new ArrayList<Shape>();

    private Shape newObject;
    Runnable onModeChange;

    public DrawingPane(Context context, Runnable onModeChange) {
        super(context);
        this.onModeChange = onModeChange;
    }

    public void clear() {
        shapes.clear();
        newObject = null;
        setMode(DrawingMode.None);
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        drawObjects(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action, pointerIndex;
        float x, y;

        action = event.getActionMasked();
        pointerIndex = event.getActionIndex();

        x = event.getX(pointerIndex);
        y = event.getY(pointerIndex) - 50;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                pointerDown(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                pointerMove(x, y);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                pointerUp(x, y);
        }
        invalidate();
        return true;
    }

    void pointerDown(float x, float y) {
        if (mode == DrawingMode.Draw) {
            if (currentShape == CurrentShape.Line) {
                newObject = new Line(x, y, x, y, getPaint());
            }
        }
        if (mode == DrawingMode.Move) {
            pointerMove(x, y);
        }
    }

    void pointerMove(float x, float y) {
        if (mode == DrawingMode.Draw) {
            if (currentShape == CurrentShape.Line) {
                Line line = (Line) newObject;
                line.x2 = x;
                line.y2 = y;
            }
        }
        if (mode == DrawingMode.Move) {
            if (currentShape == CurrentShape.Line) {
                newObject.move(x, y);
            }
        }
    }

    void pointerUp(float x, float y) {
        if(mode != DrawingMode.None)
        {
            setMode(DrawingMode.Drawn);
        }
    }

    private void drawBackground(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }

    void drawObjects(Canvas canvas) {
        for (Shape s : shapes) {
            s.draw(canvas);
        }
        if (newObject != null) {
            newObject.draw(canvas);
        }
    }

    Paint getPaint() {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
        return paint;
    }

    public DrawingMode getMode() {
        return mode;
    }

    public void setMode(DrawingMode mode) {
        this.mode = mode;
        onModeChange.run();
    }

    public void setCurrentShape(CurrentShape currentShape) {
        this.currentShape = currentShape;
    }

    public void cancelNewObject() {
        newObject = null;
        setMode(DrawingMode.None);
        invalidate();
    }

    public void confirmNewObject() {
        shapes.add(newObject);
        newObject = null;
        setMode(DrawingMode.None);
    }
}
