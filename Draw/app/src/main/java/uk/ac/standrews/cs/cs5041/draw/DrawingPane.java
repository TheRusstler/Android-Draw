package uk.ac.standrews.cs.cs5041.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class DrawingPane extends View {

    private DrawingMode mode = DrawingMode.Draw;
    private CurrentShape currentShape = CurrentShape.Line;
    private ArrayList<Shape> shapes = new ArrayList<Shape>();

    private Shape newObject;
    Runnable onModeChange;

    private Shape preScaleShape;
    private double multitouchStartDist = 0;

    private float startRotateX, startRotateY;

    private int color = Color.BLACK;

    public DrawingPane(Context context, Runnable onModeChange) {
        super(context);
        this.onModeChange = onModeChange;
    }

    public void clear() {
        shapes.clear();
        newObject = null;
        setMode(DrawingMode.Draw);
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


        if (event.getPointerCount() == 2) {
            // Multitouch event

            switch (action) {
                case MotionEvent.ACTION_POINTER_DOWN:
                    if (mode == DrawingMode.Scale) {
                        preScaleShape = newObject.deepCopy();
                        multitouchStartDist = getMultitouchDist(event);
                    }
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (mode == DrawingMode.Scale) {
                        pointerMultitouchMove(event);
                    }
                    break;
            }
        } else {
            // Single touch event
            switch (action) {
                case MotionEvent.ACTION_DOWN:
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
        }

        invalidate();
        return true;
    }

    void pointerMultitouchMove(MotionEvent event) {
        double dist = getMultitouchDist(event);
        double factor = dist / multitouchStartDist;
        Shape scaled = preScaleShape.deepCopy();
        scaled.scale(factor);
        newObject = scaled;
    }

    static double getMultitouchDist(MotionEvent event) {
        float diffX = Math.abs(event.getX(0) - event.getX(1));
        float diffY = Math.abs(event.getY(0) - event.getY(1));
        double dist = Math.sqrt((Math.pow(diffX, 2) + Math.pow(diffY, 2)));
        return dist;
    }

    void pointerDown(float x, float y) {
        if (mode == DrawingMode.Draw) {
            switch (currentShape) {
                case Line:
                    newObject = new Line(x, y, x, y, 0, getPaint());
                    break;
                case Rectangle:
                    newObject = new Rectangle(x, y, x, y, 0, getPaint());
                    break;
                case Circle:
                    newObject = new Circle(x, y, 0, getPaint());
                    break;
            }
        }
        if (mode == DrawingMode.Move) {
            pointerMove(x, y);
        }
        if (mode == DrawingMode.Rotate) {
            startRotateX = x;
            startRotateY = y;
        }
    }

    void pointerMove(float x, float y) {
        if (mode == DrawingMode.Draw) {
            switch (currentShape) {
                case Line:
                    Line line = (Line) newObject;
                    line.x2 = x;
                    line.y2 = y;
                    break;
                case Rectangle:
                    Rectangle rect = (Rectangle) newObject;
                    rect.x2 = x;
                    rect.y2 = y;
                    break;
                case Circle:
                    Circle circ = (Circle) newObject;
                    circ.setRadius(x, y);
                    break;
            }
        }
        if (mode == DrawingMode.Move) {
            newObject.move(x, y);
        }
        if (mode == DrawingMode.Rotate) {
            newObject.rotate(startRotateX, startRotateY, x, y);
        }
    }

    void pointerUp(float x, float y) {
        setMode(DrawingMode.Drawn);
    }

    private void drawBackground(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }

    void drawObjects(Canvas canvas) {
        float rotation = 0, centreX = 0, centreY = 0;
        for (Shape s : shapes) {
            drawObject(canvas, s);
        }
        if (newObject != null) {
            drawObject(canvas, newObject);
        }
    }

    void drawObject(Canvas canvas, Shape s) {
        float rotation = 0, centreX = 0, centreY = 0;
        rotation = (float)s.getRotation();
        centreX = s.getCentreX();
        centreY = s.getCentreY();
        canvas.rotate(rotation, centreX, centreY);
        s.draw(canvas);
        canvas.rotate(-rotation, centreX, centreY);
    }

    Paint getPaint() {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(6);
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
        setMode(DrawingMode.Draw);
        invalidate();
    }

    public void confirmNewObject() {
        shapes.add(newObject);
        newObject = null;
        setMode(DrawingMode.Draw);
    }


    public void setColor(int color) {
        this.color = color;
    }
}
